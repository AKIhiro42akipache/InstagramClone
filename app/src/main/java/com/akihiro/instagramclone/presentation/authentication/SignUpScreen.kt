package com.akihiro.instagramclone.presentation.authentication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.akihiro.instagramclone.R
import com.akihiro.instagramclone.common.NetworkResponse
import com.akihiro.instagramclone.common.Screens
import com.akihiro.instagramclone.presentation.Toast

@Composable
fun SignUpScreen(navigator: NavHostController, authViewModel: AuthenticationViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "LoginScreen Logo",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val userNameState = remember {
                mutableStateOf("")
            }
            val emailState = remember {
                mutableStateOf("")
            }
            val passwordState = remember {
                mutableStateOf("")
            }
            Spacer(modifier = Modifier.height(115.dp))
            Text(
                text = "Instagram",
                modifier = Modifier.padding(10.dp),
                fontSize = 60.sp,
                color = Color.White,
                fontFamily = FontFamily(
                    Font(
                        R.font.cookie_regular,
                        FontWeight.Normal
                    )
                )
            )
            Spacer(modifier = Modifier.height(70.dp))
            //userNameを入力するTextField
            OutlinedTextField(
                value = userNameState.value,
                onValueChange = {
                    userNameState.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(10.dp),
                label = {
                    Text(
                        text = "ユーザー名",
                        color = Color.White
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                ),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = emailState.value,
                onValueChange ={
                    emailState.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(10.dp),
                label = {
                    Text(
                        text = "メールアドレス",
                        color = Color.White
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                ),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = passwordState.value,
                onValueChange = {
                    passwordState.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(10.dp),
                label = {
                    Text(
                        text = "パスワード",
                        color = Color.White
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                )
            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = {
                    authViewModel.signUp(
                        username = userNameState.value,
                        email = emailState.value,
                        password = passwordState.value
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF2DC0FF),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "新規登録",
                    fontWeight = FontWeight.Bold,
                )
                when (val netWorkResponse = authViewModel.signUpState.value) {
                    is NetworkResponse.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is NetworkResponse.Success -> {
                        if (netWorkResponse.data) {
                            navigator.navigate(Screens.FeedScreen.route) {
                                popUpTo(Screens.SignUpScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    is NetworkResponse.Error -> {
                        Toast(message = netWorkResponse.error)
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .padding(start = 20.dp)
                )

                {
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp)) // テキストと線の間にスペースを作成

                Text(
                    text = "または",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp)) // テキストと線の間にスペースを作成

                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .padding(end = 20.dp)
                ) {
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "すでに登録していますか? ログイン",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigator.navigate(Screens.LoginScreen.route) {
                            launchSingleTop = true
                        }
                    })
        }
    }
}