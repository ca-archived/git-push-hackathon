package com.example.masato.githubfeed.githubapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.GitHubObjectMapper;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.http.HandyHttpURLConnection;
import com.example.masato.githubfeed.http.HttpConnectionPool;

import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/19.
 */

class GitHubResourceManager {

    private static final String MIME_TYPE_HTML = "application/vnd.github.v3.html";

    private HttpConnectionPool connectionPool;

    void updateToken(String token) {
        connectionPool.setDefHeader("Authorization", "Token " + token);
    }

    void getProfile(final GitHubApiCallback callback) {
        final HandyHttpURLConnection connection = connectionPool.newConnection(GitHubUrls.PROFILE_URL);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapProfile(successfulResult.getBodyString());
            });
        });
    }

    void getFeedUrl(final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection("https://api.github.com/feeds");
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapFeedUrl(result.getBodyString());
            });
        });
    }

    void getFeedEntries(String url, int page, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", "application/atom+xml");
        connection.addParams("page", Integer.toString(page));
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapFeedEntries(successfulResult.getBodyString());
            });
        });
    }

    void getRepository(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapRepository(successfulResult.getBodyString());
            });
        });
    }

    void getIssue(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", MIME_TYPE_HTML);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapIssue(successfulResult.getBodyString());
            });
        });
    }

    void getPullRequest(String url, GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", MIME_TYPE_HTML);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapPullRequest(successfulResult.getBodyString());
            });
        });
    }

    void getReadMeHtml(Repository repository, final GitHubApiCallback callback) {
        String url = GitHubUrls.getReadMeUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", MIME_TYPE_HTML);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return successfulResult.getBodyString();
            });
        });
    }

    void isStarredByCurrentUser(Repository repository, final GitHubApiCallback callback) {
        String url = GitHubUrls.getStarUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, null);
        });
    }

    void starRepository(Repository repository, final GitHubApiCallback callback) {
        String url = GitHubUrls.getStarUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Content-Length", "0");
        connection.put(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, null);
        });
    }

    void unStarRepository(Repository repository, final GitHubApiCallback callback) {
        String url = GitHubUrls.getStarUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.delete(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, null);
        });
    }

    void isSubscribedByCurrentUser(Repository repository, final GitHubApiCallback callback) {
        String url = GitHubUrls.getSubscriptionUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiCallbackHandler. handleResult(result, callback, null);
        });
    }

    void subscribeRepository(Repository repository, final GitHubApiCallback callback) {
        String url = GitHubUrls.getSubscriptionUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.addParams("subscribed", "true");
        connection.put(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, null);
        });
    }

    void unSubscribeRepository(Repository repository, final GitHubApiCallback callback) {
        String url = GitHubUrls.getSubscriptionUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.delete(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, null);
        });
    }

    void getIssueListFromUrl(String url, int page, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.addParams("page", Integer.toString(page));
        connection.setHeader("Accept", MIME_TYPE_HTML);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapIssueList(successfulResult.getBodyString());
            });
        });
    }

    void getRepositoryIssueList(Repository repository, int page, final GitHubApiCallback callback) {
        String url = GitHubUrls.getIssueListUrl(repository);
        getIssueListFromUrl(url, page, callback);
    }

    void getRepositoryPullRequestList(Repository repository, int page, final GitHubApiCallback callback) {
        String url = GitHubUrls.getPullListUrl(repository);
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.addParams("page", Integer.toString(page));
        connection.setHeader("Accept", MIME_TYPE_HTML);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapPullRequestList(successfulResult.getBodyString());
            });
        });
    }

    void getCommentListFromUrl(String url, int page, GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.addParams("page", Integer.toString(page));
        connection.setHeader("Accept", MIME_TYPE_HTML);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapCommentList(successfulResult.getBodyString());
            });
        });
    }

    void getRepositoryCommitList(Repository repository, int page, final GitHubApiCallback callback) {
        String url = GitHubUrls.getCommitListUrl(repository);
        getCommitListFromUrl(url, page, callback);
    }

    void getCommitListFromUrl(String url, int page, GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.addParams("page", Integer.toString(page));
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapCommitList(successfulResult.getBodyString());
            });
        });
    }

    void getDiffFileList(String url, GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapDiffFileList(successfulResult.getBodyString());
            });
        });
    }

    void getCommitDiffFileList(Commit commit, GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(commit.url);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapCommitDiffFileList(successfulResult.getBodyString());
            });
        });
    }

    void getEventList(String url, int page, GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.addParams("page", Integer.toString(page));
        connection.setHeader("Accept", MIME_TYPE_HTML);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapEventList(successfulResult.getBodyString());
            });
        });
    }

    void getBitmapFromUrl(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiCallbackHandler.handleResult(result, callback, successfulResult -> {
                byte[] bodyBytes = successfulResult.bodyBytes;
                return BitmapFactory.decodeByteArray(bodyBytes, 0, bodyBytes.length);
            });
        });
    }

    GitHubResourceManager(String token, ExecutorService service) {
        this.connectionPool = new HttpConnectionPool(service);
        this.connectionPool.setDefHeader("User-Agent", "YokoMasa");
        this.connectionPool.setDefHeader("Authorization", "Token " + token);
    }

}
