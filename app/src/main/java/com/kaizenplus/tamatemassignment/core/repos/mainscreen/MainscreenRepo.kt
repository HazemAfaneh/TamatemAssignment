package com.kaizenplus.tamatemassignment.core.repos.mainscreen

import com.kaizenplus.tamatemassignment.core.models.ResultData

interface MainscreenRepo {
    fun checkInternetConnection(): ResultData<Boolean>
}