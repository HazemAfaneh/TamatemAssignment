package com.kaizenplus.tamatemassignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaizenplus.tamatemassignment.core.models.ErrorEntity
import com.kaizenplus.tamatemassignment.core.models.ResultData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel(): ViewModel() {

  val generalUiState: MutableSharedFlow<ErrorEntity> = MutableSharedFlow()

    fun <T> handleResult(
        result: ResultData<T>,
        onSuccess: (T) -> Unit,
        onError: (ErrorEntity) -> Unit
    ) {
        when (result) {
            is ResultData.Error -> onError(result.data)
            is ResultData.Success -> onSuccess(result.data)
        }
    }
}