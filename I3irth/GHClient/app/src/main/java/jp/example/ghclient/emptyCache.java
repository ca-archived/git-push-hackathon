package jp.example.ghclient;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class emptyCache extends AppCompatActivity {

    static final String client_id = "01dd29909b4db21d26d3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_cache);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);

        final WebView mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("Url : "+url);
                if (!url.startsWith("file:") && !url.startsWith("http:") && !url.startsWith("https:")) {
                    mWebView.stopLoading();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                 }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.loadUrl("https://github.com/login/oauth/authorize?client_id=" + client_id);
        System.out.println("EMPTYYYYYYYYY");
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }

}
