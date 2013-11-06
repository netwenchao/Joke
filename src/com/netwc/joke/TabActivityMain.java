package com.netwc.joke;

import java.util.List;

import com.netwc.joke.R;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class TabActivityMain extends Activity{
	private TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start_page);
		tabHost=(TabHost)findViewById(R.id.tabhost);
		tabHost.addTab(tabHost.newTabSpec("A").setIndicator("").setContent(new Intent(this,JokeListActivity.class)));
		tabHost.setCurrentTab(0);
	}
	
}
