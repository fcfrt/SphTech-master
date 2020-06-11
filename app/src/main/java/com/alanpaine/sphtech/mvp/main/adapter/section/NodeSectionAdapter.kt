package com.alanpaine.sphtech.mvp.main.adapter.section

import com.alanpaine.sphtech.bean.section.ItemNode
import com.alanpaine.sphtech.bean.section.RootNode
import com.alanpaine.sphtech.mvp.main.adapter.section.provider.RootNodeProvider
import com.alanpaine.sphtech.mvp.main.adapter.section.provider.SecondNodeProvider
import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode

class NodeSectionAdapter : BaseNodeAdapter() {
    override fun getItemType(
        data: List<BaseNode>,
        position: Int
    ): Int {
        val node = data[position]
        if (node is RootNode) {
            return 0
        } else if (node is ItemNode) {
            return 1
        }
        return -1
    }

    init {
        addFullSpanNodeProvider(RootNodeProvider())
        addNodeProvider(SecondNodeProvider())
    }
}