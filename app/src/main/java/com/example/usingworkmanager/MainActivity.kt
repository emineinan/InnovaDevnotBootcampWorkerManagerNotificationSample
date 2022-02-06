package com.example.usingworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.usingworkmanager.databinding.ActivityMainBinding
import com.example.usingworkmanager.notification.MyWorkerNotification
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMake.setOnClickListener {
            val workingCondition =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val request = OneTimeWorkRequestBuilder<MyWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setConstraints(workingCondition)
                .build()
            WorkManager.getInstance(this@MainActivity).enqueue(request)
        }

        binding.buttonNotification.setOnClickListener {
            val request =
                PeriodicWorkRequestBuilder<MyWorkerNotification>(15, TimeUnit.MINUTES)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .build()
            WorkManager.getInstance(this@MainActivity).enqueue(request)
        }
    }
}