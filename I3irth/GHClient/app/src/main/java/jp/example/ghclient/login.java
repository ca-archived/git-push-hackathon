package jp.example.ghclient;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    static final String client_id = "01dd29909b4db21d26d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        System.out.println("-----Start Login-----");

        StringBuilder sb = new StringBuilder();
        sb.append("https://github.com/login/oauth/authorize");
        sb.append("?client_id=").append(client_id);

        JSONArray json = new JSONArray();
        JSONArray json2 = null;
        JSONObject object = new JSONObject();

//        for(int i=0; i<=9; i++){
//            try {
//                json2 = new JSONArray();
//                json.put(i,json2);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            System.out.println(json2);
//        }
        Uri uri = Uri.parse(sb.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW , uri);
        startActivity(intent);
    }
}
