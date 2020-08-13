package com.example.interview.task.view.fragment

import android.app.SearchManager
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.interview.R
import com.example.interview.databinding.FragmentServerlistBinding
import com.example.interview.task.`interface`.Connectivity
import com.example.interview.task.`interface`.ServerResult
import com.example.interview.task.datamodel.ListModel
import com.example.interview.task.view.InterviewActivity
import com.example.interview.task.view.adapter.ServerListAdapter
import com.example.interview.task.viewmodel.ServerListViewModel
import com.k12.mvvmsample.factory.SampleViewModelFactory
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServerListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentServerlistBinding
    private lateinit var viewmodel: ServerListViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var serverResult= object : ServerResult {
            override fun success(result: MutableLiveData<ArrayList<ListModel>>) {
                viewmodel.arraylist.clear()
                result.value?.let { viewmodel.arraylist.addAll(it) }
                adapter.check(viewmodel.arraylist)
                adapter.notifyDataSetChanged()
            }
        }
        viewmodel = ViewModelProvider(this, SampleViewModelFactory(activity!!,serverResult)).get(ServerListViewModel::class.java)
        val connectvity = object : Connectivity {
            override fun onConnectec(boolean: Boolean) {
                viewmodel.refreshdata()
            }
        }
        (activity as InterviewActivity?)!!.setConnect(connectvity)
        retainInstance=true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_serverlist, container, false)
        var view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val scale = context!!.resources.displayMetrics.density
        dpWidthInPx = ((100 * scale).toInt())
        adapter = activity?.let { ServerListAdapter(viewmodel.arraylist, it) }!!
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.rvList.layoutManager = linearLayoutManager
        binding.rvList.adapter = adapter
        binding.service = viewmodel
        binding.setVariable(BR.service, viewmodel)
        binding.lifecycleOwner=this
        binding.executePendingBindings()
        binding.swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewmodel.refreshdata()
        });

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewmodel.destroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        @JvmStatic
        private lateinit var adapter: ServerListAdapter;

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        private var dpWidthInPx: Int = 0

        @JvmStatic
        fun newInstance(param1: String) =
            ServerListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }

        @JvmStatic
        @BindingAdapter(value = ["imageUrl"], requireAll = false)
        fun loadimage(view: ImageView, imageurl: String) {
            val layoutParams = ConstraintLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
            view.setLayoutParams(layoutParams)
            if (imageurl.length > 0) {
                Picasso.get().load(imageurl)
                    .error(R.drawable.gradient)
                    .into(view)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)

        for (i in 0 until menu.size()) {
            val drawable = menu.getItem(i).icon
            if (drawable != null) {
                drawable.mutate()
                drawable.setColorFilter(resources.getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP)
            }
        }
        // Associate searchable configuration with the SearchView
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.action_search).getActionView() as SearchView
        searchView.setQueryHint("Search")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                // do your search
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                // do your search on change or save the last string or...
                if (binding.rvList.adapter != null) {
                    val i = text?.let { adapter.filter(it) }
                    if (i!! > 0) {
                        binding.nodatatext.visibility = View.GONE
                        binding.rvList.visibility = View.VISIBLE
                    } else {
                        binding.nodatatext.visibility = View.VISIBLE
                        binding.rvList.visibility = View.GONE
                    }
                }
                return true
            }
        })

    }

}
