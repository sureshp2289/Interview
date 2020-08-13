package com.example.interview.task.`interface`

import androidx.lifecycle.MutableLiveData
import com.example.interview.task.datamodel.ListModel

interface ServerResult {
    fun success(result: MutableLiveData<ArrayList<ListModel>>)
}