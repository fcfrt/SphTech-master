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
    FcfrtBasePresenter<CMain.IVMain, MMainImpl>(mContext, mView,
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
                     if(data.isNotEmpty()){
                         mView?.onSuccess(data)
                     }else{
                         mView?.onFailure(error.errorMsg.toString())
                     }
                 }
             }).toString()
    }


     fun getEntity(data: List<RecordsData>): List<BaseNode>? {
        val list: MutableList<BaseNode> = ArrayList()
        for (records in data){
            if (records.quarter!=null) {
                var items: MutableList<BaseNode> = ArrayList()
                val year: Int = records.quarter?.substring(0,4)?.toInt()?:0
                if (year in 2008..2018){
                    val itemEntity = ItemNode()
                    itemEntity._id = records._id
                    itemEntity.quarter = records.quarter
                    itemEntity.volume_of_mobile_data = records.volume_of_mobile_data
                    itemEntity.year = year
                    items.add(itemEntity)
                    // Root Node
                    val entity = RootNode(items, "$year")
                    list.add(entity)
                }
            }
        }

        return list
    }


}
