package com.netwc.DataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.netwc.Entities.CategoryInfo;
import com.netwc.Entities.JokeInfo;

import android.R.bool;
import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.style.UpdateAppearance;
import android.util.Log;

public class DataCenter {
	private Context mContext;
	private SQLiteDatabase db;
	private String dbName="jokeInfo.db3";
	private String packageNameString="com.netwc.joke";
	
	public DataCenter(Context ctx){
		this.mContext=ctx;
		db=OpenDataBase(dbName);		
	}
	
	private SQLiteDatabase OpenDataBase(String dbName){
		File dataFolder=mContext.getFilesDir(); 
		File dbFile=new File(dataFolder.getAbsolutePath()+"/"+dbName);
		Log.v("DataCenter", dbFile.getAbsolutePath());
		if(!dbFile.exists()){
			try {				
				InputStream inputStream=mContext.getAssets().open("jokeInfo.png");				
				FileOutputStream fso=new FileOutputStream(dbFile);
				byte[] buffer=new byte[1024];
				int readCount=0;
				while((readCount=inputStream.read(buffer))>0){
					fso.write(buffer);
				}
				inputStream.close();
				fso.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		SQLiteDatabase db=SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,SQLiteDatabase.CREATE_IF_NECESSARY);
		return db;
	} 

	/*
	 * Add CategoryInfo
	 * */
	public boolean AddCategoryInfo(CategoryInfo category){
		try {			
			int id=GenerateCategoryId();
			if(!CategoryExists(category.Name,category.PageUrl)){
				db.execSQL("insert into categoryinfo(ID,Name,PageUrl) values(?,?,?)", new Object[]{
						id,category.Name,category.PageUrl
				});				
			}else{
				Log.v("DataCenter","Category with Name:"+category.Name+"  ,PageUrl:"+category.PageUrl+" exists.");
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * Add Joke Info to database*/
	public boolean AddJokeInfo(JokeInfo joke){
		try {
			if(!JokeExists(joke.Title,joke.Url)){
				int id=GenerateJokeInfoId();
				joke.DateAdd=System.currentTimeMillis();
				String jokeInsetString="insert into JokeInfo(_ID,Title,Url,Content,Category,SiteDate,DataFrom,DateAdd,IsDownLoad,IsNew,IsFavourite) values(?,?,?,?,?,?,?,?,?,?,?)";
				db.execSQL(jokeInsetString,new Object[]{
					id,joke.Title,joke.Url,joke.Content,joke.CategoryID,joke.SiteDate,joke.DataFrom,joke.DateAdd,joke.IsDownLoad?1:0,joke.IsNew?1:0,joke.IsFavourite?1:0
				});				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}		
		return true;
	}

	public List<CategoryInfo> GetCategorys(){
		List<CategoryInfo> arr=new ArrayList<CategoryInfo>();
		Cursor cur=null;
		try {
			/**/
			Calendar calendar=Calendar.getInstance();		
			calendar.add(Calendar.DATE,-30);			
			Log.v("Time", String.valueOf(calendar.getTimeInMillis()));
			cur=db.rawQuery("select * from categoryinfo",null);					
			while(cur.moveToNext()){
				CategoryInfo cate= new CategoryInfo();
				cate.ID=cur.getInt(0);
				cate.Name=cur.getString(1);
				cate.PageUrl=cur.getString(2);
				arr.add(cate);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			if(cur!=null) cur.close();
		}
		return arr;
	}
	
	public Boolean CategoryExists(String name,String PageUrl){
		try {
			Cursor cur=db.rawQuery("select id from categoryinfo where name=? and pageUrl=?",new String[]{name,PageUrl});
			if(cur.getCount()>0){
				cur.close();
				return true;
			}
			cur.close();			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public Boolean JokeExists(String name,String url){
		try {
			Cursor cur=db.rawQuery("select _id from jokeinfo where title=? and url=?",new String[]{name,url});
			if(cur.getCount()>0){
				cur.close();
				return true;
			}
			cur.close();			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	/*
	 * 
	 * */
	public Cursor GetJokeInfo(int pageSize,int pageIndex,int categoryId){
		try {
			if(categoryId==0){
				return db.rawQuery("select * from jokeInfo limit "+pageSize+" offset "+pageSize*pageIndex, null);
			}
			return db.rawQuery("select * from jokeInfo where category="+String.valueOf(categoryId)+" limit "+pageSize+" offset "+pageSize*pageIndex, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * */
	public Cursor GetDailyJokeInfo(int pageSize,int pageIndex){
		try {			
			Calendar cal=Calendar.getInstance();			
			return db.rawQuery("select * from jokeInfo limit "+pageSize+" offset "+pageSize*pageIndex+" order by dateadd desc",null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * Add to or remove from fav
	 * */
	public boolean Add2Fav(int jokeId,boolean bFav){
		try {
			String jokeInsetString="update JokeInfo set IsFavourite=? where _id=?";
			db.execSQL(jokeInsetString,new Object[]{bFav?1:0,jokeId});				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}		
		return true;
	}

	/*
	 * Get ID
	 * */
	public int GenerateCategoryId(){
		try {
			Cursor cur=db.rawQuery("select max(id) from categoryinfo",null);
			if(cur.getCount()>0){
				cur.moveToFirst();
				return cur.getInt(0)+1;
			}
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

	
	/*
	 * Get ID
	 * */
	public int GenerateJokeInfoId(){
		try {
			Cursor cur=db.rawQuery("select max(_id) from jokeInfo",null);
			if(cur.getCount()>0){
				cur.moveToFirst();
				return cur.getInt(0)+1;
			}
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

	public void Dispose(){
		if(db.isOpen()) db.close();		
	}
}
