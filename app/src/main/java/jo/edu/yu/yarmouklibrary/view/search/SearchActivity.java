package jo.edu.yu.yarmouklibrary.view.search;

import androidx.appcompat.app.AppCompatActivity;
import jo.edu.yu.yarmouklibrary.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        WebView webView=findViewById(R.id.search_web_view);
//        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/search.html");
//        webView.loadUrl("https:///sis.yu.edu.jo/");
        Log.i("I/chromium:start",webView.getUrl());

    }
//    public class myWebClient extends WebViewClient
//    {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            // TODO Auto-generated method stub
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//            view.loadUrl(url);
//            return true;
//        }
//    }


//    @Override
//    public void onBackPressed() {
//        final WebView webView=findViewById(R.id.search_web_view);
//        webView.evaluateJavascript("document.getElementById(\"P101_USERNAME\").value=\"2016980044\";" +
//                                        "document.getElementsByClassName(\"password\")[0].value=\"123mmm123\";"
//                ,new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//                webView.loadUrl("javascript:apex.submit('LOGIN');");
//                Log.i("I/chromium:go",webView.getUrl());
//
//            }
//        });
//
//
//    }
}
