package com.ecodisonante.signam.data

import android.util.Log
import com.ecodisonante.signam.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class FirebaseUserService(private val database: DatabaseReference) : UserService {

    override fun getByEmail(email: String, onResult: (User?) -> Unit) {
        val usersRef = database.child("users")
        val foundUser = usersRef.orderByChild("email").equalTo(email)

        foundUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.children.firstOrNull()?.getValue(User::class.java)
                    onResult(user)
                } else {
                    onResult(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("UserService", "Error en getByEmail: ${error.message}")
                onResult(null)
            }
        })
    }

    override fun createUser(user: User, onResult: (Boolean) -> Unit) {
        database.child("users").push().setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }


    // Otros métodos CRUD
}
