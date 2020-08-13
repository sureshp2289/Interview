package com.example.interview.task.viewmodel


import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interview.task.`interface`.ServerResult
import com.example.interview.task.datamodel.ListModel
import com.example.interview.task.repository.ListRepository
import com.example.interview.task.utils.NetworkCheck


class ServerListViewModel(
    private var context: Context,
    private var serverResult: ServerResult
) : ViewModel() {
    lateinit var arraylist: ArrayList<ListModel>;
    var recyclerview: ObservableField<Int>? = null
    var nodatafound: ObservableField<Int>? = null
    var swipeRefreshLayout: ObservableField<Boolean>? = null
    private var repository: ListRepository? = null
    private var mainlist = MutableLiveData<ArrayList<ListModel>>();


    init {
        arraylist= ArrayList<ListModel>()
        recyclerview = ObservableField()
        nodatafound = ObservableField()
        swipeRefreshLayout = ObservableField()
        recyclerview!!.set(View.GONE)
        nodatafound!!.set(View.VISIBLE)
        swipeRefreshLayout!!.set(false)
        repository = swipeRefreshLayout?.let { ListRepository(it, context,mainlist) };

    }

    fun refreshdata() {
        var result = object : ServerResult {
            override fun success(result: MutableLiveData<ArrayList<ListModel>>) {
                if (result.value!!.size > 0) {
                    recyclerview!!.set(View.VISIBLE)
                    nodatafound!!.set(View.GONE)
                    serverResult.success(result)
                } else {
                    recyclerview!!.set(View.GONE)
                    nodatafound!!.set(View.VISIBLE)
                }
            }
        }
        if (NetworkCheck.isInternetAvailable(context)) {
            swipeRefreshLayout!!.set(true)
            repository!!.getOnline(result)
        } else {
            swipeRefreshLayout!!.set(true)
            repository!!.getOffline(result)
        }
    }

    fun destroyView() {
        repository!!.destroy()
    }

}