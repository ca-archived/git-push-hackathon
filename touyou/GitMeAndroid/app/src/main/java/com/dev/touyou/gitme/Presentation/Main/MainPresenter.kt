package com.dev.touyou.gitme.Presentation.Main

import com.dev.touyou.gitme.Domain.Main.MainConverterInterface
import com.dev.touyou.gitme.Domain.Main.MainViewModel
import com.dev.touyou.gitme.Domain.UserInfoViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by touyou on 2018/02/26.
 */

interface MainPresenterInterface {
    fun fetchUser()
    fun reload()
    fun loadMore()
}

// TODO: 購読するスケジューラを決める

class MainPresenter constructor(val converterInterface: MainConverterInterface): MainPresenterInterface {

    private val perPage = 30

    private var page = 1
    private var cellData: Array<MainViewModel> = Array<MainViewModel>(0, { MainViewModel() })
    var logInData: Observable<UserInfoViewModel> = Observable.empty()

    private fun subscribeMainViewModel() {

        for (i in 0 until cellData.size) {

            if (cellData[i].repositoryDescription == null) {

                cellData[i].repoObservable?.let {
                    it.subscribe(
                            {
                                cellData[i].repositoryDescription = it.repositoryDescription
                                cellData[i].repositoryInfo = it.repoInfo
                            },
                            {
                                println(it)
                            }
                    ).dispose()
                }
            }
            if (cellData[i].readmeObservable == null) {

                cellData[i].readmeObservable?.let {
                    it.subscribe(
                        {
                            cellData[i].readmeUrl = it
                        },
                        {
                            println(it)
                        }
                    ).dispose()
                }
            }
        }
    }

    override fun fetchUser() {

        converterInterface.fetchLoginUserInfo()
                .subscribe(
                        {
                            logInData = Observable.just(it)
                        },
                        {
                            println(it)
                        }
                )
                .dispose()
    }

    override fun reload() {

        page = 1;
        converterInterface.fetchEvent(page, perPage)
                .subscribe(
                        {
                            cellData = it
                            subscribeMainViewModel()
                        },
                        {
                            println(it)
                        }
                )
                .dispose()
    }

    override fun loadMore() {

        page++;
        converterInterface.fetchEvent(page, perPage)
                .subscribe(
                        {
                            cellData += it
                            subscribeMainViewModel()
                        },
                        {
                            println(it)
                        }
                )
                .dispose()
    }
}