package com.unieventos.utils

import android.content.Context
import com.unieventos.dto.UserDTO
import com.unieventos.model.Role

object SharedPreferenceUtils {

    fun savePreference(context: Context, cedula: String, rol: Role){

        val sharedPreferences = context.getSharedPreferences("sesion",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("sesion", cedula)
        editor.putString("rol", rol.name)
        editor.apply()
    }

    fun clearPreference(context: Context){

        val sharedPreferences = context.getSharedPreferences("sesion",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun getCurrentUser(context: Context): UserDTO?{
        val sharedPreferences = context.getSharedPreferences("sesion",Context.MODE_PRIVATE)
        val cedula = sharedPreferences.getString("cedula", "")
        val rol = sharedPreferences.getString("rol", "")

        return if(cedula.isNullOrEmpty() || rol.isNullOrEmpty()){
             null
        }else{
        return UserDTO(cedula, Role.valueOf(rol))
        }

}}