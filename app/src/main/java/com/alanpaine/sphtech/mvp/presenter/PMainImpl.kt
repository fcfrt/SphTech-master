package com.alanpaine.sphtech.mvp.presenter

import android.content.Context
import com.alanpaine.baselib.base.FcfrtBasePresenter
import com.alanpaine.sphtech.mvp.contract.CMain
import com.alanpaine.sphtech.mvp.model.MMainImpl

/**
 * 项目名称：
 * 类名称：PMainImpl
 * 类描述：MainActivity的Presenter
 * 作者：FCFRT
 * 创建时间：
 * 邮箱：
 * 修改简介：
 */
class PMainImpl(mContext: Context, mView: CMain.IVMain) :
    FcfrtBasePresenter<CMain.IVMain, MMainImpl>(mContext, mView, MMainImpl()),
    CMain.IPMain {

}
