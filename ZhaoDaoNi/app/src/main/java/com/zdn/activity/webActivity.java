package com.zdn.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zdn.R;
import com.zdn.interf.webAndroidInterface;
import com.zdn.internet.InternetComponent;

public class webActivity extends zdnBasicActivity{

	private WebView webView;
	
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_activity);
		
		webView = (WebView) findViewById(R.id.webview );
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDefaultTextEncodingName("utf-8");
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
									 JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}
		});
		webView.addJavascriptInterface(new webAndroidInterface(this), "androidInterfaceMethod");

		webView.loadUrl( InternetComponent.WEBSITE_ADDRESS_TASK_LIST );

	}

	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
        	webView.goBack(); // goBack()��ʾ����WebView����һҳ��
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
