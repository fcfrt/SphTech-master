package com.alanpaine.sphtech.help

import android.os.SystemClock

/**
 * 项目名称：
 * 类名称：FcfrtDoubleClickHelper
 * 类描述：防双击判断工具类
 * 作者：FCFRT
 * 创建时间： 2019/5/24-15:33
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */
object FcfrtDoubleClickHelper {

    private val TIME_ARRAY = LongArray(2) // 数组的长度为2代表只记录双击操作

    /**
     * 是否在短时间内进行了双击操作
     */
    // 默认间隔时长
    val isOnDoubleClick: Boolean
        get() = isOnDoubleClick(1500)

    /**
     * 是否在短时间内进行了双击操作
     */
    fun isOnDoubleClick(time: Int): Boolean {
        System.arraycopy(
            TIME_ARRAY, 1,
            TIME_ARRAY, 0, TIME_ARRAY.size - 1)
        TIME_ARRAY[TIME_ARRAY.size - 1] = SystemClock.uptimeMillis()
        return TIME_ARRAY[0] >= SystemClock.uptimeMillis() - time
    }
}