package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unieventos.model.Role
import com.unieventos.model.User
import com.unieventos.utils.RequestResult
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

    private val _authResult = MutableStateFlow<RequestResult?>(null)
    val authResult: StateFlow<RequestResult?> = _authResult.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        _users.value = getUsersList()
    }

    fun createUser(user: User){
        viewModelScope.launch{
           _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { createuserFirebase(user) }
                .fold(
                    onSuccess = {RequestResult.Success("Usuario creado exitosamente")},
                    onFailure = {RequestResult.Failure(it.message.toString())}
                )
        }
    }

    private suspend fun createuserFirebase(user:User){
        val response = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val userId = response.user?.uid ?: throw Exception("No se pudo crear el usuario")

        val userSave = user.copy(id = userId, password = "")

        //TODO no guardar la contraseña
        db.collection("users")
            .document(userId)
            .set(userSave)
            .await()
    }

    fun login(email: String, password: String){
        viewModelScope.launch{
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { loginFirebase(email, password)  }
                .fold(
                    onSuccess = {RequestResult.Success("Inició de sesión correcto")},
                    onFailure = {RequestResult.Failure(it.message.toString())}
                )
        }

        }


    private suspend fun loginFirebase(email: String, password: String){
        val response = auth.signInWithEmailAndPassword(email, password).await()
        val userId = response.user?.uid ?: throw Exception("No se pudo iniciar sesión")

        val user = getUserById(userId)
        _currentUser.value = user

    }

    fun deleteUser(userId: String) {
        viewModelScope.launch{
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { deleteUserFirebase(userId) }
                .fold(
                    onSuccess = {RequestResult.Success("Usuario eliminado exitosamente")},
                    onFailure = {RequestResult.Failure(it.message.toString())}
                )
        }

        }

    private suspend fun deleteUserFirebase(userId: String) {
    db.collection("users")
        .document(userId)
        .delete()
        .await()
    }

    fun resetAuthResult(){
        _authResult.value = null

    }

    private suspend fun getUserById(userId: String): User? {
        return db.collection("users")
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java)
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