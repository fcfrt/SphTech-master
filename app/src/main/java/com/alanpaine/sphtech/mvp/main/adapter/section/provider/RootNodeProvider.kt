package com.alanpaine.sphtech.mvp.main.adapter.section.provider

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
        helper.setText(R.id.tv_year,"${entity.title}年度")
        helper.setText(R.id.tv_volume,"TotalVolume: ${entity.yearVolume}")
        helper.setText(R.id.tv_more,if (entity.isExpanded){"收起 >"}else{"展开 >"})
        //helper.getView<ImageView>(R.id.iv_down).visibility = if (entity.isDecreased){View.GONE}else{View.VISIBLE}
        setViewdrawableLeft(helper.getView(R.id.tv_more),entity.isDecreased)
    }
    /**
     * 设置按钮顶部图片
     *
     * @param view
     * @param resid
     */
    private fun setViewdrawableLeft(view: TextView, visibility: Boolean) {
        if (!visibility) {
            val left: Drawable =
                view.resources.getDrawable(R.drawable.ic_trending_down) // 获取res下的图片drawable
            left.setBounds(0, 0, left.minimumWidth, left.minimumHeight) // 一定要设置setBounds();
            // 调用setCompoundDrawables(Drawable left, Drawable top,Drawable right, Drawable bottom)方法。(有四个参数，不需要设置的参数传null)
            view.setCompoundDrawables(left, null, null, null)
        }else{
            view.setCompoundDrawables(null, null, null, null)
        }
    }
    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        getAdapter()?.expandOrCollapse(position)
    }


}