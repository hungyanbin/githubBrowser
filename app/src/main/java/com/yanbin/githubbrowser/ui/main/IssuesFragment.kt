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
import kotlinx.android.synthetic.main.issues_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IssuesFragment: Fragment() {

    companion object {
        fun newInstance(repoId: String): IssuesFragment {
            return IssuesFragment().apply {
                val bundle = Bundle()
                bundle.putString("repoId", repoId)
                arguments = bundle
            }
        }
    }

    private val viewModel by viewModel<IssuesViewModel> {
        parametersOf(arguments!!.getString("repoId")!!)
    }
    private val adapter = IssueAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.issues_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.issuesLiveData
            .observe(viewLifecycleOwner, Observer { issues ->
                adapter.issues.clear()
                adapter.issues.addAll(issues)
                adapter.notifyDataSetChanged()
            })

        btnAdd.setOnClickListener {
            viewModel.addNewIssue()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}