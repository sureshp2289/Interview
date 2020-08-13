package com.example.interview.task.viewmodel

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.interview.R
import com.example.interview.task.`interface`.CallFragment
import com.example.interview.task.`interface`.Connectivity

class MainViewModel(private var connectivity: CallFragment):ViewModel() {
    init {
       connectivity.check(true)
    }
}