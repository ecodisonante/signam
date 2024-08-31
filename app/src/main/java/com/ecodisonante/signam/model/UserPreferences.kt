package com.ecodisonante.signam.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val USER_KEY = "USER_KEY"
private const val USERLIST_KEY = "USERLIST_KEY"


/**
 * Clase encargada de manejar el SharedPreferences para Usuarios
 */
class UserPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    /**
     * Guardar el usuario activo
     *
     * @param user Usuario activo
     */
    fun saveCurrentUser(user: User?) {
        val jsonUser = gson.toJson(user)
        sharedPreferences.edit().putString(USER_KEY, jsonUser).apply()
    }

    /**
     * Obtener usuario activo
     *
     * @return usuario activo
     */
    fun getCurrentUser(): User? {
        val jsonUser = sharedPreferences.getString(USER_KEY, null)
        return gson.fromJson(jsonUser, User::class.java)
    }

    /**
     * Guardar lista completa de usuarios
     *
     * @param userList lista completa de usuarios
     */
    fun saveUserList(userList: List<User>) {
        val jsonUserList = gson.toJson(userList)
        sharedPreferences.edit().putString(USERLIST_KEY, jsonUserList).apply()
    }

    /**
     * Obtener la lista completa de usuarios
     *
     * @return lista completa de usuarios
     */
    fun getUserList(): List<User>? {
        val jsonUserList = sharedPreferences.getString(USERLIST_KEY, null)
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(jsonUserList, type)
    }

    /**
     * Agregar usuario a la lista de usuarios
     *
     * @param user Usuario a agregar
     */
    fun addUserToList(user: User) {
        val currentList = getUserList()?.toMutableList() ?: mutableListOf()
        currentList.add(user)
        saveUserList(currentList)
    }

    /**
     * Obtener un usuario por su email
     *
     * @param email Email del usuario buscado
     * @return usuario encontrado o null si no existe
     */
    fun findUserByEmail(email: String): User? {
        return getUserList()?.find { it.email == email }
    }


}
