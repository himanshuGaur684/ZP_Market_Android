package com.gaur.zpmarket.presentation.payment

import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO

data class PostOrderIdState(
    val data: ServerMessageDTO? = null,
    val error: String = "",
    val isLoading: Boolean = false
)