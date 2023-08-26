package com.kaizenplus.tamatemassignment.core.usecases.mainscreen

import com.kaizenplus.tamatemassignment.core.models.ResultData

interface MainscreenUsecase {
    suspend operator fun invoke(): ResultData<Boolean>
}