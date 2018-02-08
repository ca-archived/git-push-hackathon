package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;

import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.model.diff.DiffFile;

import java.util.ArrayList;

/**
 * Created by Masato on 2018/02/07.
 */

public class FragmentFactory {

    public static CommentListFragment createCommentListFragment(String url, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("name", name);
        CommentListFragment commentListFragment = new CommentListFragment();
        commentListFragment.setArguments(bundle);
        return commentListFragment;
    }

    public static CommitListFragment createCommitListFragment(String url, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("name", name);
        CommitListFragment commitListFragment = new CommitListFragment();
        commitListFragment.setArguments(bundle);
        return commitListFragment;
    }

    public static CommitListFragment createCommitListFragment(Repository repository, String name) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("repository", repository);
        bundle.putString("name", name);
        CommitListFragment commitListFragment = new CommitListFragment();
        commitListFragment.setArguments(bundle);
        return commitListFragment;
    }

    public static DiffFileListFragment createDiffFileListFragment(ArrayList<DiffFile> diffFiles, String name) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("diff_files", diffFiles);
        bundle.putString("name", name);
        DiffFileListFragment diffFileListFragment = new DiffFileListFragment();
        diffFileListFragment.setArguments(bundle);
        return diffFileListFragment;
    }

    public static FeedListFragment createFeedListFragment(String url, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("name", name);
        FeedListFragment feedListFragment = new FeedListFragment();
        feedListFragment.setArguments(bundle);
        return feedListFragment;
    }

    public static IssueListFragment createIssueListFragment(Repository repository, String name) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("repository", repository);
        bundle.putString("name", name);
        IssueListFragment issueListFragment = new IssueListFragment();
        issueListFragment.setArguments(bundle);
        return issueListFragment;
    }

    public static IssueListFragment createIssueListFragment(String url, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("name", name);
        IssueListFragment issueListFragment = new IssueListFragment();
        issueListFragment.setArguments(bundle);
        return issueListFragment;
    }

    public static IssueOverviewFragment createIssueOverviewFragment(Issue issue, String name) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("issue", issue);
        bundle.putString("name", name);
        IssueOverviewFragment issueOverviewFragment = new IssueOverviewFragment();
        issueOverviewFragment.setArguments(bundle);
        return issueOverviewFragment;
    }

    public static RepoOverviewFragment createRepoOverviewFragment(Repository repository, String name) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("repository", repository);
        bundle.putString("name", name);
        RepoOverviewFragment repoOverviewFragment = new RepoOverviewFragment();
        repoOverviewFragment.setArguments(bundle);
        return repoOverviewFragment;
    }

    private FragmentFactory(){}
}
