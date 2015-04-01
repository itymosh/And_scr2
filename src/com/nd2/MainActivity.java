package com.nd2;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	    Spinner s;
		EditText txtData1;	
//	    SQ sqh;
//		SQLiteDatabase sqdb;
		DataBaseHelper dbHelper;
		SQLiteDatabase db;
		int click;
		double timesum=0;
		String valToSet;
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
			
				s = (Spinner) findViewById(R.id.spinner1);
				
				
			// Метод 2: INSERT через SQL-запрос
			//String insertQuery = "INSERT INTO " + SQ.TABLE_NAME+ " (" + SQ.CATNAME + ") VALUES ('"	+ txtData1.getText().toString() + "')";
			//sqdb.execSQL(insertQuery);nb
			//txtData1.setText("");	
			int i;
			String q="" ,qq="",query="",name,timestart,timeend, charthatwedontneed="",word1="";
			
			//String word=txtData1.getText().toString();
			String word="тимни";//"протимс";
		    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			   //get current date time with Date()
			   Date date = new Date();
			   timestart=(dateFormat.format(date));
			Map<Character,String> chars = new HashMap<Character, String>();
			HashSet<String> byorder = new HashSet<String>();
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
			ArrayList<String>	labels1 = new ArrayList<String>();
			ArrayAdapter<String> dataAdapter;
			dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, labels);
			ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, labels1);
			Spinner s2 = (Spinner) findViewById(R.id.spinner2);
		
	//		s.setOnItemSelectedListener(this);
			
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
						if(wrongword) 
						{
							labels.add(name);
							labels1.add(name);
						}	
							///labels.set(1,name);byorder.add(name);
						}
					cursor2.close();  			
					}
					}
				}
		//	labels.add("4434");	labels.add("121");labels.add("22");labels.add("9");labels.add("4446");  labels.add("5448"); labels.add("2447");
			Collections.sort((labels),new Comparator<String>()
					{
					  public int compare(String s1,String s2)
					   {
					   	
						  //int x1 =(s1.charAt(0)- '0'); 
					//		   int x2=( s2.charAt(0)- '0');
						  if((s1.length() - s2.length()<0)) {return(-1); }
					     if((s1.length() - s2.length()>0)) return(1);  
					    		 else {boolean orderbyalphabet=false, poperednisymvolyok=true;
					    		 
					    			 for (int i=0;i<s1.length();i++)	
									   	{ poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'>=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
									   	if(i==0) poperednisymvolyok=true;
									   		if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')>0&&poperednisymvolyok) {orderbyalphabet=true; } //else  orderbyalphabet=false;
									   	}
					    			 //if((s1.charAt(0)-'0')-(s2.charAt(0)-'0')>0) orderbyalphabet=true;
					    			 if((s1.length() == s2.length())&&orderbyalphabet) return(1);
					    				else return(-1);}
					    }
					});
			//Collections.sort(labels);
			//labels1.addAll(((labels)));
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dataAdapter.notifyDataSetChanged();
			s.setAdapter(dataAdapter);
			dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dataAdapter2.notifyDataSetChanged();
			s2.setAdapter(dataAdapter2);
			Date date2=new Date();
			timeend=(dateFormat.format(date2));
		//	DecimalFormat df = new DecimalFormat("####0.#");
		//	double difference;
			timesum+=(date.getTime()-date2.getTime())/1000.;
		//	df.format((date.getTime()  - date2.getTime())/1000);
			TextView text = (TextView) findViewById(R.id.textView2);
			text.setText(timestart+"\n"+timeend+"\n"+(date.getTime()-date2.getTime())/1000.+"  avg "+timesum/click);
	//	default: Log.i("1w","ss"); 
			s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			        Object item = parent.getItemAtPosition(pos);
			        valToSet = s.getSelectedItem().toString();
					
			    }
			    public void onNothingSelected(AdapterView<?> parent) {
			    }
			});
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
