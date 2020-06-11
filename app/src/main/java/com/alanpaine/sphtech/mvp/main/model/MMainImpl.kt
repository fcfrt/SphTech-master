package com.alanpaine.sphtech.mvp.main.model

import com.alanpaine.baselib.base.FcfrtBaseModel
import com.alanpaine.sphtech.bean.ModeData
import com.alanpaine.sphtech.utils.ApiUrl
import io.reactivex.Observable
import rxhttp.wrapper.param.RxHttp
import java.util.*


/**
 * 项目名称：
 * 类名称：MMainImpl
 * 类描述：MainActivity的Model
 * 作者：FCFRT
 * 创建时间：
 * 邮箱：
 * 修改简介：
 */
class MMainImpl : FcfrtBaseModel() {
    fun datastoreSearch(resource_id:String): Observable<ModeData> {
        val map = TreeMap<String, String>()
        map["resource_id"] = resource_id//资源id
        return RxHttp.get(ApiUrl.DATASTORE_SEARCH)
            .addAll(map)
            .asDataParser(ModeData::class.java)
    }
}
