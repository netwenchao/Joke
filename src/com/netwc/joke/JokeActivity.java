package com.netwc.joke;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdSize;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.kyview.AdViewInterface;
import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.AdViewTargeting.UpdateMode;
import com.tencent.exmobwin.MobWINManager;
import com.tencent.exmobwin.Type;
import com.tencent.exmobwin.banner.AdListener;
import com.tencent.exmobwin.banner.TAdView;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class JokeActivity extends Activity {

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	String[] Catalogs;
	ListView mListView;
	LinearLayout contentAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_main);
		/*mListView=(ListView)findViewById(R.id.bookList);
		this.Catalogs = getResources().getStringArray(R.array.Catalogs);
		setListAdapter(Catalogs);
		contentAd=(LinearLayout)findViewById(R.id.adContainer);
		AdViewTargeting.setRunMode(RunMode.NORMAL); 
		AdViewTargeting.setUpdateMode(UpdateMode.DEFAULT); 
		AdViewLayout adViewLayout = new AdViewLayout(this, "SDK20131012100521kidb0cxesk8oste"); 
		//adViewLayout.setAdViewInterface(this); 
		adViewLayout.setAdViewInterface(new AdViewInterface(){
			@Override
			public void onClickAd() {
				// TODO Auto-generated method stub
				Log.v("AD","Clicked");
			}

			@Override
			public void onDisplayAd() {
				// TODO Auto-generated method stub
				Log.v("AD","Displayed");
			}
			
		});
		contentAd.addView(adViewLayout); 
		contentAd.invalidate();
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				Bundle bl=new Bundle();
				bl.putInt("Num", arg2);
				bl.putString("Title", Catalogs[arg2]);
				Intent intent=new Intent(JokeActivity.this,JokeListActivity.class);
				intent.putExtras(bl);
				startActivity(intent);
			}			
		});
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.joke, menu);
		return true;
	}
	public void setListAdapter(String[] paramArrayOfString)
	{
	    try
	    {
	      ArrayList localArrayList = new ArrayList();
	      for (int i = 0; ; i++)
	      {
	        if (i >= paramArrayOfString.length)
	        {
	          SimpleAdapter localSimpleAdapter = new SimpleAdapter(this, localArrayList,R.layout.book_item, new String[] {"ItemTitle" }, new int[] {R.id.ItemTitle});
	          this.mListView.setAdapter(localSimpleAdapter);
	          return;
	        }
	        HashMap localHashMap = new HashMap();	        
	        localHashMap.put("ItemTitle", paramArrayOfString[i]);
	        localArrayList.add(localHashMap);
	      }
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	}

}
