package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val data = inputData.getString(WORK_DATA_KEY)
//        Log.d("TAG", "doWork: $data start")
        Thread.sleep(3000)
        val sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
        var number = sp.getInt(data,0)
        sp.edit().putInt(data,++number).apply()
//        Log.d("TAG", "doWork: $data end")
        return Result.success(workDataOf(OUTPUT_DATA_KEY to "$data output"))
    }
}