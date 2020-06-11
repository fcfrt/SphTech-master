package com.alanpaine.sphtech.bean.section

import com.chad.library.adapter.base.entity.node.BaseNode

class ItemNode : BaseNode(){
    var _id:Int=0
    var volume_of_mobile_data: String? = null//数据
    var quarter: String? = null//时间
    var year:Int=0//年
    /**
     * 重写此方法，获取子节点。如果没有子节点，返回 null 或者 空数组
     *
     * 如果返回 null，则无法对子节点的数据进行新增和删除等操作
     */
    override val childNode: MutableList<BaseNode>?
        get() = null

}