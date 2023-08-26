package com.kaizenplus.tamatemassignment.presentation.screens.main

data class UiModel (
    val description: String,
    val isInternetConnected: Boolean,
    val onClick: () -> Unit = {}
)