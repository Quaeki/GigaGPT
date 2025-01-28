@RestController
@RequestMapping("/api")
class ChatController {
    @PostMapping("/chat")
    suspend fun chat(@RequestBody message: String): String {
        // Здесь логика работы с GigaChat API
        // API ключ хранится на сервере
        return response
    }
} 