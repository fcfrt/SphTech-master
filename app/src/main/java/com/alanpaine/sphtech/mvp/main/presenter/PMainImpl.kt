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

    /**
     * 根据返回对数据进行分组分类
     */
    fun getGroupEntity(data: List<RecordsData>): List<BaseNode>? {
        val list: MutableList<BaseNode> = ArrayList()
        val byLength = data.groupBy { it.quarter?.split("-")?.get(0) }
        byLength.forEach {
            var items: MutableList<BaseNode> = ArrayList()
            var yearVolume = 0F
            var quarterVolume = 0f
            var isDecreased: Boolean
            var isAllDecreased = true
            if (it.key?.toInt() in 2008..2018) {
                it.value.forEach { its ->
                    if (quarterVolume > its.volume_of_mobile_data?.toFloat() ?: 0F) {
                        isDecreased = false
                        isAllDecreased = false
                    } else {
                        isDecreased = true
                    }
                    quarterVolume = its.volume_of_mobile_data?.toFloat() ?: 0F
                    yearVolume += its.volume_of_mobile_data?.toFloat() ?: 0F
                    val itemEntity = ItemNode()
                    itemEntity._id = its._id
                    itemEntity.quarter = its.quarter
                    itemEntity.isDecreased = isDecreased
                    itemEntity.volume_of_mobile_data = its.volume_of_mobile_data
                    items.add(itemEntity)
                }
                // Root Node
                val entity = RootNode()
                entity.title = "${it.key}"
                entity.isDecreased = isAllDecreased
                entity.yearVolume = yearVolume
                entity.quarterVolume = quarterVolume
                entity.childNode = items
                //根据下降数据来控制展开与不展开
                entity.isExpanded = !isAllDecreased
                list.add(entity)
            }
        }
        return list
    }


}
