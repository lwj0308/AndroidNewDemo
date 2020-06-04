package com.example.gallery

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

//外部不可通过构造器创建实例
class VolleySingleton private constructor(context: Context) {
    //类似static
    companion object {
        private var INSTANCE: VolleySingleton? = null
        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    //it等于前面返回的对象
                    VolleySingleton(context).also { INSTANCE = it }
                }
    }
    val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

}