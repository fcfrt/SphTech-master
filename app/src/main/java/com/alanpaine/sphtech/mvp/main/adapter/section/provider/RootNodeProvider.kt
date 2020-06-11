package com.alanpaine.sphtech.mvp.main.adapter.section.provider

import android.view.View
import com.alanpaine.sphtech.R
import com.alanpaine.sphtech.bean.section.RootNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class RootNodeProvider : BaseNodeProvider(){
    override val itemViewType: Int
        get() = 0
    override val layoutId: Int
        get() = R.layout.def_section_head

    override fun convert(helper: BaseViewHolder, data: BaseNode) {
        val entity = data as RootNode
        helper.setText(R.id.tv_header,entity.title)
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        getAdapter()?.expandOrCollapse(position)
    }


}