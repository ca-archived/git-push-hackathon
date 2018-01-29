package com.example.masato.githubfeed.presenter;

import android.util.Log;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.RepoView;

/**
 * Created by Masato on 2018/01/27.
 */

public class RepoPresenter {

    private RepoView view;
    private String repoUrl;
    private Repository repository;
    private boolean isStarred;
    private boolean isSubscribed;

    public void onCreate() {
        GitHubApi.getApi().fetchRepository(repoUrl, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Repository repository = (Repository) object;
                RepoPresenter.this.repository = repository;
                checkIfRepoStarred(repository);
                checkIfRepoSubscribed(repository);
                fetchReadMe(repository);
                view.showRepo(repository);
            }

            @Override
            public void onApiFailure(Failure failure) {

            }
        });
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
        GitHubApi.getApi().starRepository(repository, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                repository.stars++;
                view.showRepo(repository);
                view.showToast(R.string.repo_starred);
                isStarred = true;
            }

            @Override
            public void onApiFailure(Failure failure) {
                view.showToast(R.string.repo_star_failed);
                view.setStarActivated(false);
            }
        });
    }

    private void unStarRepo() {
        view.setStarActivated(false);
        GitHubApi.getApi().unStarRepository(repository, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                repository.stars--;
                view.showRepo(repository);
                view.showToast(R.string.repo_unstarred);
                isStarred = false;
            }

            @Override
            public void onApiFailure(Failure failure) {
                view.showToast(R.string.repo_unstar_failed);
                view.setStarActivated(true);
            }
        });
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
        GitHubApi.getApi().subscribeRepository(repository, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                repository.watches++;
                view.showRepo(repository);
                view.showToast(R.string.repo_subscribed);
                isSubscribed = true;
            }

            @Override
            public void onApiFailure(Failure failure) {
                view.setWatchActivated(false);
                view.showToast(R.string.repo_subscribe_failed);
            }
        });
    }

    private void unSubscribeRepo() {
        view.setWatchActivated(false);
        GitHubApi.getApi().unSubscribeRepository(repository, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                repository.watches--;
                view.showRepo(repository);
                view.showToast(R.string.repo_unsubscribed);
                isSubscribed = false;
            }

            @Override
            public void onApiFailure(Failure failure) {
                view.setWatchActivated(true);
                view.showToast(R.string.repo_unsubscribe_failed);
            }
        });
    }

    private void checkIfRepoStarred(Repository repository) {
        GitHubApi.getApi().isStarredByCurrentUser(repository, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                isStarred = true;
                view.setStarActivated(true);
            }

            @Override
            public void onApiFailure(Failure failure) {
                isStarred = false;
            }
        });
    }

    private void checkIfRepoSubscribed(Repository repository) {
        GitHubApi.getApi().isSubscribedByCurrentUser(repository, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                isSubscribed = true;
                view.setWatchActivated(true);
            }

            @Override
            public void onApiFailure(Failure failure) {
                isSubscribed = false;
            }
        });
    }

    private void fetchReadMe(Repository repository) {
        GitHubApi.getApi().fetchReadMe(repository, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                String contentHtml = (String) object;
                view.showReadMe(contentHtml);
            }

            @Override
            public void onApiFailure(Failure failure) {
                view.showReadMe("No README here.");
            }
        });
    }

    public RepoPresenter(RepoView view, String url) {
        this.view = view;
        this.repoUrl = url;
    }
}
