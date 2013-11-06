package com.netwc.joke;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class JokeApp extends Application{
	private List<Activity> mainActivity = new ArrayList();
	public String domobPublisherId="";

	public void AddActivity(Activity paramActivity){
		this.mainActivity.add(paramActivity);
	}

	public void finishAll(){
		Iterator localIterator=this.mainActivity.iterator();
		while(true){
			if(!localIterator.hasNext()) return;
			Activity localActivity=(Activity)localIterator.next();
			if(null!=localActivity && !localActivity.isFinishing())
				localActivity.finish();
		}
	}

	public void onCreate()
  	{
    	super.onCreate();    	
	}
}
