package com.akihiro.instagramclone.di

import com.akihiro.instagramclone.data.repository.AuthenticationRepositoryImpl
import com.akihiro.instagramclone.domain.repository.AuthenticationRepository
import com.akihiro.instagramclone.domain.usecase.AuthenticationsUseCases
import com.akihiro.instagramclone.domain.usecase.FirebaseAuthState
import com.akihiro.instagramclone.domain.usecase.FirebaseSignIn
import com.akihiro.instagramclone.domain.usecase.FirebaseSignOut
import com.akihiro.instagramclone.domain.usecase.FirebaseSignUp
import com.akihiro.instagramclone.domain.usecase.IsUserAuthenticated
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthentication():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(auth:FirebaseAuth,fireStore:FirebaseFirestore):AuthenticationRepository{
        return AuthenticationRepositoryImpl(auth = auth,fireStore = fireStore)
    }

    @Provides
    @Singleton
    fun provideAuthUserCases(repository: AuthenticationRepository) = AuthenticationsUseCases(
        isUserAuthenticated = IsUserAuthenticated(repository = repository),
        firebaseAuthState = FirebaseAuthState(repository = repository),
        firebaseSignOut = FirebaseSignOut(repository = repository),
        firebaseSignIn = FirebaseSignIn(repository = repository),
        firebaseSignUp = FirebaseSignUp(repository = repository)
    )
}