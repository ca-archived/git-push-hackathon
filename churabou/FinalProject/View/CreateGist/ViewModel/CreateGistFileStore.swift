//
//  CreateGistReactor.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation

// fileName, fileContent

struct File: DiffModel {
    
    var id: String = UUID().uuidString
    
    func same(with: File) -> Bool {
        return id == with.id
    }
    
    var name = ""
    var content = ""
    
    func isEmpty() -> Bool {
        return content.isEmpty
    }
}

protocol CreateGistFileStoreDelegate: class {
    func update(oldSValue: CreateGistFileStore.State, newValue: CreateGistFileStore.State)
}

class CreateGistFileStore {
    
    weak var delegate: CreateGistFileStoreDelegate?
    
    struct State {
        var files: [File] = [.init()]
    }
    
    var state = State() {
        didSet {
            delegate?.update(oldSValue: oldValue, newValue: state)
        }
    }
    
    enum Action {
        case addFile
        case deleteFile(row: Int)
        case updateFileName(row: Int, String)
        case updateFileContent(row: Int, String)
    }
    
    func dispatch(action: Action) {
        state = reduce(action: action, state: state)
    }
    
    func reduce(action: Action, state: State) -> State {
        var newState = state
        switch action {
        case .addFile:
            newState.files.append(File())
        case .deleteFile(let row):
            newState.files.remove(at: row)
        case .updateFileContent(let row, let content):
            newState.files[row].content = content
        case .updateFileName(let row, let fileName):
            newState.files[row].name = fileName
        }
        return newState
    }
}
