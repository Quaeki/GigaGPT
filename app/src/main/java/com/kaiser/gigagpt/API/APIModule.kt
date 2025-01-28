package com.kaiser.gigagpt.API

import retrofit2.http.*

interface APIModule {
    @POST("api/v1/chat/completions")
    @Headers("Content-Type: application/json")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body message: ChatRequest
    ): ChatResponse

    @POST("POST/api/v2/oauth")
    @Headers("Content-Type: application/json")
    suspend fun getAuthToken(
        @Header("Authorization") credentials: String
    ): TokenResponse
}

data class ChatRequest(
    val messages: List<Message>,
    val temperature: Double = 0.7,
    val model: String = "GigaChat:latest"
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    val choices: List<Choice>,
    val created: Long,
    val model: String
)

data class Choice(
    val message: Message,
    val finish_reason: String
)

data class TokenResponse(
    val access_token: String,
    val expires_at: String,
    val token_type: String
)