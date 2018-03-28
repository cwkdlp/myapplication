package com.example.jung.autojoin;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/**
 * HTTP 요청 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 *
 */


public class MainActivity extends AppCompatActivity {

    EditText input01;
    WebView webView;

    public static String defaultUrl = null;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input01 = (EditText) findViewById(R.id.input01);

        webView = (WebView) findViewById(R.id.webView);

        // 버튼 이벤트 처리
        Button requestBtn = (Button) findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String seach = input01.getText().toString();
                String urlStr = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="+seach;

                ConnectThread thread = new ConnectThread(urlStr);
                thread.start();

            }
        });

    }

    /**
     * 소켓 연결할 스레드 정의
     */
    class ConnectThread extends Thread {
        String urlStr;

        public ConnectThread(String urlStr) {
            this.urlStr = urlStr;
        }

        public void run() {

            try {

                handler.post(new Runnable() {
                    public void run() {

                        webView.loadUrl(urlStr);
                        webView.setWebViewClient(new WebViewClient());
                        WebSettings set = webView.getSettings();
                        set.setJavaScriptEnabled(true); // javascript를 실행할 수 있도록 설
                        set.setBuiltInZoomControls(true); // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정
                        set.setPluginState(WebSettings.PluginState.ON_DEMAND); // 플러그인을 사용할 수 있도록 설정
                        set.setSupportMultipleWindows(true); // 여러개의 윈도우를 사용할 수 있도록 설정
                        set.setSupportZoom(true); // 확대,축소 기능을 사용할 수 있도록 설정
                        set.setBlockNetworkImage(false); // 네트워크의 이미지의 리소스를 로드하지않음
                        set.setLoadsImagesAutomatically(true); // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
                        set.setUseWideViewPort(true); // wide viewport를 사용하도록 설정
                        set.setCacheMode(WebSettings.LOAD_NO_CACHE); // 웹뷰가 캐시를 사용하지 않도록 설정
                    }
                });
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

//https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=검색어 전체
//https://search.naver.com/search.naver?where=post&sm=tab_jum&query=검색어 블로그