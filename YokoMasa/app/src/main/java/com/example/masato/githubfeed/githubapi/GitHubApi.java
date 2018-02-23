package com.example.masato.githubfeed.githubapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.Repository;
import com.google.android.gms.security.ProviderInstaller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubApi {

    public static final String GLOBAL_FEED_URL = "https://github.com/timeline";
    private GitHubTokenManager tokenManager;
    private GitHubResourceManager resourceManager;
    private ExecutorService executorService;

    private static GitHubApi api;

    public static void init(Context appContext) {
        api = new GitHubApi(appContext);
    }

    public static GitHubApi getApi() {
        if (api == null) {
            throw new RuntimeException("init() must be called before accessing to the api");
        }
        return api;
    }

    public void fetchProfile(GitHubApiCallback callback) {
        resourceManager.getProfile(callback);
    }

    public void fetchFeedList(String url, int page, GitHubApiCallback callback) {
        resourceManager.getFeedEntries(url, page, callback);
    }

    public void fetchFeedUrl(GitHubApiCallback callback) {
        resourceManager.getFeedUrl(callback);
    }

    public void fetchBitmap(String url, GitHubApiCallback callback) {
        resourceManager.getBitmapFromUrl(url, callback);
    }

    public void fetchRepository(String url, GitHubApiCallback callback) {
        resourceManager.getRepository(url, callback);
    }

    public void fetchReadMe(Repository repository, GitHubApiCallback callback) {
        resourceManager.getReadMeHtml(repository, callback);
    }

    public void isStarredByCurrentUser(Repository repository, GitHubApiCallback callback) {
        resourceManager.isStarredByCurrentUser(repository, callback);
    }

    public void isSubscribedByCurrentUser(Repository repository, GitHubApiCallback callback) {
        resourceManager.isSubscribedByCurrentUser(repository, callback);
    }

    public void starRepository(Repository repository, GitHubApiCallback callback) {
        resourceManager.starRepository(repository, callback);
    }

    public void subscribeRepository(Repository repository, GitHubApiCallback callback) {
        resourceManager.subscribeRepository(repository, callback);
    }

    public void unStarRepository(Repository repository, GitHubApiCallback callback) {
        resourceManager.unStarRepository(repository, callback);
    }

    public void unSubscribeRepository(Repository repository, GitHubApiCallback callback) {
        resourceManager.unSubscribeRepository(repository, callback);
    }

    public void fetchIssueList(String url, int page, GitHubApiCallback callback) {
        resourceManager.getIssueListFromUrl(url, page, callback);
    }

    public void fetchIssueList(Repository repository, int page,  GitHubApiCallback callback) {
        resourceManager.getRepositoryIssueList(repository, page, callback);
    }

    public void fetchIssue(String url, GitHubApiCallback callback) {
        resourceManager.getIssue(url, callback);
    }

    public void fetchPullRequest(String url, GitHubApiCallback callback) {
        resourceManager.getPullRequest(url, callback);
    }

    public void fetchPullRequestList(Repository repository, int page, GitHubApiCallback callback) {
        resourceManager.getRepositoryPullRequestList(repository, page, callback);
    }

    public void fetchCommentList(String url, int page, GitHubApiCallback callback) {
        resourceManager.getCommentListFromUrl(url, page, callback);
    }

    public void fetchCommitList(String url, int page, GitHubApiCallback callback) {
        resourceManager.getCommitListFromUrl(url, page, callback);
    }

    public void fetchCommitList(Repository repository, int page, GitHubApiCallback callback) {
        resourceManager.getRepositoryCommitList(repository, page, callback);
    }

    public void fetchDiffFileList(String url, GitHubApiCallback callback) {
        resourceManager.getDiffFileList(url, callback);
    }

    public void fetchCommitDiffFileList(Commit commit, GitHubApiCallback callback) {
        resourceManager.getCommitDiffFileList(commit, callback);
    }

    public void fetchEventList(String url, int page, GitHubApiCallback callback) {
        resourceManager.getEventList(url, page, callback);
    }

    public void deleteToken() {
        tokenManager.deleteToken();
    }

    public void checkIfTokenValid(final GitHubApiCallback callback) {
        tokenManager.checkIfTokenValid(callback);
    }

    public void requestToken(String code, final GitHubApiCallback callback) {
        tokenManager.fetchToken(code, result -> {
            if (result.isSuccessful) {
                String token = (String) result.resultObject;
                resourceManager.updateToken(token);
            }
            callback.onApiResult(result);
        });
    }

    private GitHubApi(Context appContext) {
        SharedPreferences preferences = appContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        Resources resources = appContext.getResources();
        this.executorService = Executors.newFixedThreadPool(3);
        this.tokenManager = new GitHubTokenManager(resources, preferences, executorService);
        this.resourceManager = new GitHubResourceManager(tokenManager.getToken(), executorService);
    }

}
