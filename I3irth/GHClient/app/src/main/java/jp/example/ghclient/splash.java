package jp.example.ghclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_splash);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);

        File file = new File(getCacheDir(), "cache.txt");
        if(file.exists()){
            String access_token = getByCache(file);
            Uri uri = Uri.parse("event://xmz");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra("access_token", access_token);
            startActivity(intent);
        }else{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("emptycache://xmz")));
        }
    }
    protected String getByCache(File file) {
        FileInputStream fis = null;
        byte[] buffer = new byte[40];
        String access_token = null;
        file = new File(getCacheDir(), "cache.txt");
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                fis.read(buffer);
                access_token = new String(buffer, "UTF-8");
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }
}
