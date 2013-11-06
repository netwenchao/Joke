package com.netwc.joke;

import com.netwc.joke.R;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabActivityMain extends ActivityGroup{
	private TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_start_page);		
		tabHost=(TabHost)findViewById(R.id.tabhost);
		tabHost.setup(getLocalActivityManager());
		tabHost.addTab(tabHost.newTabSpec("A").setIndicator(getString(R.string.tab_spec_view)).setContent(new Intent(this,JokeListActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("B").setIndicator(getString(R.string.tab_spec_daily)).setContent(new Intent(this,JokeDailyActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("C").setIndicator(getString(R.string.tab_spec_fav)).setContent(new Intent(this,JokeFavListActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("D").setIndicator(getString(R.string.tab_spec_settings)).setContent(new Intent(this,JokeSettingsActivity.class)));
	}
	
}
