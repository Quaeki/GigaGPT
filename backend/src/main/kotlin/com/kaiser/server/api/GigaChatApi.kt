package com.kaiser.server.api

import com.kaiser.server.model.ChatRequest
import com.kaiser.server.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GigaChatApi {
    @POST("chat/completions")
    suspend fun sendMessage(
        @Header("Authorization") auth: String,
        @Body request: ChatRequest
    ): ChatResponse
} 