package jp.example.ghclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class myprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Profile");
        setSupportActionBar(toolbar);

        String myname = null;
        try {
            myname = getNameByCache();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView imageView = findViewById(R.id.imageView);
        File file = new File(getCacheDir(), myname+".bmp");
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
        TextView textView = findViewById(R.id.textView);
        textView.setText(myname.toString());
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
        File file = new File(getCacheDir(), "myname.txt");
        byte[] buffer = new byte[Integer.parseInt(String.valueOf(file.length()))];
        if (file.exists()) {
            fis = new FileInputStream(file);
            fis.read(buffer);
            myname = new String(buffer, "UTF-8");
        }
        fis.close();

        return myname;
    }
}
