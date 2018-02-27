package com.dev.touyou.gitme.Domain.Login

import com.dev.touyou.gitme.Data.GitHubAPI
import com.dev.touyou.gitme.Domain.UserInfoViewModel
import io.reactivex.Observable

/**
 * Created by touyou on 2018/02/26.
 */

interface LoginConverterInterface {

    fun logIn(): Observable<UserInfoViewModel>
}

class LoginConverter: LoginConverterInterface {

    override fun logIn(): Observable<UserInfoViewModel> {

        return GitHubAPI.logIn().map {

            val userInfoViewModel = UserInfoViewModel()
            userInfoViewModel.userName = it.login
            userInfoViewModel.iconUrl = it.avatarUrl
            userInfoViewModel
        }
    }
}