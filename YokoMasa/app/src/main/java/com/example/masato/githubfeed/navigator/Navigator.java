package com.example.masato.githubfeed.navigator;

import android.content.Context;
import android.content.Intent;

import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.model.event.Event;
import com.example.masato.githubfeed.view.activity.CommitActivity;
import com.example.masato.githubfeed.view.activity.HomeActivity;
import com.example.masato.githubfeed.view.activity.GlobalFeedActivity;
import com.example.masato.githubfeed.view.activity.IssueActivity;
import com.example.masato.githubfeed.view.activity.LogInActivity;
import com.example.masato.githubfeed.view.activity.PullRequestActivity;
import com.example.masato.githubfeed.view.activity.RepoActivity;

/**
 * Created by Masato on 2018/02/07.
 */

public class Navigator {
    
    public static void navigateToLogInView(Context context) {
        Intent intent = new Intent(context, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void navigateToHomeView(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void navigateToGlobalFeedView(Context context) {
        Intent intent = new Intent(context, GlobalFeedActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToCommitView(Context context, Commit commit) {
        Intent intent = new Intent(context, CommitActivity.class);
        intent.putExtra("commit", commit);
        context.startActivity(intent);
    }

    public static void navigateToIssueView(Context context, String url) {
        Intent intent = new Intent(context, IssueActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void navigateToRepoView(Context context, String url) {
        Intent intent = new Intent(context, RepoActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void navigateToPullRequestView(Context context, String url) {
        Intent intent = new Intent(context, PullRequestActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void navigateFromEvent(Context context, Event event) {
        Event.Action action = event.action;
        if (action == null) {
            return;
        }
        switch (action) {
            case REPO_VIEW:
                navigateToRepoView(context, event.triggerUrl);
                break;
            case PR_VIEW:
                navigateToPullRequestView(context, event.triggerUrl);
                break;
            case ISSUE_VIEW:
                navigateToIssueView(context, event.triggerUrl);
                break;
        }
    }
    
    private Navigator() {}
}
