package com.alanpaine.sphtech.bean.section

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

class RootNode : BaseExpandNode(){

    var title:String?=null
    /**
     * 重写此方法，获取子节点。如果没有子节点，返回 null 或者 空数组
     *
     * 如果返回 null，则无法对子节点的数据进行新增和删除等操作
     */
    override var childNode: MutableList<BaseNode>?=null



}