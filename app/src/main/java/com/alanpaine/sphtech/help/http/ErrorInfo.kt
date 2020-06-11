package com.alanpaine.sphtech.help.http

import android.text.TextUtils
import com.alanpaine.baselib.utils.FcfrtToast
import com.alanpaine.sphtech.R
import com.alanpaine.sphtech.SphtechApp
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException







class ErrorInfo {
     var errorMsg: String? = null //错误文案，网络错误、请求失败错误、服务器返回的错误等文案
     var network: Boolean = false//当前网络状态
     var throwable: Throwable //异常信息

    constructor(throwable:Throwable){
        this.throwable = throwable
        when (throwable) {
            is UnknownHostException -> {//没有网络
                if (!FcfrtNetHelper.isNetworkConnected(SphtechApp.instance)) {
                    this.errorMsg = SphtechApp.instance.getString(R.string.network_error)
                } else {
                    this.errorMsg = SphtechApp.instance.getString(R.string.notify_no_network)
                }
                this.network = false
            }
            is SocketTimeoutException -> {//连接超时
                this.errorMsg = SphtechApp.instance.getString(R.string.time_out_please_try_again_later)
                this.network = false
            }
            is TimeoutException -> {//请求超时
                this.errorMsg = SphtechApp.instance.getString(R.string.time_out_please_try_again_later)
                this.network = false
            }
            is ConnectException -> {//网络不给力
                this.errorMsg = SphtechApp.instance.getString(R.string.esky_service_exception)
                this.network = false
            }
            is HttpStatusCodeException -> {//请求失败异常
                val code = throwable.localizedMessage
                errorMsg = if("404"==code){
                    "请求接口不存在"
                }else{
                    throwable.message

                }
                this.network = false

            }
            is ParseException -> { // ParseException异常表明请求成功，但是数据不正确
                val errorCode = throwable.localizedMessage
                errorMsg = throwable.message
                if (TextUtils.isEmpty(errorMsg)) errorMsg = errorCode//errorMsg为空，显示errorCode
                this.network = true
            }
            else -> {
                this.errorMsg = throwable.message
                this.network = true
            }
        }
    }

    fun show(): Boolean {
        FcfrtToast.show(errorMsg.toString())
        return true
    }

    /**
     * @param standbyMsg 备用的提示文案
     */
    fun show(standbyMsg: String): Boolean {
        FcfrtToast.show(if (TextUtils.isEmpty(errorMsg)) standbyMsg else errorMsg.toString())
        return true
    }

    /**
     * @param standbyMsg 备用的提示文案
     */
    fun show(standbyMsg: Int): Boolean {
        FcfrtToast.show(if (TextUtils.isEmpty(errorMsg)) SphtechApp.instance.getString(standbyMsg) else errorMsg.toString())
        return true
    }



}