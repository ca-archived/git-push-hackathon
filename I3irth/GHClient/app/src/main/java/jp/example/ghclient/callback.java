package jp.example.ghclient;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class callback extends AppCompatActivity {

    static final String client_id = "";                 // Client ID
    static final String client_secret = "";             // Client Secret

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);

        Uri uri = getIntent().getData();
        String auth_code = uri.getQueryParameter("code");

        uri = Uri.parse("event://xmz");
        Context context = this;
        AsyncToken task = new AsyncToken(uri, context);
        task.execute(auth_code, client_id, client_secret);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }
}

