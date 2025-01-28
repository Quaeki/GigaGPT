package com.kaiser.server.service

import com.kaiser.server.api.GigaChatApi
import com.kaiser.server.model.ChatRequest
import com.kaiser.server.model.Message
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Service
class ChatService {
    private val apiKey = "b910c2f3-17b4-4d09-a0d7-3e7ec487f168"
    private val api: GigaChatApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gigachat.devices.sberbank.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(GigaChatApi::class.java)
    }

    suspend fun sendMessage(message: String): String {
        val request = ChatRequest(
            messages = listOf(
                Message(
                    role = "user",
                    content = message
                )
            )
        )

        return try {
            val auth = "Bearer $apiKey"
            val response = api.sendMessage(auth, request)
            response.choices.firstOrNull()?.message?.content ?: "No response"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
} 