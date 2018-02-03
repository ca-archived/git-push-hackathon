package com.example.masato.githubfeed.model;

import android.content.res.Resources;
import android.os.ParcelFormatException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return profile;
    }

    public static Profile mapProfile(JSONObject jsonObject) {
        Profile profile = new Profile();
        try {
            profile.name = jsonObject.getString("login");
            profile.iconUrl = jsonObject.getString("avatar_url");
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return profile;
    }

    public static String mapFeedUrl(String feedUrlJson) {
        try {
            JSONObject jsonObject = new JSONObject(feedUrlJson);
            return jsonObject.getString("current_user_public_url");
        } catch(JSONException je) {
            je.printStackTrace();
        }
        return null;
    }

    public static Repository mapRepository(String repositoryString) {
        Repository repository = new Repository();
        try {
            JSONObject jsonObject = new JSONObject(repositoryString);
            repository = mapRepository(jsonObject);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return repository;
    }

    public static Repository mapRepository(JSONObject jsonObject) {
        Repository repository = new Repository();
        try {
            repository.fullName = jsonObject.getString("full_name");
            repository.name = jsonObject.getString("name");
            repository.stars = jsonObject.getInt("stargazers_count");
            repository.watches = jsonObject.getInt("subscribers_count");
            repository.forks = jsonObject.getInt("forks_count");
            repository.baseUrl = jsonObject.getString("url");
            JSONObject ownerJsonObject = jsonObject.getJSONObject("owner");
            repository.owner = ownerJsonObject.getString("login");
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return repository;
    }

    public static Comment mapComment(JSONObject jsonObject) {
        Comment comment = new Comment();
        try {
            comment.author = mapProfile(jsonObject.getJSONObject("user"));
            comment.bodyHtml = jsonObject.getString("body_html");
            comment.createdAt = dateFormat.parse(jsonObject.getString("created_at"));
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return comment;
    }

    public static Issue mapIssue(JSONObject jsonObject) {
        Issue issue = new Issue();
        try {
            issue.name = jsonObject.getString("title");
            issue.bodyHtml = jsonObject.getString("body_html");
            issue.createdAt = dateFormat.parse(jsonObject.getString("created_at"));
            issue.commentsUrl = jsonObject.getString("comments_url");
            issue.repository = mapRepository(jsonObject.getJSONObject("repository"));
            issue.author = mapProfile(jsonObject.getJSONObject("user"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issue;
    }

    public static List<Issue> mapIssueList(String jsonString) {
        List<Issue> issues = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                issues.add(mapIssue(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issues;
    }

}
