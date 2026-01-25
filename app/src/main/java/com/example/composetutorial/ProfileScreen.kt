package com.example.composetutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
fun ProfileScreen(
    profile: Profile,
    onNavigateToConversation: () -> Unit,
) {
    val context = LocalContext.current
    val profilePicFile = remember { File(context.filesDir, "profilePic.jpg") }
    var profilePicBitmap by remember {
        mutableStateOf<Bitmap>(BitmapFactory.decodeFile(profilePicFile.absolutePath))
    }

    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        saveToAppStorage(context, uri, profilePicFile)
        profilePicBitmap = BitmapFactory.decodeFile(profilePicFile.absolutePath)

    }

    Column {
        Image(
            bitmap = profilePicBitmap.asImageBitmap(),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .border(1.8.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Text(
            text = "Profile for ${profile.name}",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        Button(onClick = { onNavigateToConversation() }) {
            Text(text = "Go to conversation")
        }

        Button(
            onClick = {
                pickMediaLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            Text("Pick photo")
        }
    }
}
fun saveToAppStorage(context: Context, uri: Uri?, destFile: File) {
    if(uri != null) {
        context.contentResolver.openInputStream(uri).use { input ->
            destFile.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfile() {
    ProfileScreen(
        profile = Profile(name = "LeXXXi"),
        onNavigateToConversation = {},
    )
}
