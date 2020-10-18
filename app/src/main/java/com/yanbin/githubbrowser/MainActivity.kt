package com.yanbin.githubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.yanbin.githubbrowser.ui.main.IssuesFragment
import com.yanbin.githubbrowser.ui.main.MainFragment
import com.yanbin.githubbrowser.ui.main.RepoViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val repoViewModel by viewModel<RepoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack("Repo")
                .commit()
        }

        repoViewModel.selectedRepo.observe(this, Observer { repoId ->
            val issueFragment = IssuesFragment.newInstance(repoId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, issueFragment)
                .addToBackStack("Issue")
                .commit()
        })
    }
}