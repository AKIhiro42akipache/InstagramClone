package com.akihiro.instagramclone.domain.usecase

data class AuthenticationsUseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val firebaseAuthState: FirebaseAuthState,
    val firebaseSignOut: FirebaseSignOut,
    val firebaseSignIn: FirebaseSignIn,
    val firebaseSignUp: FirebaseSignUp
) {
}
