package com.onuralan.learnfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.onuralan.learnfirebase.ui.theme.LearnFirebaseTheme


class MainActivity : ComponentActivity() {
    private val auth by lazy {
        Firebase.auth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            LearnFirebaseTheme {
                LoginScreen(auth)

            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}