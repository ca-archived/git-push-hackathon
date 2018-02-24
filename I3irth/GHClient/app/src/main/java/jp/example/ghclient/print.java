package jp.example.ghclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class print extends AppCompatActivity {

    private String access_token;
    private Context context = this;
    private Handler handler;
    private JSONArray eventData;
    private int addLength;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        System.out.println("Start print");

        handler = new Handler();

        getAccessToken();                                           // AccessToken on
        getEventThread getEvent = new getEventThread();             // Instance
        getEvent.start();                                           // Event out

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEventThread getEvent = new getEventThread();
                getEvent.start();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
    public void getAccessToken() {
        Intent intent = getIntent();
        access_token = intent.getStringExtra("access_token");
    }

    class getEventThread extends Thread {

        String[] eventType = null;
        String[] userName = null;
        String[] targetName = null;
        String[] actionType = null;
        public void run() {                 // Thread run
            try {
                String userName = getUserName(access_token);
                eventData = new JSONArray();
                addLength = 0;
                for(page=1; page<11;page++) getReceivedEvents(userName);
                setData(eventData);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            handler.post(new Runnable() {
                public void run() {                 // Handler run
                    ArrayList<HashMap<String, String>> list = setList();
                    SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.list_layout,
                            new String[]{"right", "main", "sub"}, new int[]{R.id.item_right, R.id.item_main, R.id.item_sub});
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(simpleAdapter);
                }
            });
        }
        protected String getUserName(String access_token) throws IOException, JSONException {

            StringBuilder sb = new StringBuilder();
            sb.append("https://api.github.com/user");
            sb.append("?access_token=").append(access_token);

            HttpURLConnection connection = (HttpURLConnection) new URL(sb.toString()).openConnection();
            connection.setRequestMethod("GET");

            sb = new StringBuilder();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            JSONObject userData = null;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            try { userData = new JSONObject(String.valueOf(sb)); }
            catch(JSONException e) { e.printStackTrace(); }
            finally { connection.disconnect(); }


            return userData.getString("login");
        }
        protected void getReceivedEvents(String userName) throws IOException {

            StringBuilder sb = new StringBuilder();
            sb.append("https://api.github.com/users/");
            sb.append(userName);
            sb.append("/received_events");
            sb.append("?page=").append(page);
            sb.append("&access_token=").append(access_token);

            System.out.println(sb.toString());

            HttpURLConnection connection = (HttpURLConnection) new URL(sb.toString()).openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            sb = new StringBuilder();
            String line = null;


            while ((line = br.readLine()) != null) { sb.append(line).append("\n"); }
            try {
                JSONArray jsonArray = new JSONArray(String.valueOf(sb));
                if(jsonArray.length() > 1) {
                    addLength += jsonArray.length();
                    eventData.put(jsonArray);
                }else {
                    page = 11;
                }
            }
            catch (JSONException e) { e.printStackTrace(); }
        }
        protected void setData(JSONArray jsonArray) {
            eventType = new String[addLength];
            userName = new String[addLength];
            targetName = new String[addLength];
            actionType = new String[addLength];
            for (int j = 0; j < jsonArray.length(); j++) {              // page
                try {
                    JSONArray json = jsonArray.getJSONArray(j);         // object in page
                    for (int i = 0; i < json.length(); i++) {
                        eventType[j*30+i] = json.getJSONObject(i).getString("type");
                        if (eventType[j*30+i].equals("MemberEvent"))        actionType[j*30+i] = json.getJSONObject(i).getJSONObject("payload").getString("action");
                        else if (eventType[j*30+i].equals("CreateEvent"))   actionType[j*30+i] = json.getJSONObject(i).getJSONObject("payload").getString("ref_type");
//                        else if ()



                        userName[j*30+i] = json.getJSONObject(i).getJSONObject("actor").getString("login");
                        targetName[j*30+i] = json.getJSONObject(i).getJSONObject("repo").getString("name");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        protected ArrayList<HashMap<String,String>> setList() {
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map = new HashMap<String, String>();
            for(int i=0; i<addLength; i++) {
                if (eventType[i].equals("WatchEvent"))          map.put("main", userName[i]+" starred "+targetName[i]);
                else if (eventType[i].equals("ForkEvent"))      map.put("main", userName[i]+" forked "+targetName[i]);
                else if (eventType[i].equals("MemberEvent"))    map.put("main", userName[i]+" "+actionType[i]+" "+userName[i]+" as a collaborator to "+targetName[i]);
                else if (eventType[i].equals("CreateEvent"))    map.put("main", userName[i]+" created "+actionType[i]+" "+targetName[i]);
                else                                            map.put("main", "null");
                list.add(new HashMap<String, String>(map));
                map.clear();
            }

            return list;
        }
    }
}
