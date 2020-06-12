package com.alanpaine.sphtech.mvp.main.ui.activity
import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.alanpaine.baselib.base.FcfrtBaseActivity
import com.alanpaine.sphtech.R
import com.alanpaine.sphtech.bean.RecordsData
import com.alanpaine.sphtech.help.FcfrtDoubleClickHelper
import com.alanpaine.sphtech.mvp.main.adapter.section.NodeSectionAdapter
import com.alanpaine.sphtech.mvp.main.contract.CMain
import com.alanpaine.sphtech.mvp.main.presenter.PMainImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FcfrtBaseActivity<PMainImpl>(), CMain.IVMain {
    var nodeAdapter:NodeSectionAdapter?=null
    /**
     * 获取布局id
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    /**
     * 创建presenter实例
     */
    override fun createPresenter(): PMainImpl {
        return PMainImpl(this, this)
    }

    /**
     * 界面创建完成
     */
    override fun onViewCreated() {
        initRefreshLayout()
        nodeAdapter = NodeSectionAdapter()
        rv_list.layoutManager = GridLayoutManager(this, 2)
        rv_list.adapter = nodeAdapter
        refresh()

    }

    private fun initRefreshLayout() {
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189))
        swipeLayout.setOnRefreshListener {refresh() }
    }
    private fun refresh(){
        mPresenter?.datastoreSearch("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
    }

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun showLoading() {
        runOnUiThread {
            swipeLayout.isRefreshing = true
        }
    }

    override fun hideLoading() {
        runOnUiThread {
            swipeLayout.isRefreshing = false
        }

    }

    override fun onSuccess(data: List<RecordsData>) {
        runOnUiThread {
            nodeAdapter?.setList(mPresenter?.getGroupEntity(data))
        }
        //showToast("数据：${data.size}")
    }

    override fun onFailure(msg: String) {
        showToast(msg)
    }


    override fun onBackPressed() {
        if (FcfrtDoubleClickHelper.isOnDoubleClick) {
            super.onBackPressed()
        } else {
            showToast("在点一次退出")
        }
    }
}
