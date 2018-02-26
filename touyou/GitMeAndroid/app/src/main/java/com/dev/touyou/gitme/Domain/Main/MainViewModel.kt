package com.dev.touyou.gitme.Domain.Main

import io.reactivex.Observable
import java.net.URL
import java.util.*

/**
 * Created by touyou on 2018/02/26.
 */

class MainViewModel {

    var repositoryName: String? = null
    var iconUrl: URL? = null
    var eventTitle: String? = null
    var createAt: Date? = null
    var repoObservable: Observable<RepositoryViewModel>? = null
    var readmeObservable: Observable<URL?>? = null
    var isShowReadme: Boolean = false
    var repositoryDescription: String? = null
    var repositoryInfo: String? = null
    var readmeUrl: URL? = null
}

class RepositoryViewModel {

    var repositoryDescription: String? = null
    var repoInfo: String? = null
}