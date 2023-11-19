package com.akihiro.instagramclone.domain.usecase

import com.akihiro.instagramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsUserAuthenticated @Inject constructor(
    private val repository:AuthenticationRepository
) {
    operator fun invoke() = repository.isUserAuthenticatedInFirebase()
}