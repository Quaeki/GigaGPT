package com.kaiser.gigagpt.presentation.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaiser.gigagpt.API.Message
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralScreen(
    viewModel: GeneralVM = hiltViewModel()
) {
    val messages = viewModel.messages.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val messageInput = remember { mutableStateOf("") }

    LaunchedEffect(messages) {
        Log.d("GeneralScreen", "Список сообщений обновлен: $messages")
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
    ) {
        // Верхняя панель
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* Навигация назад */ },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "GigaGPT",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Основной контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Список сообщений
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(messages) { message ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                        exit = fadeOut(animationSpec = tween(durationMillis = 300))
                    ) {
                        MessageItem(message = message)
                    }
                }
            }

            // Поле ввода
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = true
                    )
                    .background(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = messageInput.value,
                        onValueChange = { messageInput.value = it },
                        placeholder = {
                            Text(
                                text = "Введите сообщение...",
                                style = TextStyle(color = Color.Gray)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = Color(0xFF6A11CB)
                        ),
                        textStyle = TextStyle(color = Color.Black)
                    )

                    IconButton(
                        onClick = {
                            if (messageInput.value.isNotBlank()) {
                                viewModel.sendMessage(messageInput.value)
                                messageInput.value = ""
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFF6A11CB),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Send,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp),
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
}

@Composable
private fun MessageItem(message: Message) {
    val backgroundColor = if (message.role == "user") Color(0xFF6A11CB) else Color(0xFF2575FC)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.role == "user") Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor.copy(alpha = 0.8f))
                .padding(12.dp)
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
        }
    }
}
