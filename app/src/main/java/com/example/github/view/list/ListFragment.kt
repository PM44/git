package com.example.github.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.view_model.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val issuesListAdapter = IssuesViewAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()
        issuesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = issuesListAdapter
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.issues.observe(this, Observer { issues ->
            issues?.let {
                issuesListAdapter.updateIssuesList(issues)
            }
        })

        viewModel.loading.observe(this, Observer { loading ->
            if (loading) {
                loadingView.visibility = View.VISIBLE
            } else {
                loadingView.visibility = View.GONE

            }
        })
    }

}
