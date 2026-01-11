package com.example.composetutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(
    profile: Profile,
    onNavigateToConversation: () -> Unit,
) {
    Column {
        Text(
            text = "Profile for ${profile.name}",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        Button(onClick = { onNavigateToConversation() }) {
            Text(text = "Go to conversation")
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