package com.akihiro.instagramclone.domain.usecase

import com.akihiro.instagramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignUp@Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(email:String,password:String,username:String) = repository.firebaseSignUp(email,password,username)
}