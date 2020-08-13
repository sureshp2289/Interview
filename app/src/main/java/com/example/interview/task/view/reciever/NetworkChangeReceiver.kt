package com.example.interview.task.view.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.interview.task.`interface`.Connectivity
import com.example.interview.task.utils.NetworkCheck

class NetworkChangeReceiver(private var listener: Connectivity) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        var boolean= NetworkCheck.isInternetAvailable(context)
        listener.onConnectec(boolean)


    }


}