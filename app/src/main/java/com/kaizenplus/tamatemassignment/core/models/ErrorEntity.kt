package com.kaizenplus.tamatemassignment.core.models


sealed class ErrorEntity {
    object NoConnection : ErrorEntity()
    object Unknown : ErrorEntity()
    data class InternalError(val message: String) : ErrorEntity()
}
