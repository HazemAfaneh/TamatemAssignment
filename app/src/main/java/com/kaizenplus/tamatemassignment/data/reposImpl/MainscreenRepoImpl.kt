package com.kaizenplus.tamatemassignment.data.reposImpl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.kaizenplus.tamatemassignment.core.models.ResultData
import com.kaizenplus.tamatemassignment.core.repos.mainscreen.MainscreenRepo
import javax.inject.Inject


class MainscreenRepoImpl  @Inject constructor(
    var context: Context
) : MainscreenRepo {
    override fun checkInternetConnection(): ResultData<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.activeNetwork ?: return ResultData.Success(false)
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities)
                ?: return ResultData.Success(false)

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ResultData.Success(
                true
            )

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ResultData.Success(
                true
            )

            else -> ResultData.Success(false)
        }
    }
}