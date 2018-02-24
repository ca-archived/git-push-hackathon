package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.RepoOverviewView;

/**
 * Created by Masato on 2018/02/02.
 */

public class RepoOverviewPresenter extends BasePresenter {

    private RepoOverviewView view;
    private Repository repository;
    private String contentHtml;
    private boolean isStarred;
    private boolean isSubscribed;
    private boolean everLoaded;

    public void onViewCreated() {
        if (everLoaded) {
            showData();
        } else {
            loadData();
            everLoaded = true;        }
    }

    @Override
    public void tryAgain() {
        view.hideErrorView();
        loadData();
    }

    public void showData() {
        view.showOverview(repository);
        view.showReadMe(contentHtml);
        view.setStarActivated(isStarred);
        view.setWatchActivated(isSubscribed);
    }

    private void loadData() {
        view.showOverview(repository);
        checkIfRepoStarred(repository);
        checkIfRepoSubscribed(repository);
        fetchReadMe(repository);
    }

    public void onStarPressed() {
        if (isStarred) {
            unStarRepo();
        } else {
            starRepo();
        }
    }

    private void starRepo() {
        view.setStarActivated(true);
        GitHubApi.getApi().starRepository(repository, this::handleStarRepoResult);
    }

    private void handleStarRepoResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            repository.stars++;
            view.showOverview(repository);
            view.showToast(R.string.repo_starred);
            isStarred = true;
        } else {
            view.showToast(R.string.repo_star_failed);
            view.setStarActivated(false);
        }
    }

    private void unStarRepo() {
        view.setStarActivated(false);
        GitHubApi.getApi().unStarRepository(repository, this::handleUnStarRepoResult);
    }

    private void handleUnStarRepoResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            repository.stars--;
            view.showOverview(repository);
            view.showToast(R.string.repo_unstarred);
            isStarred = false;
        } else {
            view.showToast(R.string.repo_unstar_failed);
            view.setStarActivated(true);
        }
    }

    public void onSubscribePressed() {
        if (isSubscribed) {
            unSubscribeRepo();
        } else {
            subscribeRepo();
        }
    }

    private void subscribeRepo() {
        view.setWatchActivated(true);
        GitHubApi.getApi().subscribeRepository(repository, this::handleSubscribeRepoResult);
    }

    private void handleSubscribeRepoResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            repository.watches++;
            view.showOverview(repository);
            view.showToast(R.string.repo_subscribed);
            isSubscribed = true;
        } else {
            view.setWatchActivated(false);
            view.showToast(R.string.repo_subscribe_failed);
        }
    }

    private void unSubscribeRepo() {
        view.setWatchActivated(false);
        GitHubApi.getApi().unSubscribeRepository(repository, this::handleUnSubscribeRepoResult);
    }

    private void handleUnSubscribeRepoResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            repository.watches--;
            view.showOverview(repository);
            view.showToast(R.string.repo_unsubscribed);
            isSubscribed = false;
        } else {
            view.setWatchActivated(true);
            view.showToast(R.string.repo_unsubscribe_failed);
        }
    }

    private void checkIfRepoStarred(Repository repository) {
        GitHubApi.getApi().isStarredByCurrentUser(repository, this::handleCheckStarResult);
    }

    private void handleCheckStarResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            isStarred = true;
            view.setStarActivated(true);
        } else {
            isStarred = false;
        }
    }

    private void checkIfRepoSubscribed(Repository repository) {
        GitHubApi.getApi().isSubscribedByCurrentUser(repository, this::handleCheckSubscribeResult);
    }

    private void handleCheckSubscribeResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            isSubscribed = true;
            view.setWatchActivated(true);
        } else {
            isSubscribed = false;
        }
    }

    private void fetchReadMe(Repository repository) {
        view.showLoadingView();
        GitHubApi.getApi().fetchReadMe(repository, this::handleFetchReadMeResult);
    }

    private void handleFetchReadMeResult(GitHubApiResult result) {
        view.hideLoadingView();
        if (result.isSuccessful) {
            contentHtml = (String) result.resultObject;
            view.showReadMe(contentHtml);
        } else {
            if (result.failure == Failure.NOT_FOUND) {
                view.showNoReadMe();
            } else {
                view.showErrorView(result.failure, result.errorMessage);
            }
        }
    }

    public RepoOverviewPresenter(RepoOverviewView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }
}
