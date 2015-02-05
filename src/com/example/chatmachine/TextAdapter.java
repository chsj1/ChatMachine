package com.example.chatmachine;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter {

	private List<ListData> lists;
	private Context mContext;
	private RelativeLayout layout;
	
	
	public TextAdapter(List<ListData> lists,Context mContext) {
		this.lists = lists;
		this.mContext = mContext;
	}
	
	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		
		if(lists.get(position).getFlag() == ListData.RECEIVER){
			layout = (RelativeLayout) inflater.inflate(R.layout.leftitem, null);
		}
		
		if(lists.get(position).getFlag() == ListData.SEND){
			layout = (RelativeLayout) inflater.inflate(R.layout.rightitem, null);
		}
		
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		tv.setText(lists.get(position).getContent());
		
		
		TextView time = (TextView) layout.findViewById(R.id.time);
		time.setText(lists.get(position).getTime());
		
		return layout;
	}

}
