package com.kaizenplus.tamatemassignment.core.usecases.mainscreen

import android.content.Context
import com.kaizenplus.tamatemassignment.core.models.ResultData
import com.kaizenplus.tamatemassignment.core.repos.mainscreen.MainscreenRepo
import com.kaizenplus.tamatemassignment.data.reposImpl.MainscreenRepoImpl
import javax.inject.Inject

class MainscreenUsecaseImpl @Inject constructor(private var repo: MainscreenRepo):MainscreenUsecase {
    override suspend fun invoke(): ResultData<Boolean> {
         return repo.checkInternetConnection()
    }

}