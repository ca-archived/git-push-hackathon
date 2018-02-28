package com.dev.touyou.gitme.Domain.Main

import com.dev.touyou.gitme.Data.Event
import com.dev.touyou.gitme.Data.EventType
import com.dev.touyou.gitme.Data.GitHubAPI
import com.dev.touyou.gitme.Data.Repository
import com.dev.touyou.gitme.Domain.UserInfoViewModel
import io.reactivex.Observable

/**
 * Created by touyou on 2018/02/26.
 */

interface MainConverterInterface {

    fun fetchEvent(page: Int, perPage: Int): Observable<Array<MainViewModel>>
    fun fetchLoginUserInfo(): Observable<UserInfoViewModel>
}

class MainConverter: MainConverterInterface {

    private fun convertEventTitle(event: Event): String {

        val eventTitle = event.actor.displayLogin + when(event.type) {
            EventType.Create -> " created ${event.repo!!.name}"
            EventType.IssueComment -> " ${event.payload.action!!} comment on issue #${event.payload.issue!!.number} in ${event.repo!!.name}"
            EventType.Issues -> " ${event.payload.action!!} issue #${event.payload.issue!!.number} in ${event.repo!!.name}"
            EventType.Push -> " pushed to ${event.payload.ref!!.split("/").last()} at ${event.repo!!.name}"
            EventType.Watch -> " starred ${event.repo!!.name}"
            else -> "${event.type.rawValue}: Unknown"
        }

        return eventTitle
    }

    private fun convertRepositoryInfo(repo: Repository): String {

        val starCount = repo.stargazersCount ?: 0
        val starString = if (starCount >= 1000) "${starCount / 1000}K" else starCount.toString()

        repo.updatedAt?.let {

            // TODO: 日付の変換形式を整える
            return "${repo.language ?: "None"}  ★$starString Updated $it"
        }

        return "${repo.language ?: "None"}  ★$starString"
    }

    /// MainConverterInterface

    override fun fetchEvent(page: Int, perPage: Int): Observable<Array<MainViewModel>> {

        return GitHubAPI.fetchEvents(page, perPage).map {

            it.map {

                val mainViewModel = MainViewModel()
                mainViewModel.iconUrl = it.actor.avatarUrl
                mainViewModel.eventTitle = convertEventTitle(it)
                mainViewModel.createAt = it.createdAt
                mainViewModel.repositoryName = it.repo?.name
                val (repo, readme) = GitHubAPI.fetchRepositoryInfo(it.repo!!.url)
                mainViewModel.repoObservable = repo.map {

                    val repoViewModel = RepositoryViewModel()
                    repoViewModel.repositoryDescription = it.description
                    repoViewModel.repoInfo = convertRepositoryInfo(it)
                    repoViewModel
                }
                mainViewModel.readmeObservable = readme.map {

                    it.downloadUrl
                }
                mainViewModel
            }.toTypedArray()
        }
    }

    override fun fetchLoginUserInfo(): Observable<UserInfoViewModel> {

        return GitHubAPI.logIn().map {

            val userInfoViewModel = UserInfoViewModel()
            userInfoViewModel.userName = it.login
            userInfoViewModel.iconUrl = it.avatarUrl
            userInfoViewModel
        }
    }
}