package com.akihiro.instagramclone.data.repository

import com.akihiro.instagramclone.common.Constants
import com.akihiro.instagramclone.common.NetworkResponse
import com.akihiro.instagramclone.domain.model.Users
import com.akihiro.instagramclone.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth:FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthenticationRepository {
    private var operationSuccessful:Boolean = false
    override fun isUserAuthenticatedInFirebase(): Boolean {
      return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        //ユーザーの認証状況を監視してユーザーがログインしているか否かを返す
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }

        //ユーザーの認証状況を監視してリスナが呼ばれる毎にauthStateListenerを呼び出す
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSingIn(email: String, password: String): Flow<NetworkResponse<Boolean>> = flow{
        operationSuccessful = false
        try{
            emit(NetworkResponse.Loading)
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                operationSuccessful = true
            }.await()
            emit(NetworkResponse.Success(operationSuccessful))
        }catch (e:Exception){
           //例外が発生したときにエラーを返却する
            emit(NetworkResponse.Error(e.localizedMessage?:"エラーが発生しました"))
        }
    }

    override fun firebaseSignOut(): Flow<NetworkResponse<Boolean>> = flow {
       try{
           emit(NetworkResponse.Loading)
           auth.signOut()
           emit(NetworkResponse.Success(true))
       }catch (e:Exception){
           emit(NetworkResponse.Error(e.localizedMessage?:"エラーが発生しました"))
       }
    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        userName: String,
    ): Flow<NetworkResponse<Boolean>> = flow{
        operationSuccessful = false
        try {
            emit(NetworkResponse.Loading)
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                operationSuccessful = true
            }
            //新規ユーザーを登録する処理
            if(operationSuccessful){
                val userId = auth.currentUser?.uid!!
                val obj = Users(userName = userName, userid = userId,password = password,email = email)
                fireStore.collection(Constants.COLLECTION_NAME_USERS).document(userId).set(obj).addOnSuccessListener {

                }.await()
                emit(NetworkResponse.Success(operationSuccessful))
            }else{
                NetworkResponse.Success(operationSuccessful)
            }
        }catch (e:Exception){
            emit(NetworkResponse.Error(e.localizedMessage?:"エラーが発生しました"))
        }
    }
}