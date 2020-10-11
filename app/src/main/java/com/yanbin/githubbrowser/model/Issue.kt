package com.yanbin.githubbrowser.model

import java.time.LocalDate

data class Issue(val title: String, val openedDate: LocalDate, val status: IssueStatus)

enum class IssueStatus {
    OPEN, CLOSED
}