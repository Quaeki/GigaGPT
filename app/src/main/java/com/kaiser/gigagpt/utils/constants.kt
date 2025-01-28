package com.kaiser.gigagpt.utils

object ApiConstants {
    const val API_KEY = "YjkxMGMyZjMtMTdiNC00ZDA5LWEwZDctM2U3ZWM0ODdmMTY4OmM2YTUwZmQzLTVkN2ItNDRkZS04MjVmLTBhZjk3YTVhNGViNA=="
    const val BASE_URL = "https://gigachat.devices.sberbank.ru/"
    const val OAUTH_ENDPOINT = "POST/api/v2/oauth"
    
    // Добавим константы для сетевых запросов
    const val TIMEOUT_SECONDS = 30L
    const val MAX_RETRIES = 3
}

object UiConstants {
    const val DEFAULT_PADDING = 16
    const val MESSAGE_CORNER_RADIUS = 12
    const val INPUT_CORNER_RADIUS = 20
    const val LOADING_INDICATOR_SIZE = 50
}