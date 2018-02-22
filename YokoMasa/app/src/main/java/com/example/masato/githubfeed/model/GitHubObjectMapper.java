package com.example.masato.githubfeed.model;

import android.util.Log;

import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.model.diff.DiffParser;
import com.example.masato.githubfeed.model.event.Event;
import com.example.masato.githubfeed.model.event.EventMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubObjectMapper {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    public static List<FeedEntry> mapFeedEntries(String feedString) {
        return XmlFeedParser.parse(feedString);
    }

    public static Profile mapProfile(String profileString) {
        Profile profile = new Profile();
        try {
            JSONObject jsonObject = new JSONObject(profileString);
            profile = mapProfile(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

    public static Profile mapProfile(JSONObject jsonObject) {
        Profile profile = new Profile();
        try {
            profile.name = jsonObject.getString("login");
            profile.iconUrl = jsonObject.getString("avatar_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

    public static String mapFeedUrl(String feedUrlJson) {
        try {
            JSONObject jsonObject = new JSONObject(feedUrlJson);
            return jsonObject.getString("current_user_public_url");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Repository mapRepository(String repositoryString) {
        Repository repository = new Repository();
        try {
            JSONObject jsonObject = new JSONObject(repositoryString);
            repository = mapRepository(jsonObject);
        } catch (Exception e) {
            Log.e("gh_feed", repositoryString);
            e.printStackTrace();
        }
        return repository;
    }

    public static Repository mapRepository(JSONObject jsonObject) {
        Repository repository = new Repository();
        try {
            repository.fullName = jsonObject.getString("full_name");
            repository.htmlUrl = jsonObject.getString("html_url");
            repository.baseUrl = jsonObject.getString("url");
            repository.name = jsonObject.getString("name");
            repository.stars = jsonObject.getInt("stargazers_count");
            repository.watches = jsonObject.optInt("subscribers_count");
            repository.forks = jsonObject.getInt("forks_count");
            JSONObject ownerJsonObject = jsonObject.getJSONObject("owner");
            repository.owner = ownerJsonObject.getString("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repository;
    }

    public static Comment mapComment(JSONObject jsonObject) {
        Comment comment = new Comment();
        try {
            comment.author = mapProfile(jsonObject.getJSONObject("user"));
            comment.bodyHtml = wrap(jsonObject.getString("body_html"));
            comment.createdAt = dateFormat.parse(jsonObject.getString("created_at"));
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return comment;
    }

    public static List<Comment> mapCommentList(String jsonString) {
        List<Comment> comments = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                comments.add(mapComment(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static Issue mapIssue(JSONObject jsonObject) {
        Issue issue = new Issue();
        try {
            issue.url = jsonObject.getString("url");
            issue.htmlUrl = jsonObject.getString("html_url");
            issue.repoUrl = jsonObject.getString("repository_url");
            issue.name = jsonObject.getString("title");
            issue.number = jsonObject.getInt("number");
            issue.bodyHtml = wrap(jsonObject.optString("body_html"));
            issue.createdAt = dateFormat.parse(jsonObject.getString("created_at"));
            issue.commentsUrl = jsonObject.getString("comments_url");
            issue.author = mapProfile(jsonObject.getJSONObject("user"));
            issue.comments = jsonObject.getInt("comments");
            issue.state = jsonObject.getString("state");
            //issue.repository = mapRepository(jsonObject.optJSONObject("repository"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issue;
    }

    public static Issue mapIssue(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return mapIssue(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Issue> mapIssueList(String jsonString) {
        List<Issue> issues = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (!jsonObject.has("pull_request")) {
                    issues.add(mapIssue(jsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issues;
    }
    
    public static PullRequest mapPullRequest(JSONObject jsonObject) {
        PullRequest pr = new PullRequest();
        try {
            pr.url = jsonObject.getString("url");
            pr.htmlUrl = jsonObject.getString("html_url");
            pr.name = jsonObject.getString("title");
            pr.number = jsonObject.getInt("number");
            pr.bodyHtml = wrap(jsonObject.optString("body_html"));
            pr.createdAt = dateFormat.parse(jsonObject.getString("created_at"));
            pr.commentsUrl = jsonObject.getString("comments_url");
            pr.author = mapProfile(jsonObject.getJSONObject("user"));
            pr.state = jsonObject.getString("state");
            pr.diffUrl = jsonObject.getString("diff_url");
            pr.commitsUrl = jsonObject.getString("commits_url");
            pr.repository = mapRepository(jsonObject.getJSONObject("base").getJSONObject("repo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pr;
    }

    public static PullRequest mapPullRequest(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return mapPullRequest(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PullRequest> mapPullRequestList(String jsonString) {
        List<PullRequest> prs = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                prs.add(mapPullRequest(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prs;
    }

    public static Commit mapCommit(JSONObject jsonObject) {
        Commit commit = new Commit();
        try {
            commit.sha = jsonObject.getString("sha");
            commit.url = jsonObject.getString("url");
            commit.htmlUrl = jsonObject.getString("html_url");
            commit.committer = mapProfile(jsonObject.optJSONObject("committer"));
            commit.author = mapProfile(jsonObject.optJSONObject("author"));
            JSONObject commitObject = jsonObject.getJSONObject("commit");
            commit.comment = commitObject.getString("message");
            JSONObject authorObject = commitObject.optJSONObject("author");
            if (authorObject != null) {
                commit.authorDate = dateFormat.parse(authorObject.getString("date"));
            }
            JSONObject committerObject = commitObject.optJSONObject("committer");
            if (committerObject != null) {
                commit.committerDate = dateFormat.parse(committerObject.getString("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commit;
    }

    public static List<Commit> mapCommitList(String jsonString) {
        List<Commit> commits = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                commits.add(mapCommit(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commits;
    }

    public static List<DiffFile> mapCommitDiffFileList(String jsonString) {
        List<DiffFile> diffFiles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray filesArray = jsonObject.getJSONArray("files");
            for (int i = 0;i<filesArray.length();i++) {
                JSONObject fileObject = filesArray.getJSONObject(i);
                DiffFile diffFile = DiffParser.parseDiffFile(fileObject.optString("patch"), fileObject.optString("filename"));
                diffFiles.add(diffFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffFiles;
    }

    public static List<DiffFile> mapDiffFileList(String diffString) {
        return DiffParser.parseDiffFiles(diffString);
    }

    public static List<Event> mapEventList(String jsonString) {
        return EventMapper.getMapper().mapEventList(jsonString);
    }

    private static String wrap(String htmlString) {
        return "<html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"></head><body>" +
                "<div style=\"word-wrap: break-word;\">" +
                htmlString +
                "</div></body></html>";
    }

}
