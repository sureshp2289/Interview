package com.k12.mvvmsample.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interview.task.`interface`.ServerResult
import com.example.interview.task.viewmodel.ServerListViewModel

class SampleViewModelFactory(private var context: Context,private var result: ServerResult): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ServerListViewModel::class.java!!)) {
            ServerListViewModel(context,result) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
