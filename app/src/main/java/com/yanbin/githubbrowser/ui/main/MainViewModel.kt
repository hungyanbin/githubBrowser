package com.yanbin.githubbrowser.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val repos = listOf(
        Repo("Project1", "Kotlin"),
        Repo("Project2", "Java"),
        Repo("Project3", "C#"),
        Repo("Project4", "Kotlin")
    )

    val repoLiveData = MutableLiveData(repos)
}