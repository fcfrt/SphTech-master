package com.alanpaine.sphtech.mvp.main.presenter

import android.content.Context
import com.alanpaine.baselib.base.FcfrtBasePresenter
import com.alanpaine.sphtech.bean.RecordsData
import com.alanpaine.sphtech.bean.section.ItemNode
import com.alanpaine.sphtech.bean.section.RootNode
import com.alanpaine.sphtech.help.FcfrtDataHelper
import com.alanpaine.sphtech.help.http.ErrorInfo
import com.alanpaine.sphtech.mvp.main.contract.CMain
import com.alanpaine.sphtech.mvp.main.model.MMainImpl
import com.chad.library.adapter.base.entity.node.BaseNode
import org.litepal.LitePal
import java.util.*
import kotlin.collections.HashMap

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
    FcfrtBasePresenter<CMain.IVMain, MMainImpl>(
        mContext, mView,
        MMainImpl()
    ),
    CMain.IPMain {
    /**
     * 搜索
     */
    override fun datastoreSearch(resource_id: String) {
        mModel!!.datastoreSearch(resource_id)
            .doOnSubscribe { mView?.showLoading() }
            .doFinally { mView?.hideLoading() }
            .subscribe({ data ->
                data.records?.let {
                    mView?.onSuccess(it)
                    //保存数据，辅助离线状态时有数据展示
                    FcfrtDataHelper.saveRecordsListData(it)
                }
            }, {
                val error = ErrorInfo(it)
                LitePal.findAllAsync(RecordsData::class.java).listen { data ->
                    if (data.isNotEmpty()) {
                        mView?.onSuccess(data)
                    } else {
                        mView?.onFailure(error.errorMsg.toString())
                    }
                }
            }).toString()
    }


    fun getEntity(data: List<RecordsData>): List<BaseNode>? {
        val list: MutableList<BaseNode> = ArrayList()
        val byLength = data.groupBy { it.quarter?.split("-")?.get(0) }
        byLength.forEach {
            var items: MutableList<BaseNode> = ArrayList()
            var yearVolume=0F
            if (it.key?.toInt() in 2008..2018) {
                it.value.forEach {its->
                    val itemEntity = ItemNode()
                    itemEntity._id = its._id
                    itemEntity.quarter = its.quarter
                    itemEntity.volume_of_mobile_data = its.volume_of_mobile_data
                    items.add(itemEntity)
                    yearVolume += its.volume_of_mobile_data?.toFloat() ?: 0F

                }
                // Root Node
                val entity = RootNode()
                entity.title = "${it.key}"
                entity.childNode = items
                list.add(entity)
            }
        }
        return list
    }


}
