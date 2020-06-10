package com.alanpaine.sphtech.mvp.ui.activity
import com.alanpaine.baselib.base.FcfrtBaseActivity
import com.alanpaine.sphtech.R
import com.alanpaine.sphtech.mvp.contract.CMain
import com.alanpaine.sphtech.mvp.presenter.PMainImpl

class MainActivity : FcfrtBaseActivity<PMainImpl>(), CMain.IVMain {

    /**
     * 获取布局id
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    /**
     * 创建presenter实例
     */
    override fun createPresenter(): PMainImpl {
        return PMainImpl(this, this)
    }

    /**
     * 界面创建完成
     */
    override fun onViewCreated() {

    }

}
