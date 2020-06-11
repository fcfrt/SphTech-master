package com.alanpaine.sphtech.mvp.main.adapter.section.provider

import android.view.View
import com.alanpaine.sphtech.R
import com.alanpaine.sphtech.bean.section.ItemNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class SecondNodeProvider : BaseNodeProvider(){
    override val itemViewType: Int
        get() = 1
    override val layoutId: Int
        get() = R.layout.item_main_yearly

    override fun convert(helper: BaseViewHolder, data: BaseNode) {
        val entity = data as ItemNode
        helper.setText(R.id.tv_year,"Year: ${entity.quarter}")
        helper.setText(R.id.tv_volume,"Volume: ${entity.volume_of_mobile_data}")
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        super.onClick(helper, view, data, position)
    }

}