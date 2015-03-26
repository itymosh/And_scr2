package com.nd2;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;






//import com.nd2.SQ;
import com.nd2.R;	

import android.support.v7.app.ActionBarActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

		EditText txtData1;	
//	    SQ sqh;
//		SQLiteDatabase sqdb;
		DataBaseHelper dbHelper;
		SQLiteDatabase db;
		int click;
		double timesum=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		sqh = new SQ(this);
//		sqdb = sqh.getWritableDatabase();
		//		sqh.onUpgrade(sqdb, 1, 2);
		txtData1 = (EditText) findViewById(R.id.editText1);
		 try {
			 dbHelper = new DataBaseHelper(this);
		 	 } 
		 catch (IOException e) 
		 	 {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 	 }
		db = dbHelper.getWritableDatabase();
	}		
	

	
public void onClick(View v) 
{
		switch (v.getId()) 
		{
			case R.id.seven_letters:
				click++;
			// Метод 2: INSERT через SQL-запрос
			//String insertQuery = "INSERT INTO " + SQ.TABLE_NAME+ " (" + SQ.CATNAME + ") VALUES ('"	+ txtData1.getText().toString() + "')";
			//sqdb.execSQL(insertQuery);nb
			//txtData1.setText("");	
			int i;
			String q="" ,qq="",query="",name,timestart,timeend, charthatwedontneed="",word1="";
			
			//String word=txtData1.getText().toString();
			String word="протимс";
		    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			   //get current date time with Date()
			   Date date = new Date();
			   timestart=(dateFormat.format(date));
			Map<Character,String> chars = new HashMap<Character, String>();
			///	char[] charArray = new char[] ;//{ 'a', 'b', 'c', 'd', 'e' }; 
			/*for ( i=0; i<(word.length());i++)
				{
				//	chars.put(word.charAt(i),"");		
				//q+=chars.getKey()+"\',\'";
				q+=word.charAt(i)+"\',\'";
				qq+="\'%"+word.charAt(i)+"%\') or word like (";
				}*/
			if(q.length()>4) q=q.substring(0,q.length()-3);
			if(qq.length()>9) qq=qq.substring(0,qq.length()-14);
			Cursor cursor1 = db.rawQuery("SELECT Code, Name FROM Main",null);// where name in(\'"+q+"\')", null);
			while (cursor1.moveToNext()) 
				{
				if (word.indexOf(cursor1.getString(cursor1.getColumnIndex("Name")))!=-1) 
				chars.put(cursor1.getString(cursor1.getColumnIndex("Name")).charAt(0),cursor1.getString(cursor1.getColumnIndex("Code")));
				else charthatwedontneed+="\'%"+cursor1.getString(cursor1.getColumnIndex("Name"))+"%\') and word not like (";
				}
			cursor1.close();
			Iterator<Entry<Character, String>> it2,it;
			Map.Entry<Character,String> pairs,pairs2;
			it = chars.entrySet().iterator();
			Cursor cursor3,cursor2;
			Boolean t,wrongword;
			ArrayList<String>	labels = new ArrayList<String>();
			ArrayAdapter<String> dataAdapter;
			Spinner s;
			while (it.hasNext()) 
				{ pairs = (Map.Entry<Character,String>)it.next();
				 it2 = chars.entrySet().iterator();
				while (it2.hasNext()) 
					{  pairs2 = (Map.Entry<Character,String>)it2.next();
					query="SELECT CASE WHEN tbl_name = 'tbl"+(String)pairs.getValue()+"x"+(String)pairs2.getValue()+"' THEN 1 ELSE 0 END as nnd FROM sqlite_master WHERE tbl_name ='tbl"+(String)pairs.getValue()+"x"+(String)pairs2.getValue()+"'  AND type = 'table'";
					cursor3=db.rawQuery(query,null);
					t=false;
					while (cursor3.moveToNext()) 
					{	
						t=(cursor3.getInt(cursor3.getColumnIndex("nnd"))==1 ? true : false);
					}
					if(t)
					{
						query = "SELECT word, full FROM tbl"+(String)pairs.getValue()+"x"+(String)pairs2.getValue()
							+" where word not like ("+charthatwedontneed.substring(0,(charthatwedontneed.length())-22)+"\')";//+" where word like("+qq;
						
					cursor2 = db.rawQuery(query, null);
					
					dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, labels);
					s = (Spinner) findViewById(R.id.spinner1);
					while (cursor2.moveToNext()) 
						{
						name = cursor2.getString(cursor2.getColumnIndex("word"));
						wrongword=true;
						word1=word;
						for ( i=0; i<(name.length());i++)
						{
							//labels.add((name.indexOf(word.charAt(i))==-1) ? (name) : labels.add(name));
							int position=word1.indexOf(name.charAt(i));
							
							if (position!=-1) 
							{
								//name=name.substring(i+1);
								word1=word1.substring(0,position)+word1.substring(position+1,word1.length());
							}
							else {wrongword=false;
							break;
							}	
							;
							}
						if(wrongword) labels.add(name);
						dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						dataAdapter.notifyDataSetChanged();
						s.setAdapter(dataAdapter);
						}
					cursor2.close();  			
					}
					}
				}
			Date date2=new Date();
			timeend=(dateFormat.format(date2));
		//	DecimalFormat df = new DecimalFormat("####0.#");
		//	double difference;
			timesum+=(date.getTime()-date2.getTime())/1000.;
		//	df.format((date.getTime()  - date2.getTime())/1000);
			TextView text = (TextView) findViewById(R.id.textView2);
			text.setText(timestart+"\n"+timeend+"\n"+(date.getTime()-date2.getTime())/1000.+"  avg "+timesum/click);
	//	default: Log.i("1w","ss"); 
		}
		
}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
