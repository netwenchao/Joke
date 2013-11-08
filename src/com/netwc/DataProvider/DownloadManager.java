package com.netwc.DataProvider;

import java.util.ArrayList;
import java.util.List;

import com.netwc.Entities.CategoryInfo;
import com.netwc.Entities.JokeInfo;

import android.content.Context;
import android.util.Log;

public class DownloadManager {
	private Context mContext;
	private List<AbsJokeProvider> providers;
	private DataCenter dataCenter;
	
	public DownloadManager(Context ctx){
		mContext=ctx;
		providers=new ArrayList<AbsJokeProvider>();
		providers.add(new ProviderJokeJi());
		providers.add(new ProviderQiuShi());
	}
	
	public void Download(){
		//providers.get(0).Execute()
	}
	
	private void InitDBCenter(){
		if(dataCenter!=null) dataCenter=new DataCenter(mContext);
	}
	
	/*             
     *1.Update Category 
     *2.Get Daily Jokes
     *3.Download tasks
     *4.Download index
     */

    private void UpdateCategorys()
    {
    	try {
	    	for (AbsJokeProvider provider : providers) {
				provider.Categorys=provider.GetJokeCategorys();
				if(provider.Categorys!=null && provider.Categorys.size()>0){
					InitDBCenter();
					//Add Category to database
					for (CategoryInfo categoryInfo : provider.Categorys) {
						dataCenter.AddCategoryInfo(categoryInfo);
					}
				}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    }

    private void GetDailyInfos()
    {
    	try {		
    		Log.v("DownLoadManager","GetDailyInfos");
	        for (AbsJokeProvider provider : providers) {
				List<JokeInfo> dailyJokes=provider.GetNewJokeInfos();
				if(null!=dailyJokes && dailyJokes.size()>0){
					for (JokeInfo jokeInfo : dailyJokes) {
						dataCenter.AddJokeInfo(jokeInfo);
					}
				}
			}    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void DownloadTasks()
    {
        /*
         *foreach(provider in jokeProviders){                    
         *  foreach(categorypage in provider.categorypage){                 
         *      var pages=new List<>()£ûnew PageInfo{Url:"",PageIdx:"",IsFeached:""}£ý;
         *      while(pages.find(t=>t.isFeached==false).count()>0)
             *      GetJokeTitleList(){
             *      
             *      }
             *      GetCategoryPages(){
             *      
             *      }
         *  }
         * }                 
         */
    }
}
