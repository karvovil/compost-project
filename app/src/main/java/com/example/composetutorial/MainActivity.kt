package com.example.composetutorial

import SampleData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import java.io.File
import java.io.FileOutputStream

@Serializable
object Conversation
@Serializable
data class Profile(val name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val avatarFile = File(filesDir, "profilePic.jpg")
        if (!avatarFile.exists()) {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.profile_picture)
            FileOutputStream(avatarFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            bitmap.recycle()
        }

        setContent {
            ComposeTutorialTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Profile(name = "LeXXXi")) {
        composable<Profile> { backStackEntry ->
            val profile: Profile = backStackEntry.toRoute()
            ProfileScreen(
                profile = profile,
                onNavigateToConversation = {
                    navController.navigate(route = Conversation){
                        popUpTo(Conversation){inclusive = true}
                    }
                }
            )
        }
        composable<Conversation> {
            ConversationScreen(
                SampleData.conversationSample,
                onNavigateToProfile = {
                    navController.navigate(route = Profile(name = "LeXXXi"))
                }
            )
        }
    }
}