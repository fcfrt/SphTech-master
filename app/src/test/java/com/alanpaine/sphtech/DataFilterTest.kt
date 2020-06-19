package com.alanpaine.sphtech

import com.alanpaine.sphtech.bean.RecordsData
import org.junit.Before
import org.junit.Test
import rxhttp.wrapper.utils.GsonUtil
import kotlin.random.Random

class DataFilterTest {


    lateinit var mData: MutableList<RecordsData>
    fun getYearRange() : Int {
        return (1000 .. 2000).random()
    }

    @Before
    fun setup() {
        mData = ArrayList()
        val count = (50 .. 100).random()
        var year = getYearRange();
        for (i in 1..count) {
            val spy = RecordsData()
            val quarter = (1..4).random().coerceAtLeast(1)
            spy.quarter = String.format("%s-Q%s", year, quarter)
            spy.volume_of_mobile_data = Random.nextDouble().toString()
            mData.add(spy)
            year = if (i % 4 == 0) getYearRange() else year
        }
    }


    /**
     * 测试从数据集合中筛选出给定的开始时间到结束时间的数据（test filter data in given start and end）
     * step 1: 模拟startAt、endAt，（mock startAt & endAt range）
     * step 2: 检查筛选结果 （assert the result equal to expect）
     * step 4: 对比数据，确保筛选的结果数据和被过滤的数据合起来等于总数据（assert filtered and result data equal to total count）
     */
    @Test
    fun testFilterYear() {
        //get random startAt and endAt
        val mStart: Int = mData[(0 .. mData.size).random()].quarterYearNum
        val mEndAt: Int = mData[(0 .. mData.size).random()].quarterYearNum
        val startAt = mStart.coerceAtMost(mEndAt)
        val endAt = mStart.coerceAtLeast(mEndAt)

        log("┌─── testFilterYear $startAt => $endAt ────────────────────────────────")

        log("|\t-─── testFilterYear  模拟数据=> ${GsonUtil.toJson(mData)} ────────────────────────────────")
        val byLength = mData.groupBy { it.quarter?.split("-")?.get(0) }
        log("|\t-─── testFilterYear  分组数据=> ${GsonUtil.toJson(byLength)} ────────────────────────────────")
        byLength.forEach {
            if (it.key?.toInt() in startAt..endAt) {
                log("|\t┌─── after filter ${it?.key} year size:${it.value.size} ────────")
                it.value.forEach { its ->
                    log("|\t| data ${GsonUtil.toJson(its)}")
                }

                log("|\t└────────────────────────────────────────────────")
            }
        }


        log("└─── TEST PASS ──────────────────────────────────────────────────")
    }



    @Test
    fun testFilterYearGroupData() {
        //get random startAt and endAt
        val mStart: Int = mData[(0 .. mData.size).random()].quarterYearNum
        val mEndAt: Int = mData[(0 .. mData.size).random()].quarterYearNum
        val startAt = mStart.coerceAtMost(mEndAt)
        val endAt = mStart.coerceAtLeast(mEndAt)

        log("┌─── testFilterYearGroupData $startAt => $endAt ────────────────────────────────")

        log("|\t-─── testFilterYearGroupData  模拟数据=> ${GsonUtil.toJson(mData)} ────────────────────────────────")
        val byLength = mData.groupBy { it.quarter?.split("-")?.get(0) }
        log("|\t-─── testFilterYearGroupData  分组数据=> ${GsonUtil.toJson(byLength)} ────────────────────────────────")
        byLength.forEach {
            var yearVolume = 0F
            if (it.key?.toInt() in startAt..endAt) {
                log("|\t┌─── after filter Group ${it?.key} year size:${it.value.size} ────────")
                log("|\t| quarter \t\t\t| usage \t\t\t")
                it.value.forEach { its ->
                    yearVolume += its.volume_of_mobile_data?.toFloat() ?: 0F
                    log("|\t| ${its.quarter} \t\t\t| ${its.volume_of_mobile_data} \t\t\t")
                }
                log("|\t└─── total total $yearVolume ───────────────")
            }
        }


        log("└─── TEST PASS ──────────────────────────────────────────────────")
    }
    private fun log(message: String) {
        println(message)
    }
}