package jp.example.ghclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncToken extends AsyncTask<String, Void, String> {

    private Uri uri;
    private Context context;
    private String auth_code;
    private String client_id;
    private String client_secret;

    public AsyncToken(Uri uri, Context context) {
        this.uri = uri;
        this.context = context;
    }
    @Override
    protected String doInBackground(String... param) {

        auth_code = param[0];
        client_id = param[1];
        client_secret = param[2] ;
        String access_token = null;

        try {
            access_token = getTokens();
        } catch (IOException e) {
            System.out.println("errorだよ〜ん : " + this);
        }

        return access_token;
    }
    protected String getTokens() throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append("https://github.com/login/oauth/access_token");
        sb.append("?code=").append(auth_code);
        sb.append("&client_id=").append(client_id);
        sb.append("&client_secret=").append(client_secret);
        byte[] payload = sb.toString().getBytes();

        HttpURLConnection connection = (HttpURLConnection) new URL(sb.toString()).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Length", String.valueOf(payload.length));
        connection.getOutputStream().write(payload);
        connection.getOutputStream().flush();

        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        sb = new StringBuilder();
        while ((line = br.readLine()) != null) { sb.append(line); }
        String tokens[] = sb.toString().split("[= &]");

        connection.disconnect();
        is.close();
        isr.close();
        br.close();

        return tokens[1];
    }
    @Override
    protected void onPostExecute(String access_token){

        Intent intent = new Intent(Intent.ACTION_VIEW , uri);
        intent.putExtra("access_token", access_token);
        context.startActivity(intent);

    }
}

