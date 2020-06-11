package com.alanpaine.sphtech.mvp.main.contract

import com.alanpaine.baselib.base.IFcfrtBasePresenter
import com.alanpaine.baselib.base.IFcfrtBaseView
import com.alanpaine.sphtech.bean.ModeData
import com.alanpaine.sphtech.bean.RecordsData


/**
 * 项目名称：
 * 类名称：CMain
 * 类描述：MainActivity的Contract
 * 作者：Alanpaine
 * 创建时间：
 * 邮箱：
 * 修改简介：
 */
interface CMain {

    interface IPMain : IFcfrtBasePresenter {
        fun datastoreSearch(resource_id:String)
    }

    interface IVMain : IFcfrtBaseView {
        fun showLoading()
        fun hideLoading()
        fun onSuccess(data: List<RecordsData>)
        fun onFailure(msg:String)
    }
}
