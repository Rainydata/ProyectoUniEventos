package com.unieventos.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import com.unieventos.model.Role
import com.unieventos.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel: ViewModel() {

    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow <List<User>> = _users.asStateFlow()

    init {
        _users.value = getUsersList()
    }

    fun createUser(user: User){
        _users.value += user
    }

    fun loginUser(email: String, password: String): User?{
        return _users.value.find { it.email == email && it.password == password }
    }

    private fun getUsersList():List<User>{

        return listOf(
            User(
                cedula = "1004701942",
                name = "Bryan Benavides",
                address = "calle 58 #45-68",
                phoneNumber = "3007103645",
                email = "bryan@gmail.com",
                password = "123456",
                role = Role.ADMIN,
            ),
        )

    }

}