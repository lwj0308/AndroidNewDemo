package com.example.workmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 应用被强退后，任务无法被继续调度，只能等下次启动
 */
const val WORK_DATA_KEY = "work_data_key"
const val OUTPUT_DATA_KEY = "output_data_key"
const val WORK_A_NAME = "Work A"
const val WORK_B_NAME = "Work B"
const val SP_NAME = "SP"

class MainActivity : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {
    private val workManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
        updateView()
        button.setOnClickListener {
            val workRequestA = createWorkRequest(WORK_A_NAME)//工作请求
            val workRequestB = createWorkRequest(WORK_B_NAME)//工作请求

//            workManager.enqueue(workRequest)//加入队列
            workManager.beginWith(workRequestA)
                    .then(workRequestB)
                    .enqueue()
            /*  workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
                  Log.i("TAG", "onCreate: " + it.state)
                  if (it.state == WorkInfo.State.SUCCEEDED) {
                      Log.i("TAG", "onCreate: " + it.outputData.getString(OUTPUT_DATA_KEY))
                  }
              })*/
        }

    }

    private fun createWorkRequest(name: String): OneTimeWorkRequest {
        //        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()//约束条件
        return OneTimeWorkRequestBuilder<MyWorker>()
                //                    .setConstraints(constraints)
                .setInputData(workDataOf(WORK_DATA_KEY to name))//输入数据
                .build()
    }

    private fun updateView(){
        val sp = getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
        textViewA.text = sp.getInt(WORK_A_NAME,0).toString()
        textViewB.text = sp.getInt(WORK_B_NAME,0).toString()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        updateView()
    }
}
