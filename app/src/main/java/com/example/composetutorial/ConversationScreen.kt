package com.example.composetutorial

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

@Composable
fun ConversationScreen(
    messages: List<Message>,
    onNavigateToProfile: () -> Unit
) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message, onNavigateToProfile = onNavigateToProfile)
        }
    }
}


@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        ConversationScreen(
            messages = SampleData.conversationSample,
            onNavigateToProfile = {}
        )
    }
}