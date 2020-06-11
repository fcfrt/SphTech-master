package com.alanpaine.sphtech.help.http

import com.alanpaine.sphtech.bean.BaseMode
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser

import java.io.IOException
import java.lang.reflect.Type

/**
 * 数据解析器 ,解析完成对Data对象做判断,如果ok,返回数据 T
 */
@Parser(name = "DataParser", wrappers = [MutableList::class])
open class DataPareser<T> : AbstractParser<T> {

     constructor() : super() {}

     constructor(type: Type) : super(type) {}

    /**
     * @param response Http执行结果
     * @return 开发者传入的泛型类型
     * @throws IOException 网络异常、数据异常等，RxJava的观察者会捕获此异常
     */
    @Throws(IOException::class)
    override fun onParse(response: Response): T {
        val type = ParameterizedTypeImpl[BaseMode::class.java, mType] //获取泛型类型
        val content: BaseMode<T> = convert(response, type)
        var data = content.result
        if (data==null&&mType== String::class.java){
            /*
             * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
             * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
             * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
             */
            @Suppress("UNCHECKED_CAST")
            data = "抱歉没有找到相关数据" as T
        }
        if (content.success!=true||data==null){
            throw ParseException("404", "数据异常没有找到", response)
        }
        return data
    }



    companion object {
        @JvmStatic
        operator fun <T> get(type: Class<T>): DataPareser<T> {
            return DataPareser(type)
        }
    }
}