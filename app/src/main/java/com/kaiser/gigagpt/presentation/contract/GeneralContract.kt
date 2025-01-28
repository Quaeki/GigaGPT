package com.kaiser.gigagpt.presentation.contract

import com.kaiser.gigagpt.API.Message

interface GeneralContract {
    interface View {
        fun showMessages(messages: List<Message>)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun clearInput()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun sendMessage(message: String)
        fun loadMessages()
    }
} 