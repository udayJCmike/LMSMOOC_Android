package com.deemsys.lmsmooc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

public class SampleWebView extends Activity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_text_one);
		webView = (WebView) findViewById(R.id.webView1);
                String customHtml = "<body>" +
                		"<h1>Heading Text</h1>" +
                		"<p>This tutorial</p>"+
            "<p><strong>HTML </strong></p>"+            
            "<iframe src=http://www.youtube.com/embed/iZilpyPSPKY/>" +
            "<blockquote>Example from <a href=\"www.javatechig.com\">" +
            "Javatechig.com<a></blockquote>"+
                		"<p>This tutorial</p>"+
            "<p><strong>HTML </strong></p>"+            
          
            "<blockquote>Example from <a href=\"www.javatechig.com\">" +
            "Javatechig.com<a></blockquote>"+
            "Javatechig.com<a></blockquote>"+
                		"<p>This tutorial</p>"+
            "Javatechig.com<a></blockquote>"+
                		"<p>This tutorial</p>"+
            "Javatechig.com<a></blockquote>"+
                		"<p>This tutorial</p>"+
            "Javatechig.com<a></blockquote>"+
                		"<p>This tutorial</p></body>";
		webView.loadData(customHtml, "text/html", "UTF-8");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
}