package com.alanpaine.sphtech.mvp.main.presenter
import com.alanpaine.sphtech.bean.section.ItemNode
import com.alanpaine.sphtech.bean.section.RootNode
import com.alanpaine.sphtech.help.FcfrtDataHelper
import com.alanpaine.sphtech.help.http.ErrorInfo
import com.alanpaine.sphtech.mvp.main.contract.CMain
import com.alanpaine.sphtech.mvp.main.model.MMainImpl
import com.chad.library.adapter.base.entity.node.BaseNode
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import org.junit.Before
import org.junit.Test
import rxhttp.wrapper.utils.GsonUtil
import java.util.ArrayList

class PMainImplTest {
    @MockK
    lateinit var mModel: MMainImpl

    lateinit var mView: CMain.IVMain


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mModel = MMainImpl()
        mView = spyk()
    }
    @Test
    fun datastoreSearch() {
        log("┌─── test datastoreSearch ───────────────────────────────────────")
        mModel.datastoreSearch("a807b7ab-6cad-4aa6-87d0-e283a7353a0f",true)
            .doOnSubscribe {
                log("|\t┌─── datastoreSearch  => 开始 ────────────────────────────────")
                mView.showLoading()
            }
            .doFinally {
                log("|\t└─── datastoreSearch  => 结束 ────────────────────────────────")
                mView.hideLoading()
            }
            .subscribe({ data ->
                data.records?.let {
                    log("|\t-─── datastoreSearch  服务器数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                    mView.onSuccess(it)
                }
            }, {//失败就获取离线数据
                val error = ErrorInfo(it)
                log("|\t-─── datastoreSearch  请求失败=> ${error.errorMsg}  ────────────────────────────────")
                FcfrtDataHelper.getData(callBack = {
                    log("|\t-─── getGroupEntity  离线数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                    mView.onSuccess(it)
                })

            }).toString()
        log("└──────────────────────────────────────────────────────────────")
    }



    /**
     * 测试数据分组结果 (test data grouping results)
     * 对数据按照年进行分组 (group data by year)
     */
    @Test
    fun getGroupEntity() {
        log("┌─── test getGroupEntity ───────────────────────────────────────")
        mModel.datastoreSearch("a807b7ab-6cad-4aa6-87d0-e283a7353a0f",true)
            .doOnSubscribe {    log("|\t┌─── getGroupEntity  => 开始 ────────────────────────────────") }
            .doFinally { log("|\t└─── getGroupEntity  => 结束 ────────────────────────────────") }
            .subscribe({ data ->
                data.records?.let {
                    log("|\t-─── getGroupEntity  服务器数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                    val byLength = it.groupBy { it.quarter?.split("-")?.get(0) }
                    log("|\t-─── getGroupEntity  分组数据=> ${GsonUtil.toJson(byLength)} ────────────────────────────────")
                    byLength.forEach {
                        var yearVolume = 0F
                        if (it.key?.toInt() in 2008..2018) {
                            log("|\t┌─── after filter ${it?.key} year size:${it.value.size} ────────")
                            log("|\t| quarter \t\t\t| usage \t\t\t")
                            it.value.forEach { its ->
                                yearVolume += its.volume_of_mobile_data?.toFloat() ?: 0F
                                log("|\t| ${its.quarter} \t\t\t| ${its.volume_of_mobile_data} \t\t\t")
                            }
                            log("|\t└─── total total $yearVolume ───────────────")
                        }
                    }
                }
            }, {//失败就获取离线数据
                val error = ErrorInfo(it)
                log("|\t-─── getGroupEntity  失败=> ${error.errorMsg}  ────────────────────────────────")
                //log("testDatastoreSearch","失败原因：${error.errorMsg}")
                FcfrtDataHelper.getData(callBack = {
                    log("|\t-─── getGroupEntity  离线数据=> ${GsonUtil.toJson(it)} ────────────────────────────────")
                    val byLength = it.groupBy { it.quarter?.split("-")?.get(0) }
                    log("|\t-─── getGroupEntity  分组数据=> ${GsonUtil.toJson(byLength)} ────────────────────────────────")
                    //log("testGroupEntity",GsonUtil.toJson(byLength))
                })

            }).toString()
        log("└──────────────────────────────────────────────────────────────")

    }



    private fun log(message: String) {
        println(message)
    }
}