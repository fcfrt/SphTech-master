package com.alanpaine.sphtech

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alanpaine.sphtech.bean.ModeData
import com.alanpaine.sphtech.bean.RecordsData
import com.alanpaine.sphtech.bean.section.ItemNode
import com.alanpaine.sphtech.bean.section.RootNode
import com.alanpaine.sphtech.help.FcfrtDataHelper
import com.alanpaine.sphtech.help.http.ErrorInfo
import com.alanpaine.sphtech.utils.ApiUrl
import com.chad.library.adapter.base.entity.node.BaseNode
import com.google.gson.Gson
import junit.framework.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.litepal.LitePal
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.utils.GsonUtil
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FcfrtDataUnitTest {

    var appContext: Context? = null
    var data: MutableList<RecordsData>? = null

    @Before
    fun setupContext() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.alanpaine.sphtech", appContext?.packageName)
    }

    @Before
    fun setupData() {
        data = mutableListOf()
    }

    /**
     * 测试运行是否正常
     */
    @Test
    fun testSaveRecordsListData() {
        //测试list对象为空
        FcfrtDataHelper.saveRecordsListData(data)
        //测试list不为空但无数据
        data = mutableListOf()
        FcfrtDataHelper.saveRecordsListData(data)
        //测试list中某条数据为空
        for (index in 1..10) {
            val recordsData = RecordsData()
            if (index != 2) {
                recordsData._id = index
                recordsData.volume_of_mobile_data = index.toString()
                recordsData.quarter = (2008+index).toString()
            }
            data?.add(recordsData)
        }
        FcfrtDataHelper.saveRecordsListData(data)
        //测试list中某条数据的_id字段为空
        data?.clear()
        for (index in 1..10) {
            val recordsData = RecordsData()
//                recordsData._id = index
            recordsData.volume_of_mobile_data = index.toString()
            recordsData.quarter = (2008+index).toString()
            data?.add(recordsData)
        }
        FcfrtDataHelper.saveRecordsListData(data)
        //测试list中某两条数据的_id字段相同
        data?.clear()
        for (index in 1..10) {
            val recordsData = RecordsData()
            if (index == 1 || index == 2) {
                recordsData._id = 0
            }
            recordsData.volume_of_mobile_data = index.toString()
            recordsData.quarter = (2008+index).toString()
            data?.add(recordsData)
        }
        FcfrtDataHelper.saveRecordsListData(data)
    }





    /**
     * 测试获取离线数据对数据分组
     */
    @Test
    fun testGroupEntity(){
        FcfrtDataHelper.getData(callBack = {
            val byLength = it.groupBy { it.quarter?.split("-")?.get(0) }
            log("testGroupEntity",GsonUtil.toJson(byLength))
        })
    }



    private fun log(tag:String,message: String) {
        println("$tag => $message")
    }



}
