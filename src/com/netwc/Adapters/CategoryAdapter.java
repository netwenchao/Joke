package com.netwc.Adapters;

import java.util.ArrayList;
import java.util.List;

import com.netwc.Entities.CategoryInfo;
import com.netwc.joke.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter{
	public List<CategoryInfo> categorys;
	private Context mContext;
	
	public CategoryAdapter(Context context,List<CategoryInfo> categories){
		this.categorys=categories;
		this.mContext=context;
	}
	
	@Override
	public int getCount() {
		return categorys.size();
	}

	@Override
	public Object getItem(int idx) {
		// TODO Auto-generated method stub
		return categorys.get(idx);
	}

	@Override
	public long getItemId(int idx) {
		// TODO Auto-generated method stub
		return categorys.get(idx).ID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup root) {
		// TODO Auto-generated method stub
		LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView= inflater.inflate(com.netwc.joke.R.layout.dropdown_item_category , root,false);
		((TextView)convertView.findViewById(R.id.tvItemTitle)).setText(categorys.get(position).Name);
		return convertView;
		
	}

}
