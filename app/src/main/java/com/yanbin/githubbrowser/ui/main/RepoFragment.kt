package com.yanbin.githubbrowser.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yanbin.githubbrowser.R
import kotlinx.android.synthetic.main.repo_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by sharedViewModel<RepoViewModel>()
    private val adapter = RepoAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.repo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.repoLiveData
            .observe(viewLifecycleOwner, Observer { repos ->
                adapter.repos.clear()
                adapter.repos.addAll(repos)
                adapter.notifyDataSetChanged()
            })

        adapter.onItemClicked = { repo ->
            viewModel.selectedRepo.value = repo.repoId
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

}
