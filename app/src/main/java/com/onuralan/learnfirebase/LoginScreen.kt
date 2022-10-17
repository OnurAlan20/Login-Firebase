package com.onuralan.learnfirebase


import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(auth:FirebaseAuth){
    val focusManager = LocalFocusManager.current

    var email by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    val isEmailValid by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    val isPasswordValid by derivedStateOf {
        password.length > 8
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(modifier = Modifier.size(150.dp),painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "PP")
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Register", style = MaterialTheme.typography.h3, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Spacer(modifier = Modifier.height(15.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp,Color.Black)
                ) {
                    Column(modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(value = email,
                            onValueChange = {email = it},
                            label = { Text(text = "Email")},
                            placeholder = { Text(text = "abc@domain.com")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {focusManager.moveFocus(FocusDirection.Down)}
                            ),
                            isError = !isEmailValid,
                            trailingIcon = {
                                if(email.isNotBlank()) {
                                    IconButton(onClick = { email = "" }) {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear Email")

                                    }
                                }
                            }

                        )

                        OutlinedTextField(value = password,
                            onValueChange = {password = it},
                            label = { Text(text = "Password")},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {focusManager.clearFocus()}
                            ),
                            isError = !isPasswordValid,
                            trailingIcon = {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                        Icon(imageVector = if(isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = "Toggle password visibility")

                                }
                            },
                            visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                        )
                        Button(
                            onClick = {
                                auth.signInWithEmailAndPassword(email,password)
                                    .addOnCompleteListener{
                                        if(it.isSuccessful) Log.d(TAG,"The User logged in") else Log.w(
                                            TAG,"The User did not log in",it.exception)
                                    }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                            enabled = isEmailValid && isPasswordValid
                            ) {
                            Text(
                                text = "Log In",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 16.sp
                                )

                        }

                    }
                }
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Forgotten password?",
                            fontStyle = FontStyle.Italic,
                            color = Color.Black,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                    }
                }
                Button(onClick ={},
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                    Text(text = "Register", fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight.Bold)

                }
            }
        }
}






