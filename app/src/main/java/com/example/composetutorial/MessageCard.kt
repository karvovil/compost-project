package com.example.composetutorial

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import java.io.File
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message, onNavigateToProfile: () -> Unit) {
    val context = LocalContext.current
    val profilePicFile = remember { File(context.filesDir, "profilePic.jpg") }
    var profilePicBitmap by remember {
        mutableStateOf(BitmapFactory.decodeFile(profilePicFile.absolutePath))
    }
    val prefs = remember { context.getSharedPreferences("prefs", Context.MODE_PRIVATE) }
    var username by remember {
        mutableStateOf(prefs.getString("username", "LeXXXi") ?: "LeXXXi")
    }
    Row(modifier = Modifier.padding(all = 10.dp)) {
        Image(
            bitmap = profilePicBitmap.asImageBitmap(),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .border(1.8.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { onNavigateToProfile() }
        )
        Spacer(modifier = Modifier.width(10.dp))
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = username,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(5.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 2.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(2.dp)
            ) {
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(all = 5.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Preview
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(
                onNavigateToProfile = {},
                msg = Message("LeXXXi", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}