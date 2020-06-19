package com.alanpaine.sphtech.bean

import com.chad.library.adapter.base.entity.node.BaseNode
import org.litepal.crud.LitePalSupport
import java.io.Serializable
import java.util.regex.Pattern

/**
 * 项目名称：
 * 类描述：
 * 作者：AlanPaine
 * 创建时间： 2020-06-11
 * 邮箱：CQZZ_ZZPT@163.COM
 * 修改简介：
 */

class RecordsData: LitePalSupport(), Serializable {
    var _id:Int=0
    var volume_of_mobile_data: String? = null//数据
    var quarter: String? = null//时间

    var quarterYearNum = 0
        get() {
            if (quarter != null && quarter!!.isNotEmpty()) {
                val pattern = Pattern.compile("[0-9]{3,4}")
                val matcher = pattern.matcher(quarter)
                field = (if (matcher.find()) matcher.group(0) else "0").toInt()
            }
            return field
        }
        private set

}
