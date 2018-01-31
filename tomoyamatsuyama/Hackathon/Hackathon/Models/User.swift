//
//  User.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/31.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation


class User {
    let public_repos: Int
    let repos_url: String
    let starred_url: String
    let bio: String
    let following_url: String
    let followers_url: String
    let id: Int
    let followers: Int
    let following: Int
    let name: String
    let avatar_url: String
    let login: String
    
    init() {
        self.public_repos = 0
        self.repos_url = ""
        self.starred_url = ""
        self.bio = ""
        self.following_url = ""
        self.followers_url = ""
        self.id = 0
        self.followers = 0
        self.following = 0
        self.name = ""
        self.avatar_url = ""
        self.login = ""
    }
    
    
    init(public_repos: Int, repos_url: String, starred_url: String, bio: String, following_url: String, followers_url: String, id: Int, followers: Int, following: Int, name: String, avatar_url: String, login: String) {
        self.public_repos = public_repos
        self.repos_url = repos_url
        self.starred_url = starred_url
        self.bio = bio
        self.following_url = following_url
        self.followers_url = followers_url
        self.id = id
        self.followers = followers
        self.following = following
        self.name = name
        self.avatar_url = avatar_url
        self.login = login
    }
}
