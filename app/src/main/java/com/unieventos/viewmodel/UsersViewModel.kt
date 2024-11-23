package com.unieventos.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unieventos.model.Role
import com.unieventos.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow <List<User>> = _users.asStateFlow()

    init {
        _users.value = getUsersList()
    }

    fun createUser(user: User){
        viewModelScope.launch{
            createuserFirebase(user)
        }
    }

    private suspend fun createuserFirebase(user:User){
        val response = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val userId = response.user?.uid ?: throw Exception("No se pudo crear el usuario")

        user.id = userId

        //TODO no guardar la contraseña
        db.collection("users")
            .add(user)
            .await()
    }

    fun loginUser(email: String, password: String): User?{
        return _users.value.find { it.email == email && it.password == password }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch{
            deleteUserFirebase(userId)
        }

    }


    private suspend fun deleteUserFirebase(userId: String) {
    db.collection("users")
        .document(userId)
        .delete()
        .await()
    }

    private fun getUsersList():List<User>{

        return listOf(
            User(
                cedula = "1004701942",
                name = "admin",
                address = "calle 58 #45-68",
                phoneNumber = "3007103645",
                email = "admin@gmail.com",
                password = "123456",
                role = Role.ADMIN,
            ),
        )

    }

}