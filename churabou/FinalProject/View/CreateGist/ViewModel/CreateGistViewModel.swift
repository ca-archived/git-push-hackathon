//
//  CreateGistViewModel.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/16.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import RxSwift
import RxCocoa

enum TableViewRefreshType {
    case insert(rows: [Int])
    case delete(rows: [Int])
}

protocol CreateGistViewModelInput {
    func tapAdd()
    func tapDelete(row: Int)
    func tapPost()
}

protocol CreateGistViewModelOutput {
    var files: [File] { get }
    var tableViewRefresh: Observable<TableViewRefreshType> { get }
    var uploadState: Observable<APIResponse<Void>> { get }
}


protocol CreateGistViewModelType: CreateGistViewModelInput, CreateGistViewModelOutput {}

final class CreateGistViewModel: CreateGistViewModelType {
    
    var input: CreateGistViewModelInput { return self }
    var output: CreateGistViewModelOutput { return self }
    var store = CreateGistFileStore()
    var description = BehaviorRelay(value: "")
    
    private var uploadStateSubject = PublishSubject<APIResponse<Void>>()
    private var tableViewRefreshTrigger = PublishSubject<TableViewRefreshType>()
    
    private let bag = DisposeBag()
    private let api = APIClient()
    
    private var isUpdating = false
    
    enum UploadState {
        case isUpdating
        case failed(String)
        case success
    }
    
    init() {
        store.delegate = self
    }
}

extension CreateGistViewModel: CreateGistViewModelInput {

    func tapAdd() {
        store.dispatch(action: .addFile)
    }
    
    func tapDelete(row: Int) {
        store.dispatch(action: .deleteFile(row: row))
    }
    
    func tapPost() {
        uploadGistFiles()
    }
}

extension CreateGistViewModel: CreateGistViewModelOutput {
    
    var files: [File] {
        return store.state.files
    }
    
    var tableViewRefresh: Observable<TableViewRefreshType> {
        return tableViewRefreshTrigger.asObservable()
    }
    
    var uploadState: Observable<APIResponse<Void>> {
        return uploadStateSubject.asObservable()
    }
}


extension CreateGistViewModel {
    
    private func makeRequest(files: [File]) ->  GithubGistRequest.Create {
        // todo
        let params = files
            .reduce(Dictionary<String, Dictionary<String, String>>()) { (dic, file) in
                var newDic = dic
                newDic[file.name] = ["content": file.content]
                return newDic
        }
        
        return GithubGistRequest.Create(
            description: description.value,
            files: params
        )
    }

    func uploadGistFiles() {
        
        if isUpdating { return }
        
        if let _  = files.first(where: { $0.isEmpty() }) {
            uploadStateSubject.onNext(.failure(message: "空のファイルがあります"))
            return
        }
        
        isUpdating = true
        let request = makeRequest(files: files)
        api.send(request) { res in
            self.isUpdating = false
            switch res {
            case .success:
                self.uploadStateSubject.onNext(.success(response: ()))
            case .failure:
                self.uploadStateSubject.onNext(.failure(message: "投稿に失敗しました"))
            }
        }
    }
}

extension CreateGistViewModel: CreateGistFileStoreDelegate {
    
    func update(oldSValue: CreateGistFileStore.State, newValue: CreateGistFileStore.State) {
        
        let detector = Detector()
        if let status = detector.detectSingle(newValue: newValue.files, oldValue: oldSValue.files) {
            switch status {
            case .delete(let row):
                tableViewRefreshTrigger.onNext(.delete(rows: [row]))
            case .insert(let row):
                tableViewRefreshTrigger.onNext(.insert(rows: [row]))
            }
        }
    }
}
