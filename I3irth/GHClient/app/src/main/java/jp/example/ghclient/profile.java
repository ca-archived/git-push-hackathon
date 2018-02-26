package jp.example.ghclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class profile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);




    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }
}
