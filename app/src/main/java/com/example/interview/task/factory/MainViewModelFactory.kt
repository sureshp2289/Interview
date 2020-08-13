package com.k12.mvvmsample.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interview.task.`interface`.CallFragment
import com.example.interview.task.`interface`.ServerResult
import com.example.interview.task.viewmodel.MainViewModel
import com.example.interview.task.viewmodel.ServerListViewModel

class MainViewModelFactory(private var result: CallFragment): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java!!)) {
            MainViewModel(result) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
