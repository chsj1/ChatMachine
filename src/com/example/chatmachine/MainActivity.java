package com.example.chatmachine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements HttpGetDataListener,OnClickListener{

	private HttpData httpData;
	private List<ListData> lists;
	private ListView lv;
	private EditText sendText;
	private Button send_btn;
	private String content_str;
	private TextAdapter adapter;
	
	private String[] welcome_array;
	
	private double currentTime,oldTime = 0;
	
	public String getTime(){
		currentTime = System.currentTimeMillis();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月DD日 hh:mm:ss");
		Date curDate = new Date();
		
		String str = format.format(curDate);
		
		if(currentTime - oldTime >= 5*60*1000){
			oldTime = currentTime;
			return str;
		}else{
			return "";
		}
		
	}
	
	@Override
	public void onClick(View v) {
		
		content_str = sendText.getText().toString();
		sendText.setText("");
		
		String dropk = content_str.replace(" ", "");
		String droph = dropk.replace("\n", "");
		
		
		ListData listData = new ListData(content_str, ListData.SEND,getTime());
		lists.add(listData);
		if(lists.size() > 30){//清除多余数据
			for(int i = 0 ; i < lists.size() ; ++i){
				lists.remove(i);
			}
		}
		
		adapter.notifyDataSetChanged();
		
		httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=78cdc548fdcb2855553bc9bef9e7e0fa&info=" + droph, this).execute();
		
	}
	
	private String getRandomWelcomTips(){
		String welcome_tip = null;
		welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
		int index = (int)(Math.random()*(welcome_array.length - 1));
		welcome_tip = welcome_array[index];
		
		
		return welcome_tip;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		initiView();
		
	}
	
	public void initiView(){
		lv = (ListView) findViewById(R.id.lv);
		sendText = (EditText) findViewById(R.id.sendText);
		send_btn = (Button) findViewById(R.id.send_btn);
		
		lists = new ArrayList<ListData>();
		send_btn.setOnClickListener(this);
		adapter = new TextAdapter(lists, this);
		lv.setAdapter(adapter);
		
		
		ListData listData;
		listData = new ListData(getRandomWelcomTips(), ListData.RECEIVER,getTime());
		lists.add(listData);
	}
	
	@Override
	public void getDataUrl(String data) {
//		System.out.println(data);
		parseText(data);
	}
	
	public void parseText(String str){
		try{
			JSONObject jb = new JSONObject(str);
//			System.out.println(jb.getString("code"));
//			System.out.println(jb.getString("text"));
			
			ListData listData;
			listData = new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();//重新适配.
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
