package com.netwc.DataProvider;

import java.util.ArrayList;
import java.util.List;

import com.netwc.Entities.JokeInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JokeItemAdapter extends BaseAdapter{
	List<JokeInfo> mData=new ArrayList();
	private Context mContext;
	private LayoutInflater mInflater;

	public JokeItemAdapter(Context context){
		mContext=context;
		mInflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mData.get(position).ID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		JokeInfo joke=(JokeInfo)getItem(position);
		//LinearLayout view=mInflater.inflater();
		return null;
	}

	static class ViewHolder{
		public TextView Daily_item_jokeTitle;
		public TextView Daily_item_jokeContent;
		public TextView Daily_item_jokeUrl;
		public TextView Daily_item_jokeUpdate;
	}
}