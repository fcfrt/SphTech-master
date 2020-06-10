package com.alanpaine.baselib.base

import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar


abstract class FcfrtBaseActivity<T: FcfrtBasePresenter<*, *>>: AppCompatActivity(),
    ViewTreeObserver.OnGlobalLayoutListener {

    var mPresenter :T?=null
    private var isShowedContent = false
    protected var mImmersionBar: ImmersionBar? = null//状态栏沉浸
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        beforeSetContentView(savedInstanceState)
        onIntentData()//获取数据
        mPresenter=createPresenter()
        initImmersion()
        onViewCreated()//界面创建完成
    }
    /**
     * 在SetContentView之前进行操作，父类空实现，子类根据需要进行实现
     */
    open fun beforeSetContentView(savedInstanceState: Bundle?) {

    }
    /**
     * 获取布局id
     */
    abstract fun getLayoutId(): Int

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
    private fun initImmersion() {
        //初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            statusBarConfig()?.init()
        }
    }

    /**
     * 是否使用沉浸式状态栏
     */
    open fun isStatusBarEnabled(): Boolean {
        return true
    }
    /**
     * 初始化沉浸式状态栏
     */
    open fun statusBarConfig(): ImmersionBar? {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
            .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
            .keyboardEnable(
                false,
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
            )  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(this)
        return mImmersionBar
    }

    override fun onGlobalLayout() {}//不用写任何方法
    /**
     * 获取状态栏字体颜色
     */
    open fun statusBarDarkFont(): Boolean {
        //返回false表示白色字体
        return true
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}