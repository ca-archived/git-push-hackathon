package jp.example.ghclient;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class callback extends AppCompatActivity {

    static final String client_id = "01dd29909b4db21d26d3";
    static final String client_secret = "32a28d1f7ecae1958671f34d2eb7ff59b717fbe5";

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);

        System.out.println("Start callback");

        Uri uri = getIntent().getData();
        String auth_code = uri.getQueryParameter("code");

        System.out.println("Callback-Code : " + auth_code);

        uri = Uri.parse("print://xmz");
        Context context = this;
        AsyncToken task = new AsyncToken(uri, context);
        task.execute(auth_code, client_id, client_secret);
    }
}

