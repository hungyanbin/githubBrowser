package com.yanbin.githubbrowser.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yanbin.githubbrowser.R

class RepoAdapter: RecyclerView.Adapter<RepoViewHolder>() {

    val repos = mutableListOf<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repos, parent, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = repos[position]
        holder.bind(item)
    }
}

class RepoViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val textTitle = view.findViewById<TextView>(R.id.textTitle)
    private val textLanguage = view.findViewById<TextView>(R.id.textLanguage)

    fun bind(item: Repo) {
        textTitle.text = item.title
        textLanguage.text = item.language
    }

}