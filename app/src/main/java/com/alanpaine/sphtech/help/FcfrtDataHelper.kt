package com.alanpaine.sphtech.help
import android.util.Log
import com.alanpaine.sphtech.bean.RecordsData
import org.litepal.LitePal

object FcfrtDataHelper {



    fun saveRecordsListData(data: List<RecordsData>?) {
        if (data!=null) {
            if (data.isNotEmpty()) {
                for (list in data) {//遍历所有数据
                    val recordsList =
                        LitePal.where("id = ? and quarter = ? ", "${list._id}" , list.quarter + "")
                            .find(RecordsData::class.java)//查询当前_id quarter在数据库中是否存在
                        //Log.v("FcfrtDataHelper","数据库中id =${list._id}的数据有：${recordsList.size}条" )
                    if (recordsList.size > 0) {//大于0代表存在
                        list.updateAll(
                            "id = ? and quarter = ?",
                            "${list._id}",
                            list.quarter + ""
                        )//更新所有_id quarter对应的数据
                        Log.v("FcfrtDataHelper","更新数据${list._id}" )
                    } else {//如果不存在就保存
                        Log.v("FcfrtDataHelper","保存数据${list._id}" )
                        list.save()
                    }

                }
            }
        }
    }

    fun getDataAsync(callBack:(List<RecordsData>)->Unit){
        LitePal.findAllAsync(RecordsData::class.java).listen{ data->
            callBack(data)
        }
    }
    fun getData(callBack:(List<RecordsData>)->Unit){
        callBack(LitePal.findAll(RecordsData::class.java))
    }


}