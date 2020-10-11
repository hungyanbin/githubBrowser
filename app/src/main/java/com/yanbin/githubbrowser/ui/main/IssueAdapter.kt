package com.yanbin.githubbrowser.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yanbin.githubbrowser.R
import com.yanbin.githubbrowser.model.Issue
import java.time.format.DateTimeFormatter

class IssueAdapter: RecyclerView.Adapter<IssueViewHolder>() {

    val issues = mutableListOf<Issue>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issues, parent, false)
        return IssueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item = issues[position]
        holder.bind(item)
    }
}

class IssueViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val textTitle = view.findViewById<TextView>(R.id.textTitle)
    private val textDate = view.findViewById<TextView>(R.id.textDate)

    fun bind(item: Issue) {
        textTitle.text = item.title
        textDate.text = item.openedDate.format(DateTimeFormatter.ISO_DATE)
    }

}