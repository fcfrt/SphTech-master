package com.alanpaine.sphtech.bean

import java.io.Serializable

/**
 * 项目名称：
 * 类描述：
 * 作者：AlanPaine
 * 创建时间： 2020-06-11
 * 邮箱：CQZZ_ZZPT@163.COM
 * 修改简介：
 */

class ModeData : Serializable {
    var fields: String? = null//-1(数据异常)/0(没有数据)/1(获取成功)
    var records: List<RecordsData>? = null//

}
