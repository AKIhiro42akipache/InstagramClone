package com.akihiro.instagramclone.domain.repository

import com.akihiro.instagramclone.common.NetworkResponse
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.flow.Flow


interface AuthenticationRepository {
    fun isUserAuthenticatedInFirebase(): Boolean
    fun getFirebaseAuthState(): Flow<Boolean>
    fun firebaseSingIn(email: String, password: String): Flow<NetworkResponse<Boolean>>
    fun firebaseSignOut(): Flow<NetworkResponse<Boolean>>
    fun firebaseSignUp(email: String, password: String, userName: String): Flow<NetworkResponse<Boolean>>

}