package com.kaiser.server.model

data class ChatRequest(
    val messages: List<Message>
)

data class ChatResponse(
    val choices: List<Choice>
)

data class Message(
    val role: String,
    val content: String
)

data class Choice(
    val message: Message
)

data class AuthResponse(
    val access_token: String,
    val expires_in: Int,
    val scope: String,
    val token_type: String
) 