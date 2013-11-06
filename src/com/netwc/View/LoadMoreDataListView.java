package com.netwc.View;

import com.netwc.joke.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class LoadMoreDataListView extends ListView implements AbsListView.OnScrollListener{

	private Context mContext;
	private Boolean mRefreshing;
	private RelativeLayout mfootView;
	private ProgressBar mfootView_processBar;
	private LayoutInflater mInflater;
	public OnRefreshListener mOnRefreshListener;
	public LoadMoreDataListView(Context context) {
		super(context);
		this.mContext=context;
	}
	public LoadMoreDataListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
	}
	public LoadMoreDataListView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		this.mContext=context;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(firstVisibleItem+visibleItemCount>=totalItemCount && !mRefreshing){
			mRefreshing=true;					
			this.mfootView.setVisibility(View.VISIBLE);
			//To Refresh
			if(null!=mOnRefreshListener) this.mOnRefreshListener.onRefresh();
			
		}	
	}

	public void OnRefreshCompleted(){
		mRefreshing=false;
	}

	private void Init(Context context){
		this.mInflater = ((LayoutInflater)context.getSystemService("layout_inflater"));
		this.mfootView=(RelativeLayout)mInflater.inflate(R.layout.pull_to_refresh_header,null,false);
		this.mfootView.setVisibility(View.GONE);
		this.mfootView_processBar=(ProgressBar)mfootView.findViewById(R.id.pull_to_refresh_progress);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	public static abstract interface OnRefreshListener
	{
		public abstract void onRefresh();
	}
}
