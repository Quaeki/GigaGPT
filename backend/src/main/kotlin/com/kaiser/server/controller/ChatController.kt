package com.kaiser.server.controller

import com.kaiser.server.service.ChatService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ChatController(
    private val chatService: ChatService
) {
    @PostMapping("/chat")
    suspend fun chat(@RequestBody message: String): String {
        return chatService.sendMessage(message)
    }
} 