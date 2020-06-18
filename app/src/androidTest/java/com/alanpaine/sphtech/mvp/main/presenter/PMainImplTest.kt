package com.alanpaine.sphtech.mvp.main.presenter
import com.alanpaine.sphtech.bean.ModeData
import com.alanpaine.sphtech.bean.RecordsData
import com.alanpaine.sphtech.bean.section.ItemNode
import com.alanpaine.sphtech.bean.section.RootNode
import com.alanpaine.sphtech.help.FcfrtDataHelper
import com.alanpaine.sphtech.help.http.ErrorInfo
import com.alanpaine.sphtech.utils.ApiUrl
import com.chad.library.adapter.base.entity.node.BaseNode
import org.junit.Before
import org.junit.Test
import org.litepal.LitePal
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.utils.GsonUtil
import java.util.*

class PMainImplTest {

    @Before
    fun setUp() {

    }

    @Test
    fun datastoreSearch() {
        val map = TreeMap<String, String>()
        map["resource_id"] = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"//资源id
        RxHttp.get(ApiUrl.DATASTORE_SEARCH)
            .addAll(map)
            .subscribeOnCurrent()
            .asDataParser(ModeData::class.java)
            .doOnSubscribe {    log("┌─── datastoreSearch  => 开始 ────────────────────────────────") }
            .doFinally { log("└─── datastoreSearch  => 结束 ────────────────────────────────") }
            .subscribe({ data ->
                data.records?.let {
                    log("|\t┌─── datastoreSearch  服务器数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                }
            }, {//失败就获取离线数据
                val error = ErrorInfo(it)
                log("|\t┌─── datastoreSearch  失败=> ${error.errorMsg}  ────────────────────────────────")
                FcfrtDataHelper.getData(callBack = {
                    log("|\t└─── getGroupEntity  离线数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                })

            }).toString()
    }

    /**
     * 测试数据分组结果 (test data grouping results)
     * 对数据按照年进行分组 (group data by year)
     */
    @Test
    fun getGroupEntity() {
        val map = TreeMap<String, String>()
        map["resource_id"] = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"//资源id
        RxHttp.get(ApiUrl.DATASTORE_SEARCH)
            .addAll(map)
            .subscribeOnCurrent()
            .asDataParser(ModeData::class.java)
            .doOnSubscribe {    log("┌─── getGroupEntity  => 开始 ────────────────────────────────") }
            .doFinally { log("└─── getGroupEntity  => 结束 ────────────────────────────────") }
            .subscribe({ data ->
                data.records?.let {
                    log("|\t┌─── getGroupEntity  服务器数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                    val byLength = it.groupBy { it.quarter?.split("-")?.get(0) }
                    log("|\t┌─── getGroupEntity  分组数据=> ${GsonUtil.toJson(byLength)} ────────────────────────────────")
                }
            }, {//失败就获取离线数据
                val error = ErrorInfo(it)
                log("|\t┌─── getGroupEntity  失败=> ${error.errorMsg}  ────────────────────────────────")
                //log("testDatastoreSearch","失败原因：${error.errorMsg}")
                FcfrtDataHelper.getData(callBack = {
                    log("|\t└─── getGroupEntity  离线数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                    val byLength = it.groupBy { it.quarter?.split("-")?.get(0) }
                    log("|\t┌─── getGroupEntity  分组数据=> ${GsonUtil.toJson(byLength)} ────────────────────────────────")
                    //log("testGroupEntity",GsonUtil.toJson(byLength))
                })

            }).toString()
    }

    private fun log(message: String) {
        println(message)
    }
}