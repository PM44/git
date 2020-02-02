package com.example.github.view.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.databinding.FragmentDetailBinding
import com.example.github.view_model.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {
    private lateinit var databinding: FragmentDetailBinding
    var issue: String? = null
    private lateinit var viewModel: DetailViewModel
    private val commentListAdapter = CommentViewAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            issue = it.getString("issue")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentListAdapter
        }
        viewModel.fetchDataFromDatabase(issue?.toInt())
        viewModel.fetchDataFromRemote(issue?.toInt())
        observeViewModel()
    }


    private fun observeViewModel() {


        viewModel.selectedIssue.observe(this, Observer { selectedIssue ->
            databinding.issue = selectedIssue
            openedDate.text = "Opened by " + selectedIssue?.user?.login + " " + selectedIssue?.comments + " comment"
            if (!(selectedIssue?.comments!! > 0)) {
                openPopup()
            }
        })
        viewModel.comments.observe(this, Observer { comments ->
            comments?.let {
                commentListAdapter.updateIssuesList(comments)
            }
        })

    }

    private fun openPopup() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage("There are no comments")
            .setCancelable(false)
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.show()
    }
}
