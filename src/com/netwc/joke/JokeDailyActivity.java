package com.netwc.joke;

import java.util.ArrayList;

import com.netwc.DataProvider.DataCenter;
import com.netwc.DataProvider.ProviderJokeJi;
import com.netwc.Entities.CategoryInfo;
import com.netwc.Entities.JokeInfo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JokeDailyActivity extends Activity{
	
	Thread newThread=new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			DataCenter dataCenter=new DataCenter(JokeDailyActivity.this);		
			ProviderJokeJi jokeJi=new ProviderJokeJi();
			ArrayList<CategoryInfo> categoryInfos=jokeJi.GetJokeCategorys();
			if(categoryInfos!=null){
				for (CategoryInfo categoryInfo : categoryInfos) {
					dataCenter.AddCategoryInfo(categoryInfo);
					Log.v("Category",categoryInfo.Name);
				}
			}
			
			ArrayList<JokeInfo> jokes=jokeJi.GetNewJokeInfos();
			if(null!=jokes && jokes.size()>0){
				for (JokeInfo jokeInfo : jokes) {
					dataCenter.AddJokeInfo(jokeInfo);
					Log.v("AddJoke:",jokeInfo.Title);
				}
			}			
			dataCenter.Dispose();
		}
	});
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_list);
		
		Button btnDownLoad=(Button)findViewById(R.id.daily_button_TestDownLoad);
		btnDownLoad.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newThread.start();
			}
		});
		
	}	
}
