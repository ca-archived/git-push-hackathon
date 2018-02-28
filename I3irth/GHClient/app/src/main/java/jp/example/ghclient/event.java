package jp.example.ghclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class event extends AppCompatActivity {

    private String access_token;
    private String myname;
    private Context context = this;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Events");
        setSupportActionBar(toolbar);

        try {
            //  cacheがあればアクセストークンを読み取る
            //  cache.txtがなければ作成　アクセストークン保存
            File file = new File(getCacheDir(), "cache.txt");
            if (file.exists())
                access_token = getByCache(file);
            else {
                setAccessToken();
                createTokenFile();
            }
            // スレッド作成 スタート
            mThread getEvent = new mThread();
            getEvent.start();

            final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mThread getEvent = new mThread();
                    getEvent.start();
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }catch(IOException e ){
            e.printStackTrace();
        }
    }
    //  前のアクティビティからアクセストークンを受け取る
    protected void setAccessToken() {
        Intent intent = getIntent();
        access_token = intent.getStringExtra("access_token");
    }
    //  cache.txtを作成してアクセストークを保存
    protected void createTokenFile() throws IOException{
        File file = new File(getCacheDir(), "cache.txt");
        FileOutputStream fos = null;
        file.createNewFile();
        fos = new FileOutputStream(file);
        fos.write(access_token.getBytes());
        fos.close();
    }
    //  cache.txtからアクセストークンを読み取る
    protected String getByCache(File file) throws IOException{
        FileInputStream fis = null;
        byte[] buffer = new byte[40];
        String access_token = null;
        file = new File(getCacheDir(), "cache.txt");
        if (file.exists()) {
            fis = new FileInputStream(file);
            fis.read(buffer);
            access_token = new String(buffer, "UTF-8");
        }
        fis.close();

        return access_token;
    }

    class mThread extends Thread {

        JSONArray eventData = null;
        String[] eventType = null;
        String[] userName = null;
        String[] repoName = null;
        String[] actionType =null;
        String[] eventDate = null;
        String[] userIcon = null;
        String[] uniqueName = null;
        int eventLength = 0;

        // Thread
        public void run(){
            try {
                getUserData();
                eventData = getReceivedEvents(myname);
                setData(eventData);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Handler run
            handler.post(new Runnable() {
                public void run() {

                    ListView listView = findViewById(R.id.listView);
                    baseAdapter mBaseAdapter = new baseAdapter(context, R.layout.list_layout, eventLength, eventDate, userName, repoName, actionType, eventType, uniqueName);
                    listView.setAdapter(mBaseAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });

                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                    TextView textView = findViewById(R.id.textView);
                    textView.setVisibility(View.GONE);
                }
            });
        }
        //  ユーザのデータをJSON形式で受け取り保存、ダウンロードする
        protected void getUserData() throws JSONException, IOException {

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

            while ((line = br.readLine()) != null) sb.append(line);
            userData = new JSONObject(String.valueOf(sb));
            connection.disconnect();
            is.close();
            isr.close();
            br.close();

            myname = userData.getString("login");
            String fileName = myname + ".bmp";
            File file = new File(getCacheDir(), fileName);
            if(file.exists()){}
            else {
                Bitmap bitMap = null;
                connection = (HttpURLConnection) new URL(userData.getString("avatar_url")).openConnection();
                connection.connect();

                is = connection.getInputStream();
                bitMap = BitmapFactory.decodeStream(new BufferedInputStream(is));
                FileOutputStream fos = new FileOutputStream(file);
                bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                is.close();
                fos.close();
            }
            file = new File(getCacheDir(), "myname.txt");
            FileOutputStream fos = null;
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(myname.getBytes());
            fos.close();

        }
        //  ユーザが受け取ったイベントを読み取る
        protected JSONArray getReceivedEvents(String myname) throws IOException {

            JSONArray data = new JSONArray();
            int page = 1;
            for(page=1; page<11; page++){

                StringBuilder sb = new StringBuilder();
                sb.append("https://api.github.com/users/");
                sb.append(myname);
                sb.append("/received_events");
                sb.append("?page=").append(page);
                sb.append("&access_token=").append(access_token);

                HttpURLConnection connection = (HttpURLConnection) new URL(sb.toString()).openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                sb = new StringBuilder();
                String line = null;

                while ((line = br.readLine()) != null)  sb.append(line);
                try {
                    JSONArray jsonArray = new JSONArray(String.valueOf(sb));
                    if(jsonArray.length() > 1) {
                        eventLength += jsonArray.length();
                        data.put(jsonArray);
                    }else {
                        page = 11;
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                connection.disconnect();
                is.close();
                isr.close();
                br.close();
            }
            return data;
        }
        //  引数で受け取ったname(ユーザ名)のアイコン画像をキャッシュに保存する
        protected String getUserIcon(String url,String name) throws IOException{
            String fileName = name.toLowerCase() + ".bmp";
            File file = new File(getCacheDir(), fileName);
            if(file.exists()){
                return fileName;
            }
            else {
                Bitmap bitMap = null;
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                bitMap = BitmapFactory.decodeStream(new BufferedInputStream(is));
                FileOutputStream fos = new FileOutputStream(file);
                bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                is.close();
                fos.close();
            }
            return fileName;
        }
        //  JSONからイベントタイプ、ユーザーネーム(login)、リポジトリなど、日付、アイコンをセットする
        protected void setData(JSONArray data) throws IOException, JSONException {
            eventType = new String[eventLength];
            userName = new String[eventLength];
            repoName = new String[eventLength];
            actionType = new String[eventLength];
            eventDate = new String[eventLength];
            userIcon = new String[eventLength];
            uniqueName = new String[eventLength];

            for (int j = 0; j < data.length(); j++) {              // page
                JSONArray json = data.getJSONArray(j);         // object in page
                for (int i = 0; i < json.length(); i++) {
                    eventType[j * 30 + i] = json.getJSONObject(i).getString("type");
                    if (eventType[j * 30 + i].equals("WatchEvent")) {
                        actionType[j * 30 + i] = json.getJSONObject(i).getJSONObject("payload").getString("action");
                        uniqueName[j * 30 + i] = "null";
                    } else if (eventType[j * 30 + i].equals("MemberEvent")) {
                        actionType[j * 30 + i] = json.getJSONObject(i).getJSONObject("payload").getString("action");
                        uniqueName[j * 30 + i] = json.getJSONObject(i).getJSONObject("payload").getJSONObject("member").getString("login");
                    } else if (eventType[j * 30 + i].equals("CreateEvent")) {
                        actionType[j * 30 + i] = json.getJSONObject(i).getJSONObject("payload").getString("ref_type");
                        uniqueName[j * 30 + i] = "null";
                    } else if (eventType[j * 30 + i].equals("CommitCommentEvent")) {
                        actionType[j * 30 + i] = json.getJSONObject(i).getJSONObject("payload").getString("comment");
                        uniqueName[j * 30 + i] = "null";
                    } else if (eventType[j * 30 + i].equals("DeleteEvent")) {
                        actionType[j * 30 + i] = json.getJSONObject(i).getJSONObject("payload").getString("ref_type");
                        uniqueName[j * 30 + i] = "null";
                    } else if (eventType[j * 30 + i].equals("ForkEvent")) {
                        actionType[j * 30 + i] = json.getJSONObject(i).getJSONObject("payload").getString("forkee");
                        uniqueName[j * 30 + i] = "null";
                    } else if (eventType[j * 30 + i].equals("PublicEvent")) {
                        actionType[j * 30 + i] = "null";
                        uniqueName[j * 30 + i] = "null";
                    } else {
                        eventType[j * 30 + i] = "null";
                        uniqueName[j * 30 + i] = "null";
                    }

                    userName[j * 30 + i] = json.getJSONObject(i).getJSONObject("actor").getString("login");
                    repoName[j * 30 + i] = json.getJSONObject(i).getJSONObject("repo").getString("name");
                    eventDate[j * 30 + i] = json.getJSONObject(i).getString("created_at");
                    userIcon[j * 30 + i] = getUserIcon(json.getJSONObject(i).getJSONObject("actor").getString("avatar_url"), userName[j * 30 + i]);
                }
            }
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_print, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_myprofile) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("myprofile://xmz")));
            return true;
        } else if (id == R.id.action_myevent) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
