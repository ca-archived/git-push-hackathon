package jp.example.ghclient;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        String userName =  getIntent().getData().getQueryParameter("username");

        ImageView imageView = findViewById(R.id.imageView);
        File file = new File(getCacheDir(), userName.toLowerCase()+".bmp");
        if(file.exists()){
        try {
            InputStream is = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(is));
            is.close();
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        } else {
            Resources resources = getResources();
            imageView.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.octocat));
        }
        TextView textView = findViewById(R.id.textView);
        textView.setText(userName.toString());
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
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("myprofile://xmz")));
            return true;
        } else if (id == R.id.action_myevent) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("event://xmz")));
            Toast.makeText(this, "Event", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected String getNameByCache() throws IOException{
        String myname=null;
        FileInputStream fis = null;
        byte[] buffer = new byte[40];
        File file = new File(getCacheDir(), "myname.txt");
        if (file.exists()) {
            fis = new FileInputStream(file);
            fis.read(buffer);
            myname = new String(buffer, "UTF-8");
        }
        fis.close();

        return myname;
    }
}
