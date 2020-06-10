package com.alanpaine.baselib.base

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar


abstract class FcfrtBaseFragment<T: FcfrtBasePresenter<*, *>> : Fragment() {

    //p层业务
    var mPresenter :T?=null
    /** 根布局  */
    private var mRootView: View? = null
    //是否懒加载
    var isLazyLoad = true

    //是否加载数据（暂时用于第一次加载判断，以后也许会有其他情况）
    private var isNeedLoad = true

    private var isShowedContent = false
    private var mImmersionBar: ImmersionBar? = null//状态栏沉浸

    /**
     * 获取布局id
     */
    abstract fun getLayoutId(): Int
    /**
     * 标题栏
     * @return
     */
     abstract fun getTitleId(): Int



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null && getLayoutId() > 0) {
            mRootView = inflater.inflate(getLayoutId(), null)
        }

        val parent = mRootView?.parent
        if (parent!=null){
            (parent as ViewGroup).removeView(mRootView)
        }


        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isLazyLoad)
            initFragment()
    }



    override fun getView(): View? {
        return mRootView
    }

    protected fun initFragment() {
        onIntentData()
        mPresenter = createPresenter()
        initImmersion()
        onViewCreated()

    }


    /**
     * 获取getIntent();数据
     */
    open fun onIntentData() {

    }
    /**
     * 创建presenter实例
     */
    abstract fun createPresenter():T

    /**
     * 界面创建完成
     */
    abstract fun onViewCreated()


    /**
     * 初始化沉浸式
     */
    fun initImmersion() {

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            statusBarConfig()?.init()
            //
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    open fun isStatusBarEnabled(): Boolean {
        return false
    }

    /**
     * 初始化沉浸式
     */
    open fun statusBarConfig(): ImmersionBar? {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
            .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
            .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        return mImmersionBar
    }

    /**
     * 获取状态栏字体颜色
     */
    open fun statusBarDarkFont(): Boolean {
        //返回true表示黑色字体
        return true
    }



    /**
     * Fragment 返回键被按下时回调
     */
    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // 默认不拦截按键事件，回传给 Activity
        return false
    }
    override fun onResume() {
        super.onResume()

        //如果是第一次且是懒加载
        //执行初始化方法
        if (isNeedLoad && isLazyLoad) {
            initFragment()
            //数据已加载，置false，避免每次切换都重新加载数据
            isNeedLoad = false
        }
        if (isStatusBarEnabled()) {
            // 重新初始化状态栏
            statusBarConfig()?.init()
        }
    }
}