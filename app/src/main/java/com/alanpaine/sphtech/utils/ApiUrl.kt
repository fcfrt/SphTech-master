package com.alanpaine.sphtech.utils

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

/**
 * 项目名称：FcfrtCampusDiningIc-Master
 * 类名称：com.fcfrt.cdic.util
 * 类描述：
 * 作者：FCFRT
 * 创建时间： 2019/5/13-17:17
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */
class ApiUrl {
    companion object {
        @DefaultDomain //设置为默认域名
        const val BASE_URL = "https://data.gov.sg/"

        val DATASTORE_SEARCH = "api/action/datastore_search"
    }
}
