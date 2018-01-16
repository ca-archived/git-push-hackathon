//
//  GitMeConstantTests.swift
//  GitMeTests
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import XCTest
@testable import GitMe

class GitMeEntityTests: XCTestCase {
    
    override func setUp() {
        super.setUp()
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testGitHubConstant() {

        XCTAssertNotNil(GitHubConstant.clientPlist)
        // 存在確認
        XCTAssertNotEqual(GitHubConstant.clientId, "")
        XCTAssertNotEqual(GitHubConstant.clientSecret, "")
    }
}
