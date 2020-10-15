package com.yanbin.githubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yanbin.githubbrowser.ui.main.GithubViewModelFactory
import com.yanbin.githubbrowser.ui.main.IssuesFragment
import com.yanbin.githubbrowser.ui.main.MainFragment
import com.yanbin.githubbrowser.ui.main.RepoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var repoViewModel: RepoViewModel
    val viewModelFactory by lazy { GithubViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack("Repo")
                .commit()
        }


        repoViewModel = ViewModelProvider(this, viewModelFactory).get(RepoViewModel::class.java)

        repoViewModel.selectedRepo.observe(this, Observer { repoId ->
            val issueFragment = IssuesFragment.newInstance(repoId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, issueFragment)
                .addToBackStack("Issue")
                .commit()
        })
    }
}