package com.akihiro.instagramclone.presentation.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akihiro.instagramclone.common.NetworkResponse
import com.akihiro.instagramclone.domain.usecase.AuthenticationsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authUseCases: AuthenticationsUseCases
):ViewModel(){

    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    //ログインを関連のmutableState
    private val _signInState = mutableStateOf<NetworkResponse<Boolean>>(NetworkResponse.Success(false))
    val signInState : State<NetworkResponse<Boolean>> = _signInState

    //ユーザー新規登録関連のmutableState
    private val _signUpState = mutableStateOf<NetworkResponse<Boolean>>(NetworkResponse.Success(false))
    val signUpState : State<NetworkResponse<Boolean>> = _signUpState

    //ログアウト関連のmutableState
    private val _signOutState = mutableStateOf<NetworkResponse<Boolean>>(NetworkResponse.Success(false))
    val signOutState : State<NetworkResponse<Boolean>> = _signOutState

    private val _firebaseAuthState = mutableStateOf<Boolean>(false)
    val firebaseAuthState:State<Boolean> = _firebaseAuthState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignIn(email = email, password = password)
                .collect {
                    _signInState.value = it
                }
        }
    }

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignUp(email = email, password = password, username = username)
                .collect {
                    _signUpState.value = it
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCases.firebaseSignOut()
                .collect {
                    _signOutState.value = it
                    if (it == NetworkResponse.Success(true)) {
                        _signInState.value = NetworkResponse.Success(false)
                    }
                }
        }
    }

    fun getFirebaseAuthState() {
        viewModelScope.launch {
            authUseCases.firebaseAuthState()
                .collect {
                    _firebaseAuthState.value = it
                }
        }
    }
}