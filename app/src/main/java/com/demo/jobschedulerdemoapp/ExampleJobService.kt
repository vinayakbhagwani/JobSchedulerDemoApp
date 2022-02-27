package com.demo.jobschedulerdemoapp

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class ExampleJobService : JobService() {

    val TAG : String = "ExampleJobService"
    var jobCancelled: Boolean = false

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG,"Job Started")
        doBackgroundWork(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG,"Job Cancelled before completion")
        jobCancelled = true
        return false
    }

    private fun doBackgroundWork(params: JobParameters?) {
        Thread(Runnable(){
            for(i in 1..10) {
                if(!jobCancelled) {
                    Log.d(TAG, "run: "+i)
                    Thread.sleep(1000)
                }
                else{
                    break;
                }
            }
            Log.d(TAG, "Job finished")
            jobFinished(params,false)
        }).start()
    }
}