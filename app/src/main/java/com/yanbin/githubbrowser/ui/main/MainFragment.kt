package com.yanbin.githubbrowser.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yanbin.githubbrowser.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val adapter = RepoAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = GithubViewModelFactory(context!!)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.repoLiveData
            .observe(viewLifecycleOwner, Observer { repos ->
                adapter.repos.addAll(repos)
                adapter.notifyDataSetChanged()
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

}

class GithubViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val database = GithubDatabase.getDatabase(context)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val githubRepoRepository = GithubRepoRepository(database.repoDao())
        return MainViewModel(githubRepoRepository) as T
    }
}