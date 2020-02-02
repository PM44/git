package com.example.github.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.databinding.ListItemBinding
import com.example.github.model.GitApiModel
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat


class IssuesViewAdapter(val issuesList: ArrayList<GitApiModel>) :
    RecyclerView.Adapter<IssuesViewAdapter.IssuesViewHolder>(),
    IssueClickListener {
    val df = SimpleDateFormat("dd-MMM-yyyy")
    override fun onIssueClicked(v: View) {
        var bundle = bundleOf("issue" to v.number.text.toString())
        Navigation.findNavController(v).navigate(R.id.action_listFragment_to_detailFragment, bundle)
    }

    fun updateIssuesList(newIssuesList: List<GitApiModel>) {
        issuesList.clear()
        issuesList.addAll(newIssuesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListItemBinding>(
            layoutInflater,
            R.layout.list_item, parent, false
        )
        return IssuesViewHolder(view)
    }

    override fun getItemCount() = issuesList.size


    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        val newFormattedDate = SimpleDateFormat("dd MMM yyyy").format(issuesList[position].getDate())
        holder.view.issues = issuesList[position]

        holder.view.postid.text =
            "#" + issuesList[position].number + " last opened " + newFormattedDate + " by " + issuesList[position].user?.login
        if (issuesList[position].comments > 0)
            holder.view.commentCount.text = issuesList[position].comments.toString()
        else {
            holder.view.commentCount.visibility = View.GONE
            holder.view.messageIcon.visibility = View.GONE
        }
        holder.view.listener = this


    }

    inner class IssuesViewHolder(var view: ListItemBinding) : RecyclerView.ViewHolder(view.root) {

    }
}