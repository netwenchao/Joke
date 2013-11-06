package com.netwc.joke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SplashActivity extends Activity{
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		webView=(WebView)findViewById(R.id.wvsplash);
		webView.getSettings().setJavaScriptEnabled(true);		
		webView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view,String url){
				view.loadUrl("http://www.qiushibaike.com/");
				return true;		
			}
		});
		/*
		(new Handler()).postDelayed(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent mainPage=new Intent(SplashActivity.this,MainAcvitity.class);
				startActivity(mainPage);
				finish();
			}
		},3000);
		*/		
	}	
}
