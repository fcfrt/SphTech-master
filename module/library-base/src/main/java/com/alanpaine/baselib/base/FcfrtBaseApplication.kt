package com.alanpaine.baselib.base

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import com.alanpaine.baselib.utils.FcfrtAppManager


/**
 * 项目名称：
 * 类名称：BaseApplication.kt
 * 类描述：基础Application
 * 作者：AlanPaine
 * 创建时间： 2020/06/10
 * 邮箱：AlanPaine@163.COM
 * 修改简介：
 */
open class FcfrtBaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }

    /** 当宿主没有继承自该Application时,可以使用set方法进行初始化baseApplication  */
    private fun setApplication(application: FcfrtBaseApplication) {
        sInstance = application
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated( activity: Activity, savedInstanceState: Bundle? ) {
                    FcfrtAppManager.instance.addActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState( activity: Activity, outState: Bundle ) { }
                override fun onActivityDestroyed(activity: Activity) {
                    FcfrtAppManager.instance.removeActivity(activity)
                }
            })
    }

    fun setsDebug(isDebug: Boolean) {
        sDebug = isDebug
    }

    fun issDebug(): Boolean {
        return sDebug
    }

    companion object {
        private var sInstance: FcfrtBaseApplication? = null
        private var sDebug = false

        /**
         * 获得当前app运行的Application
         */
        val instance: FcfrtBaseApplication?
            get() {
                if (sInstance == null) {
                    throw NullPointerException(
                        "please inherit BaseApplication or call setApplication."
                    )
                }
                return sInstance
            }

        /**
         * 获取进程名
         *
         * @param context
         * @return
         */
        fun getCurrentProcessName(context: Context): String? {
            val am =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningApps =
                am.runningAppProcesses ?: return null
            for (proInfo in runningApps) {
                if (proInfo.pid == Process.myPid()) {
                    if (proInfo.processName != null) {
                        return proInfo.processName
                    }
                }
            }
            return null
        }
    }
}