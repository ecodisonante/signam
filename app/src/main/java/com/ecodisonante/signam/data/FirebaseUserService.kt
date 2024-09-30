package com.ecodisonante.signam.data

import android.util.Log
import com.ecodisonante.signam.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class FirebaseUserService(private val database: DatabaseReference) : UserService {

    override fun getByEmail(email: String, onResult: (User?) -> Unit) {
        val searchUser = User(email = email)
        database.child("users").child(getUserKey(searchUser))
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    onResult(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("UserService", "Error en getUserByEmail: ${error.message}")
                    onResult(null)
                }
            })
    }

    override fun createUser(user: User, onResult: (Boolean) -> Unit) {
        database.child("users").child(getUserKey(user)).setValue(user)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    override fun updateUser(user: User, onResult: (Boolean) -> Unit) {
        database.child("users").child(getUserKey(user)).setValue(user)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    override fun deleteUser(email: String, onResult: (Boolean) -> Unit) {
        val user = User(email = email)
        database.child("users").child(getUserKey(user)).removeValue()
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun getUserKey(user: User): String {
        return user.email.replace(".", "_")
    }

}
