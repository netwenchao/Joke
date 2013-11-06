package com.netwc.joke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kyview.AdViewInterface;
import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.AdViewTargeting.UpdateMode;
import com.netwc.Adapters.CategoryAdapter;
import com.netwc.DataProvider.DataCenter;
import com.netwc.Entities.CategoryInfo;

import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class JokeListActivity extends Activity{
    ListView mListView;
    int pageSize=20;
    int pageIdx=0;
    public boolean isLoading=false;
    public boolean isScrolliing=true;
    private Cursor dataCursor;
    public DataCenter dCenter;
    public View FooterView;    
    public Spinner  dplCategoryOfJokes;
    public List<CategoryInfo> categorys;
    
    public int CurrentCategory() {
    	CategoryInfo info= ((CategoryInfo)dplCategoryOfJokes.getSelectedItem());
    	return info==null?0:info.ID;
	}
    
    private OnScrollListener mOnScrollListener=new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {	
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_FLING:	
				isScrolliing=true;
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				isScrolliing=true;
				break;
			case OnScrollListener.SCROLL_STATE_IDLE:
				isScrolliing=false;
				break;
			default:
				break;
			}
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if(isLoading) return;			
			Log.v("OnScorll","FirstVisibleItem:"+firstVisibleItem);
			Log.v("OnScorll","visibleItemCount:"+visibleItemCount);
			if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount>0){
				FetchDataAsyncTask fc=new FetchDataAsyncTask();
				fc.execute(new Integer[]{});
			}
		}
	};
	
	public Handler updateListViewData=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){				
				bindData2List();
				((TextView)(FooterView.findViewById(R.id.item_main_footer_page))).setText("第"+String.valueOf(pageIdx+1)+"页");
				//Toast.makeText(JokeListActivity.this,"第"+String.valueOf(pageIdx)+1+"页",Toast.LENGTH_LONG);
			}
		}		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_main);
		this.mListView = ((ListView)findViewById(R.id.jokeList));
		dplCategoryOfJokes=(Spinner) findViewById(R.id.dplCategoryOfJokes);
		this.mListView.setOnScrollListener(mOnScrollListener);	
		dCenter=new DataCenter(this);
		categorys=dCenter.GetCategorys();
		
		CategoryAdapter categoryAdapter=new CategoryAdapter(this,categorys);
		dplCategoryOfJokes.setAdapter(categoryAdapter);		
		dataCursor=dCenter.GetJokeInfo(pageSize,pageIdx,CurrentCategory());
		
		FooterView=((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_main_footer_view,null);		
		((TextView)(FooterView.findViewById(R.id.item_main_footer_page))).setText("第"+String.valueOf((pageIdx+1))+"页");
		this.mListView.addHeaderView(FooterView,null,false);
		bindData2List();
		
		dplCategoryOfJokes.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				pageIdx=0;
				Log.v("Category Selected",String.valueOf(CurrentCategory()));
				dataCursor=dCenter.GetJokeInfo(pageSize, pageIdx,CurrentCategory());
				Message msgMessage=new Message();
				msgMessage.what=1;
				updateListViewData.sendMessage(msgMessage);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}
	
	public void bindData2List()
	{		
	    try
	    {	
	    	if(dataCursor==null) return;
	    	CursorAdapter adapter=new CursorAdapter(this,dataCursor) {				
				@Override
				public View newView(Context context, Cursor cursor, ViewGroup parent) {
					// TODO Auto-generated method stub
					LayoutInflater inflat=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					return inflat.inflate(R.layout.item_joke_main,parent,false);
				}
				
				@Override
				public void bindView(View view, Context context, Cursor cursor) {
					JokeItemControl controls=GetJokeItemFromView(view);
					controls.item_joke_title.setText(cursor.getString(1));
					controls.item_joke_day.setText("1");
					controls.item_joke_month.setText("11月");
					controls.item_joke_from.setText(cursor.getString(6));
					controls.item_joke_content.setText(cursor.getString(3).replace(" 　","\n").replace("　　","\n"));	
					controls.item_joke_isFav.setSelected(cursor.getShort(cursor.getColumnIndex("IsFavourite"))==1);
					controls.item_joke_isFav.setTag(cursor.getInt(0));
					controls.item_joke_isFav.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							int itemId= Integer.valueOf(buttonView.getTag().toString());
							dCenter.Add2Fav(itemId, isChecked);
						}
						
					});
				}
			};
			mListView.setAdapter(adapter);
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	}
	
	private JokeItemControl GetJokeItemFromView(View v){
		JokeItemControl rsltControl=new JokeItemControl();
		rsltControl.item_joke_day=(TextView)v.findViewById(R.id.item_joke_day);
		rsltControl.item_joke_month=(TextView)v.findViewById(R.id.item_joke_month);
		rsltControl.item_joke_from=(TextView)v.findViewById(R.id.item_joke_from);
		rsltControl.item_joke_title=(TextView)v.findViewById(R.id.item_joke_title);
		rsltControl.item_joke_content=(TextView)v.findViewById(R.id.item_joke_content);
		rsltControl.item_joke_isFav=(CheckBox)v.findViewById(R.id.item_joke_isFav);
		return rsltControl;
	}
	
 	public class JokeItemControl{
		public TextView item_joke_day;
		public TextView item_joke_month;
		public TextView item_joke_title;
		public TextView item_joke_from;
		public TextView item_joke_content;		
		public CheckBox item_joke_isFav;
	}


 	private class FetchDataAsyncTask extends AsyncTask<Integer,Void,Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//mListView.addFooterView(v);
		}

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			
			isLoading=true;
			pageIdx++;
			dataCursor=dCenter.GetJokeInfo(pageSize, pageIdx,CurrentCategory());
			Message msgMessage=new Message();
			msgMessage.what=1;
			updateListViewData.sendMessage(msgMessage);
			return null;			
			/**/
		}
 		
		protected void onPostExecute(Void result){
			isLoading=false;
		}
 	}
}
