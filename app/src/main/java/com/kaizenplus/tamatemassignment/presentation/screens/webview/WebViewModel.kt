package com.kaizenplus.tamatemassignment.presentation.screens.webview

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.kaizenplus.tamatemassignment.presentation.BaseViewModel
import com.kaizenplus.tamatemassignment.presentation.screens.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@HiltViewModel
class WebViewModel : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState?> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState?> get() = _uiState

    data class UiState(
        val isLoading: Boolean = true,
        val action: UIAction? = null
    )

    sealed class UIAction {
        object LoadUrl : UIAction()
        object MoveBack : UIAction()
        object MoveForward : UIAction()
        object Refresh : UIAction()
        object Finish : UIAction()
        object Steady : UIAction()
    }

    fun actionTrigger(action: UIAction) {
        viewModelScope.launch {
            when (action) {
                is UIAction.MoveBack,UIAction.MoveForward,UIAction.Refresh, UIAction.LoadUrl -> {
                    _uiState.emit(
                        UiState(isLoading = true, action = action)
                    )
                }
                is UIAction.Steady, UIAction.Finish -> {
                    _uiState.emit(
                        UiState(isLoading = false, action = action)
                    )
                }
            }

        }
    }
}