package com.demo.jobschedulerdemoapp

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val TAG : String = "MainActivityClass"
    lateinit var scheduler: JobScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initJob()
    }

    fun initJob() {
        scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    fun scheduleJob(view: View) {
        var componentName: ComponentName = ComponentName(this, ExampleJobService::class.java)
        var info: JobInfo? = JobInfo.Builder(123, componentName)
            .setRequiresCharging(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setPersisted(true)
            .setPeriodic(15*60*1000)
            .build()
        var resultCode: Int? = info?.let { scheduler.schedule(it) }
        if(resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "JobScheduled")
            Toast.makeText(this, "Job Scheduled", Toast.LENGTH_SHORT).show()
        } else {
            Log.d(TAG,"Job Scheduling failed")
            Toast.makeText(this, "Job Scheduling failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun cancelJob(view: View) {
        scheduler.cancel(123)
        Log.d(TAG,"cancelJob")
    }
}