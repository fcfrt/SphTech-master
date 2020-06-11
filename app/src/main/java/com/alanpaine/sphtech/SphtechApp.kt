package com.alanpaine.sphtech
import com.alanpaine.baselib.base.FcfrtBaseApplication
import org.litepal.LitePal


class SphtechApp : FcfrtBaseApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this)
    }

    companion object {
        lateinit var instance: SphtechApp

    }

}