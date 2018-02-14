package com.example.masato.githubfeed.model.event;

import android.content.Context;
import android.util.Log;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.GitHubObjectMapper;
import com.example.masato.githubfeed.model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Masato on 2018/02/09.
 */

public class EventMapper {

    private static EventMapper mapper;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    private JSONObject blueprints;

    public static void init(Context context) {
        mapper = new EventMapper(context);
    }

    public static EventMapper getMapper() {
        return mapper;
    }

    public List<Event> mapEventList(String jsonString) {
        List<Event> events = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Event event = mapEvent(jsonObject);
                events.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event mapEvent(JSONObject jsonObject) {
        Event event = new Event();
        try {
            event.type = jsonObject.getString("type");
            event.createdAt = dateFormat.parse(jsonObject.getString("created_at"));
            JSONObject actorObject = jsonObject.getJSONObject("actor");
            event.actorName = actorObject.getString("login");
            event.actorIconUrl = actorObject.getString("avatar_url");
            JSONObject repoObject = jsonObject.getJSONObject("repo");
            event.repoUrl = repoObject.getString("url");
            event.repoName = repoObject.getString("name");
            buildEvent(event, jsonObject.getJSONObject("payload"));
            Log.i("gh_feed", event.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    private void buildEvent(Event event, JSONObject payload) throws JSONException {
        JSONObject blueprint = blueprints.optJSONObject(event.type);
        if (blueprint == null) {
            event.content = event.type;
            return;
        }

        JSONObject commentBlueprint = blueprint.getJSONObject("comment");
        buildComment(commentBlueprint, payload, event);
        buildAction(blueprint, payload, event);
    }

    private void buildComment(JSONObject commentBlueprint, JSONObject payload, Event event) {
        String comment = getCommentTemplate(commentBlueprint, payload);
        comment = comment.replace("{REPO}", event.repoName);
        comment = comment.replace("{ACTOR}", event.actorName);

        int varStartIndex = comment.indexOf("{");
        int varEndIndex = comment.indexOf("}");
        while (varStartIndex != -1) {
            String wholeCommand = comment.substring(varStartIndex, varEndIndex+1);
            String command = comment.substring(varStartIndex+1, varEndIndex);
            String commandResult = getStringFromCommand(command, payload);
            comment = comment.replace(wholeCommand, commandResult);

            varStartIndex = comment.indexOf("{");
            varEndIndex = comment.indexOf("}");
        }
        event.content = comment;
    }

    private String getCommentTemplate(JSONObject commentBlueprint, JSONObject payload) {
        if (commentBlueprint.has("switch")) {
            String switchKey = commentBlueprint.optString("switch");
            if (switchKey == null) {
                return "";
            }
            String switchWord = payload.optString(switchKey);
            String template = commentBlueprint.optString(switchWord);
            if (template == null) {
                return commentBlueprint.optString("default");
            } else {
                return template;
            }
        } else {
            return commentBlueprint.optString("default");
        }
    }

    private String getStringFromCommand(String command, JSONObject payload) {
        String[] dir = command.split("\\.");
        for (int i = 0;i<dir.length;i++) {
            if (i == dir.length -1) {
                return processLastCommand(dir[i], payload);
            } else {
                payload = payload.optJSONObject(dir[i]);
            }
        }
        return "";
    }

    private String processLastCommand(String command, JSONObject payload) {
        if (payload == null) {
            return "";
        }
        int braceStartIndex = command.indexOf("(");
        if (braceStartIndex == -1) {
            Object o = payload.opt(command);
            return o == null ? "" : o.toString();
        } else {
            int subStringFrom = Integer.parseInt(command.substring(braceStartIndex+1, braceStartIndex+2));
            int subStringTo = Integer.parseInt(command.substring(braceStartIndex+3, braceStartIndex+4));
            String key = command.substring(0, braceStartIndex);
            Object o = payload.opt(key);
            String raw = o == null ? null : o.toString();
            if (raw != null) {
                if (raw.length() < subStringFrom) {
                    return "";
                }
                if (raw.length() < subStringTo) {
                    subStringTo = raw.length();
                }
                return raw.substring(subStringFrom, subStringTo);
            } else {
                return "";
            }
        }
    }

    private void buildAction(JSONObject blueprint, JSONObject payload, Event event) {
        String action = blueprint.optString("action");
        if (action == null) {
            return;
        }
        switch (action) {
            case "REPO":
                event.action = Event.Action.REPO_VIEW;
                event.triggerUrl = event.repoUrl;
                JSONObject jsonObject = payload.optJSONObject("repository");
                if (jsonObject != null) {
                    event.triggerModel = GitHubObjectMapper.mapRepository(jsonObject);
                }
                break;
            case "ISSUE":
                event.action = Event.Action.ISSUE_VIEW;
                event.triggerUrl = payload.optString("issue_url");
                JSONObject issueObject = payload.optJSONObject("issue");
                if (issueObject != null) {
                    String a = issueObject.optString("state");
                    event.triggerModel = GitHubObjectMapper.mapIssue(issueObject);
                }
                break;
            case "PR":
                event.action = Event.Action.PR_VIEW;
                event.triggerUrl = payload.optString("pull_request_url");
                JSONObject prObject = payload.optJSONObject("pull_request");
                if (prObject != null) {
                    event.triggerModel = GitHubObjectMapper.mapPullRequest(prObject);
                }
                break;
        }
    }

    private EventMapper(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.event_blueprint);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            StringBuilder builder = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                builder.append(line);
                line = br.readLine();
            }
            this.blueprints = new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
