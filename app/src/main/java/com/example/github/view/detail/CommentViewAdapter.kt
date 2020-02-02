package com.example.github.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.databinding.CommentItemListBinding
import com.example.github.model.CommentsModel

class CommentViewAdapter(val commentList: ArrayList<CommentsModel>) :
    RecyclerView.Adapter<CommentViewAdapter.CommentViewHolder>() {
    fun updateIssuesList(newCommentList: List<CommentsModel>) {
        commentList.clear()
        commentList.addAll(newCommentList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CommentItemListBinding>(
            layoutInflater,
            R.layout.comment_item_list, parent, false
        )
        return CommentViewHolder(view)
    }

    override fun getItemCount() = commentList.size


    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.view.comment = commentList[position]
    }

    class CommentViewHolder(var view: CommentItemListBinding) : RecyclerView.ViewHolder(view.root) {

    }
}