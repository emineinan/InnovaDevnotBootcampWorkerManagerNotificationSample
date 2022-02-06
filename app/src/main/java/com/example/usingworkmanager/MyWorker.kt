package com.example.usingworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val addition = 10 + 20
        Log.e("Background process result", addition.toString())
        return Result.success()
    }
}