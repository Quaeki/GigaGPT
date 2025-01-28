package com.kaiser.gigagpt.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaiser.gigagpt.API.*
import com.kaiser.gigagpt.utils.ApiConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralVM @Inject constructor(
    private val api: APIModule
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var authToken: String? = null

    init {
        authenticate()
    }

    private fun authenticate() {
        viewModelScope.launch {
            try {
                val response = api.getAuthToken("Bearer ${ApiConstants.API_KEY}")
                authToken = "${response.token_type} ${response.access_token}"
            } catch (e: Exception) {
                // Обработка ошибки аутентификации
            }
        }
    }

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank() || authToken == null) return

        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Добавляем сообщение пользователя
                val userMsg = Message(role = "user", content = userMessage)
                _messages.value = _messages.value + userMsg

                // Отправляем запрос к API
                val request = ChatRequest(
                    messages = _messages.value,
                    temperature = 0.7,
                    model = "GigaChat:latest"
                )

                val response = api.sendMessage(authToken!!, request)

                Log.d("GeneralVM", "Отправляем сообщение: $userMessage")
                Log.d("GeneralVM", "Получен ответ: ${response.choices.firstOrNull()?.message}")

                // Добавляем ответ от API
                response.choices.firstOrNull()?.message?.let { assistantMessage ->
                    _messages.value = _messages.value + assistantMessage
                }
            } catch (e: Exception) {
                _messages.value = _messages.value + Message(
                    role = "assistant",
                    content = "Произошла ошибка. Попробуйте ещё раз."
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}