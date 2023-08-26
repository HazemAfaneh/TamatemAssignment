package com.kaizenplus.tamatemassignment.presentation.screens.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.kaizenplus.tamatemassignment.core.usecases.mainscreen.MainscreenUsecase
import com.kaizenplus.tamatemassignment.core.usecases.mainscreen.MainscreenUsecaseImpl
import com.kaizenplus.tamatemassignment.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var mainScreenUsecase: MainscreenUsecase) :
    BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    data class UiState(
        val isInternetExist: Boolean? = null,
    )

    sealed class UIAction {
        object CheckInternetConnection : UIAction()
        object Steady : UIAction()
    }

    private suspend fun checkInternetConnection() {
        handleResult(mainScreenUsecase(), {
            viewModelScope.launch {
                _uiState.emit(
                    _uiState.value.copy(isInternetExist = it)
                )
            }
        }, {
            viewModelScope.launch {
                _uiState.emit(
                    _uiState.value.copy(isInternetExist = false)
                )
            }
        })
    }

    fun actionTrigger(action: UIAction) {
        viewModelScope.launch {
            when (action) {
                is UIAction.CheckInternetConnection -> {
                    withContext(Dispatchers.IO) {
                        checkInternetConnection()
                    }
                }
                is UIAction.Steady -> {
                    _uiState.emit(
                        _uiState.value.copy(isInternetExist = null)
                    )
                }
            }

        }
    }
}