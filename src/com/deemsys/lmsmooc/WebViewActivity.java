package com.deemsys.lmsmooc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	String url;
	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		getActionBar().hide();
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		Intent i = getIntent();
		url = i.getExtras().getString("urlpassing", "");
		webView.loadUrl(url);

	}

}