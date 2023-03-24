package com.example.noorapp.auth

import android.util.Log
import com.example.noorapp.authentication.domain.AuthRepository
import com.example.noorapp.authentication.utils.AuthServiceResult
import com.example.noorapp.authentication.domain.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class AuthService(
    private val auth: FirebaseAuth,
    private val fStore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String,
    ): Flow<AuthServiceResult<Unit>> = callbackFlow {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("authService", "login: success")
                        trySend(AuthServiceResult.Success())
                    } else {
                        Log.d("authService", "login: failed")
                        trySend(AuthServiceResult.Failure(task.exception?.message))
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        awaitClose {
            cancel()
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
    ): Flow<AuthServiceResult<Unit>> = callbackFlow {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uID = task.result.user?.uid ?: ""
                        val user = User(uid = uID, name = name, email = email)
                        fStore.collection("users")
                            .document(uID)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d("user created", "success")
                                trySend(AuthServiceResult.Success())
                            }
                            .addOnFailureListener { e ->
                                Log.d("user created", "${e.message}")
                                trySend(AuthServiceResult.Failure(e.message))
                            }
                    } else {
                        Log.d("authService", "register: failed")
                        trySend(AuthServiceResult.Failure())
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        awaitClose {
            cancel()
        }
    }

    override suspend fun forgetPassword(
        email: String,
    ): Flow<AuthServiceResult<Unit>> = callbackFlow {
        try {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("authService", "forgetPassword: success")
                        trySend(AuthServiceResult.Success())
                    } else {
                        Log.d("authService", "forgetPassword: ${task.exception?.message}")
                        trySend(AuthServiceResult.Failure())
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        awaitClose {
            cancel()
        }
    }

    override suspend fun getUserDetail(uid: String): Flow<String> = callbackFlow {
        try {
            fStore.collection("users").document(uid)
                .get()
                .addOnSuccessListener { dr ->
                    val name = dr.data?.get("name")
                    trySend(name.toString())
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        awaitClose {
            cancel()
        }
    }

    override suspend fun resetPassword(oldPassword: String, newPassword: String): Flow<AuthServiceResult<Unit>> = callbackFlow {
        try {
            val user = auth.currentUser
            val email = user?.email
            val credential = EmailAuthProvider.getCredential(email!!, oldPassword)
            user.reauthenticate(credential).addOnCompleteListener {
                if (it.isSuccessful){
                    user.updatePassword(newPassword).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("changePassword", "new password: success")
                            trySend(AuthServiceResult.Success("Password Updated!"))
                        } else {
                            Log.d("changePassword", "new password: ${task.exception?.message}")
                            trySend(AuthServiceResult.Failure())
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        awaitClose {
            cancel()
        }
    }

    override suspend fun updateProfile(name: String): Flow<AuthServiceResult<Unit>> = callbackFlow {
        try {
            val mapName = hashMapOf(
                "name" to name
            )
            fStore.collection("users").document(FirebaseAuth.getInstance().uid!!)
                .set(mapName)
                .addOnSuccessListener {
                    trySend(AuthServiceResult.Success("Profile Updated!"))
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    trySend(AuthServiceResult.Failure(e.message))
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        awaitClose {
            cancel()
        }
    }


}