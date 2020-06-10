package com.alanpaine.baselib.base


import android.content.Context


open class FcfrtBasePresenter<V : IFcfrtBaseView, M : FcfrtBaseModel>  {

    var mView: V? = null
    var mModel: M? = null
    var mContext: Context? = null


    constructor (mContext: Context?, mView: V, mModel: M) {
        this.mView = mView
        this.mModel = mModel
        this.mContext = mContext

    }

}