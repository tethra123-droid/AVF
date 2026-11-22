package com.example.model

data class ChatMessage(
    val id: String,
    val dateKey: String,
    val time: String,
    val sender: String,
    val content: String,
    val isMediaOmitted: Boolean = false,
    val isSystemMessage: Boolean = false
)

sealed class ChatTimelineItem {
    data class DateHeader(
        val id: String,
        val dateKey: String,
        val messageCount: Int
    ) : ChatTimelineItem()

    data class MessageItem(
        val message: ChatMessage
    ) : ChatTimelineItem()
}
