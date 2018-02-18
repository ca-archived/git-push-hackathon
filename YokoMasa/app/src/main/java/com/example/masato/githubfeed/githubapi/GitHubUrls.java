package com.example.masato.githubfeed.githubapi;

import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.model.Repository;

/**
 * Created by Masato on 2018/02/16.
 */

public class GitHubUrls {
    public static final String BASE_HTML_URL = "https://github.com";
    public static final String BASE_API_URL = "https://api.github.com";
    public static final String LOGIN_URL = "https://github.com/login/oauth/access_token";
    public static final String OAUTH_URL = "https://github.com/login/oauth/authorize";
    public static final String PROFILE_URL = BASE_API_URL + "/user";

    private static final String BASE_AUTH_CHECK_URL = BASE_API_URL + "/applications";
    private static final String BASE_STAR_URL = BASE_API_URL + "/user/starred";
    private static final String BASE_REPO_URL = BASE_API_URL + "/repos";

    public static String getAuthCheckUrl(String clientId, String token) {
        return BASE_AUTH_CHECK_URL + "/" + clientId + "/tokens/" + token;
    }

    public static String getLoginUrl(String clientId) {
        return OAUTH_URL + "?client_id=" + clientId + "&scope=repo";
    }

    public static String getStarUrl(Repository repository) {
        return BASE_STAR_URL + "/" + repository.owner + "/" + repository.name;
    }

    public static String getRepoUrl(Repository repository) {
        return BASE_REPO_URL + "/" + repository.owner + "/" + repository.name;
    }

    public static String getSubscriptionUrl(Repository repository) {
        return getRepoUrl(repository) + "/subscription";
    }

    public static String getIssueListUrl(Repository repository) {
        return getRepoUrl(repository) + "/issues";
    }

    public static String getPullListUrl(Repository repository) {
        return getRepoUrl(repository) + "/pulls";
    }

    public static String getCommitListUrl(Repository repository) {
        return getRepoUrl(repository) + "/commits";
    }

    public static String getReadMeUrl(Repository repository) {
        return getRepoUrl(repository) + "/contents/README.md";
    }

    public static String getUserUrl(Profile profile) {
        return BASE_API_URL + "/users" + "/" + profile.name;
    }

    public static String getEventUrl(Profile profile) {
        return getUserUrl(profile) + "/events";
    }

    public static String getReceivedEventUrl(Profile profile) {
        return getUserUrl(profile) + "/received_events";
    }
}
