package com.nd2;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import android.text.InputType;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
	ImageView letterView;
	
	    Spinner s;
		//EditText txtData1;	
		DataBaseHelper dbHelper;
		SQLiteDatabase db;
		int click, letter_view_number=1;
		int sort1=0,sort2=1,sort3=0;
		double timesum=0;
		String valToSet, query="", word="";
		ListView lvCustomList;
		Map<Character,Integer> charvalues;
		HashMap<String, String> map2; 
		ArrayList<String>	labels = new ArrayList<String>();
		ArrayList<String>	labels1 = new ArrayList<String>();
		List<HashMap<String, String>> fillMaps;
		SpecialAdapter adapter;
		Map<Character,String> chars;
		String[] from = new String[] {"rowid", "col_1","col_2","col_3"};
		int[] to = new int[] { R.id.item1, R.id.item2, R.id.item3,R.id.item4 };
		WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	 requestWindowFeature(Window.FEATURE_NO_TITLE); 
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		super.onCreate(savedInstanceState);

		  	setContentView(R.layout.activity_main);
		
		//	  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		  //	 android.app.ActionBar actionBar = this.getActionBar();
		//	 actionBar.hide();
	//	txtData1 = (EditText) findViewById(R.id.editText1);
	//	txtData1.setInputType(InputType.TYPE_NULL);
		 try {
			 dbHelper = new DataBaseHelper(this);
	//		 dbHelper.onUpgrade(db,1,2);
		 	 } 
		 catch (IOException e) 
		 	 {
			 e.printStackTrace();
		 	 }
		db = dbHelper.getWritableDatabase();
		
	}		
	
	
	public void onClick(View v) 
{
		switch (v.getId()) 
		{
			
			case R.id.ImageButton01:
		{ if (labels.size()!=0){
					sort1++;
					Collections.sort((labels),new Comparator<String>()
						{
						public int compare(String s1,String s2)
						   {
								{
								 boolean orderbyalphabet=false, poperednisymvolyok=true;
								 if (s1.length()!=s2.length()) 
								 	{if(s1.length()<s2.length())
								 		{ 
								 		  s1= (s1+s2.substring(s1.length(),s2.length()));
								 		  s1=s1.substring(0,s1.length()-1);
								 		  s1+="z";
								 		}
								 	 if(s2.length()<s1.length()) 
								 	 	{
								 		  s2= (s2+s1.substring(s2.length(),s1.length()));
								 		  s2=s2.substring(0,s2.length()-1);
								 		  s2+="z";
								 	 	}
								 	 }
								 for (int i=0;i<s1.length();i++)	
									{ 
						    		poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'>=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
						    		if(i==0) poperednisymvolyok=true;
									if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')>0&&poperednisymvolyok) 
										{orderbyalphabet=true; } 
									}
								 if(orderbyalphabet) return(1);
								 else
								 return(-1);
						    	}
						    }
						});
			   	fillMaps.clear();
			   	if(sort1%2==0)
			   	{labels1.clear();
			   		for(int i=0;i<labels.size();i++)
					{
			   			labels1.add(labels.get(i));
					}
			   		labels.clear();
			   		for(int i=labels1.size();i>0;i--)
					{if (labels1.get(i-1)!=null)
			   			labels.add(labels1.get(i-1));
					}
			   	}
			   	map2= new HashMap<String, String>();
			   	map2.put("rowid", "#"  );
		        map2.put("col_1","Word");
		        map2.put("col_2","Value");
		        map2.put("col_3","Length");
		        fillMaps.add(map2);
		        
				for(int i=0;i<labels.size();i++)
				{	int chvalue=0;
					map2= new HashMap<String, String>();
					map2.put("rowid", ""+(i+1)  );
					map2.put("col_1",""+ labels.get(i));
					for (int why=0;why<labels.get(i).length();why++)
					{
			        chvalue+=charvalues.get(labels.get(i).charAt(why));
					}
					map2.put("col_2",""+ chvalue);
			        map2.put("col_3",""+ labels.get(i).length());
					fillMaps.add(map2);
				}
				adapter = new SpecialAdapter(this,fillMaps,R.layout.grid_item,from,to);
				lvCustomList.setAdapter(adapter);				
				break;
		}}
			case R.id.ImageButton02:
				if (labels.size()!=0)
				sortbyvalue();
				break;
			
			case R.id.imageButton1:
				if(labels.size()!=0)
				sortbylen();
				break;
			case R.id.letter_q:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.q);
				letter_view_number++;
				word+="й";
				runquery();
				break;
			case R.id.letter_w:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.w);
				letter_view_number++;
				word+="ц";
				runquery();
				break;
			case R.id.letter_e:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.e);
				letter_view_number++;
				word+="у";
				runquery();
				break;
			case R.id.letter_r:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.r);
				letter_view_number++;
				word+="к";
				runquery();
				break;
			case R.id.letter_t:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.t);
				letter_view_number++;
				word+="е";
				runquery();
				break;
			case R.id.letter_y:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.y);
				letter_view_number++;
				word+="н";
				runquery();
				break;
			case R.id.letter_u:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.u);
				letter_view_number++;
				word+="г";
				runquery();
				break;
			case R.id.letter_i:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.i);
				letter_view_number++;		
				word+="ш";
				runquery();
				break;
			case R.id.letter_o:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.o);
				letter_view_number++;		
				word+="щ";
				runquery();
				break;
			case R.id.letter_p:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.p);
				letter_view_number++;		
				word+="з";
				runquery();
				break;
			case R.id.letter_p0:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.a);
				letter_view_number++;		
				word+="ф";
				runquery();
				break;
			case R.id.letter_p3:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.p3);
				letter_view_number++;		
				word+="ї";
				runquery();
				break;
			case R.id.letter_a:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.s);
				letter_view_number++;		
				word+="і";
				runquery();
				break;
			case R.id.letter_s:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.d);
				letter_view_number++;		
				word+="в";
				runquery();
				break;			case R.id.letter_d:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.f);
				letter_view_number++;		
				word+="а";
				runquery();
				break;
			case R.id.letter_f:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.g);
				letter_view_number++;		
				word+="п";
				runquery();
				break;
			case R.id.letter_h:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.h);
				letter_view_number++;		
				word+="р";
				runquery();
				break;
			case R.id.letter_j:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.j);
				letter_view_number++;		
				word+="о";
				runquery();
				break;
			case R.id.letter_k:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.k);
				letter_view_number++;		
				word+="л";
				runquery();
				break;
			case R.id.letter_l:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.l);
				letter_view_number++;		
				word+="д";
				runquery();
				break;
			case R.id.letter_l0:
				if(letter_view_number>12) letter_view_number=12;
			if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.l1);
				letter_view_number++;	
				word+="ж";
				runquery();
				break;
			case R.id.letter_l1:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.l2);
				letter_view_number++;	
				word+="є";
				runquery();
				break;
			case R.id.letter_z:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.z);
				letter_view_number++;	
				word+="я";
				runquery();
				break;
			case R.id.letter_x:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.x);
				letter_view_number++;	
				word+="ч";
				runquery();
				break;
			case R.id.letter_c:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.c);
				letter_view_number++;		
				word+="с";
				runquery();
				break;
			case R.id.letter_v:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.v);
				letter_view_number++;			
				word+="м";
				runquery();
				break;
			case R.id.letter_b:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.b);
				letter_view_number++;		
				word+="и";
				runquery();
				break;
			case R.id.letter_n:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.n);
				letter_view_number++;		
				word+="т";
				runquery();
				break;
			case R.id.letter_m:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.m);
				letter_view_number++;		
				word+="ь";
				runquery();
				break;
			case R.id.letter_m0:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.m1);
				letter_view_number++;		
				word+="б";
				runquery();
				break;
			case R.id.letter_m1:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.m2);
				letter_view_number++;		
				word+="ю";
				runquery();
				break;
			case R.id.letter_m2:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.m3);
				letter_view_number++;	
				word+="ґ";
				runquery();
				break;
			case R.id.letter_m3:
				if(letter_view_number>12) letter_view_number=12;
				if(word.length()>11) word=word.substring(0,11);
				getnumberofletter(letter_view_number).setImageResource(R.drawable.m4);
				letter_view_number++;	
				word+="'";
				runquery();
				break;
			case R.id.letter_m4:
				if(letter_view_number>12) letter_view_number=13;
				getnumberofletter(letter_view_number-1).setVisibility(View.INVISIBLE);
				if(word.length()>11) word=word.substring(0,11);
				else if(word.length()==0) ;
				else word=word.substring(0,word.length()-1);
				if(letter_view_number!=1)
				letter_view_number--;
				runquery();
				break;
		}
		
		
}
	public ImageView getnumberofletter(int letternumber)	
	{	ImageView letter1= (ImageView) findViewById(R.id.letter12);
	switch(letternumber)
	{
	case 1:		{ letter1 = (ImageView) findViewById(R.id.letter1);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 2:		{ letter1 = (ImageView) findViewById(R.id.letter2);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 3:		{ letter1 = (ImageView) findViewById(R.id.letter3);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 4:		{ letter1 = (ImageView) findViewById(R.id.letter4);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 5:		{ letter1 = (ImageView) findViewById(R.id.letter5);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 6:		{ letter1 = (ImageView) findViewById(R.id.letter6);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 7:		{ letter1 = (ImageView) findViewById(R.id.letter7);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 8:		{ letter1 = (ImageView) findViewById(R.id.letter8);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 9:		{ letter1 = (ImageView) findViewById(R.id.letter9);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 10:	{ letter1 = (ImageView) findViewById(R.id.letter10);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 11:	{ letter1 = (ImageView) findViewById(R.id.letter11);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	case 12:	{ letter1 = (ImageView) findViewById(R.id.letter12);
	letter1.setVisibility(View.VISIBLE);
				return letter1;
				}
	
	}
	letter1.setVisibility(View.VISIBLE);
	return letter1;
	}
	public void runquery()
{ 
	labels.clear();
	sort1=0;sort2=1;sort3=0;
	click++;
//	s = (Spinner) findViewById(R.id.spinner1);
	lvCustomList = (ListView) findViewById(R.id.listView1);
	fillMaps = new ArrayList<HashMap<String, String>>();
//    ArrayAdapter<String> dataAdapter;
//	dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
	int i;
	String q="" ,qq="",name,timestart,timeend, charthatwedontneed="",word1="";
	
//		String word=txtData1.getText().toString();
	//word="протимс";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date(); //get current date time with Date()
	timestart=(dateFormat.format(date));
	chars = new HashMap<Character, String>();
	charvalues = new HashMap<Character, Integer>();
	if(q.length()>4) q=q.substring(0,q.length()-3);
	if(qq.length()>9) qq=qq.substring(0,qq.length()-14);
	Cursor cursor1 = db.rawQuery("SELECT Code, Name, Value FROM Main",null);
	while (cursor1.moveToNext()) 
		{
		if (word.indexOf(cursor1.getString(cursor1.getColumnIndex("Name")))!=-1) 
		{
		chars.put(cursor1.getString(cursor1.getColumnIndex("Name")).charAt(0),cursor1.getString(cursor1.getColumnIndex("Code")));
		charvalues.put(cursor1.getString(cursor1.getColumnIndex("Name")).charAt(0),cursor1.getInt(cursor1.getColumnIndex("Value")));
		}
		else
			if(!cursor1.getString(cursor1.getColumnIndex("Name")).equals("'"))
			charthatwedontneed+="\'%"+cursor1.getString(cursor1.getColumnIndex("Name"))+"%\') and word not like (";
		}
	cursor1.close();
	Iterator<Entry<Character, String>> it2,it;
	Map.Entry<Character,String> pairs,pairs2;
	it = chars.entrySet().iterator();
	Cursor cursor3,cursor2;
	Boolean t,wrongword;
//		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels1);
//	Spinner s2 = (Spinner) findViewById(R.id.spinner2);
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
				query = "SELECT word FROM tbl"+(String)pairs.getValue()+"x"+(String)pairs2.getValue()
					+" where word not like ("+charthatwedontneed.substring(0,(charthatwedontneed.length())-22)+"\')";//+" where word like("+qq;	
			cursor2 = db.rawQuery(query, null);
	
			while (cursor2.moveToNext()) 
				{
				name = cursor2.getString(cursor2.getColumnIndex("word"));
				name=(name.indexOf("&apos;")>0 ? name.substring(0,name.indexOf("&apos;"))+"'"+name.substring(name.indexOf(";")+1) :name);
				//тут прибираються слова з повторами букв (напр. титри з "три") 
				wrongword=true;
				word1=word;
				for ( i=0; i<(name.length());i++)
				{
					int position=word1.indexOf(name.charAt(i));
					if (position!=-1) 
					{
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
				}
			cursor2.close();  			
			}
			}
		}
	sortbyvalue();
//	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	dataAdapter.notifyDataSetChanged();
//	s.setAdapter(dataAdapter);
//	dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	dataAdapter2.notifyDataSetChanged();
//	s2.setAdapter(dataAdapter2);
	Date date2=new Date();
	timeend=(dateFormat.format(date2));
	timesum+=(date.getTime()-date2.getTime())/1000.;
	TextView text = (TextView) findViewById(R.id.textView2);
	text.setText(timestart+"\n"+timeend+"\n"+(date.getTime()-date2.getTime())/1000.+"  avg "+timesum/click);
	lvCustomList.setOnItemClickListener(new OnItemClickListener() 
	{
		
		  public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
		  {			  
		
		    TextView item2 = (TextView)view.findViewById(R.id.item2);
		    item2.setTextColor(android.graphics.Color.YELLOW);
			String word_with_apostrof=item2.getText().toString();
			word_with_apostrof=(word_with_apostrof.indexOf("'")>0 ? word_with_apostrof.substring(0,word_with_apostrof.indexOf("'"))+"&apos;"+word_with_apostrof.substring(word_with_apostrof.indexOf("'")+1) :word_with_apostrof);
		if(!word_with_apostrof.equals("Word")){
		    query = "SELECT full FROM tbl"+chars.get(item2.getText().toString().charAt(0))+"x"+chars.get(item2.getText().toString().charAt(1))+" where word like '"+word_with_apostrof+"'";
		   Cursor cursor2 = db.rawQuery(query, null);
		
	    while (cursor2.moveToNext()) 
				{

						Toast.makeText(getApplicationContext(), cursor2.getString(cursor2.getColumnIndex("full")),Toast.LENGTH_LONG).show();
				}
		  }}
	});	
	
	}
	public void sortbyvalue()
	{

		sort2++;
		if(sort2%2!=0){
		Collections.sort((labels),new Comparator<String>()
				{
				  public int compare(String s1,String s2)
				   {boolean orderbyalphabet=false, poperednisymvolyok=true;
			int chvalue1=0,chvalue2=0;
				    for (int why=0;why<s1.length();why++){
			            chvalue1+=charvalues.get(s1.charAt(why));
					  }
				    for (int why=0;why<s2.length();why++){
			            chvalue2+=charvalues.get(s2.charAt(why));
					  }
				if (s1.length()!=s2.length()) 
					{if(s1.length()<s2.length())
				{ s1= (s1+s2.substring(s1.length(),s2.length()));
				s1=s1.substring(0,s1.length()-1);
				s1+="z";
				}
				
				if(s2.length()<s1.length()) {
				s2= (s2+s1.substring(s2.length(),s1.length()));
				s2=s2.substring(0,s2.length()-1);
				s2+="z";
				}}
				   for (int i=0;i<s1.length();i++)	
					  	{
					    poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'>=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
						if(i==0) poperednisymvolyok=true;
						if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')>0&&poperednisymvolyok) 
							{orderbyalphabet=true; }
					  	}
				    	if(chvalue1>chvalue2||(chvalue1==chvalue2&&orderbyalphabet)) return(1);
				    	else
				    	return(-1);
				   }
				});
		}
		else 
		{
			Collections.sort((labels),new Comparator<String>()
					{
					  public int compare(String s1,String s2)
					   {boolean orderbyalphabet=false, poperednisymvolyok=true;
				int chvalue1=0,chvalue2=0;
					    for (int why=0;why<s1.length();why++){
				            chvalue1+=charvalues.get(s1.charAt(why));
						  }
					    for (int why=0;why<s2.length();why++){
				            chvalue2+=charvalues.get(s2.charAt(why));
						  }
					if (s1.length()!=s2.length()) 
						{if(s1.length()<s2.length())
					{ s1= (s1+s2.substring(s1.length(),s2.length()));
					s1=s1.substring(0,s1.length()-1);
					s1+="z";
					}
					
					if(s2.length()<s1.length()) {
					s2= (s2+s1.substring(s2.length(),s1.length()));
					s2=s2.substring(0,s2.length()-1);
					s2+="z";
					}}
					 for (int i=0;i<s1.length();i++)	
					   	{ 
						 poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'<=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
					   	 if(i==0) poperednisymvolyok=true;
				   		 if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')<0&&poperednisymvolyok) 
				   		 {orderbyalphabet=true; } 
						}
					 if(chvalue1>chvalue2||(chvalue1==chvalue2&&orderbyalphabet)) return(1);
		  			 else
					 return(-1);}
					});
			}
	   	fillMaps.clear();
	   	if(sort2%2==0)
	   	{labels1.clear();
	   		for(int i=0;i<labels.size();i++)
			{
	   			labels1.add(labels.get(i));
			}
	   		labels.clear();
	   		for(int i=labels1.size();i>0;i--)
			{if (labels1.get(i-1)!=null)
	   			labels.add(labels1.get(i-1));
			}
	   	}
	   	map2= new HashMap<String, String>();
	   	map2.put("rowid", "#"  );
          map2.put("col_1","Word");
          map2.put("col_2","Value");
          map2.put("col_3","Length");
          fillMaps.add(map2);
	   	
		for(int i=0;i<labels.size();i++)
		{int chvalue=0;
		map2= new HashMap<String, String>();
			  map2.put("rowid", ""+(i+1)  );
			  map2.put("col_1",""+ labels.get(i));
			  for (int why=0;why<labels.get(i).length();why++){
	            chvalue+=charvalues.get(labels.get(i).charAt(why));
			  }
			  	map2.put("col_2",""+ chvalue);
	            map2.put("col_3",""+ labels.get(i).length());
				  fillMaps.add(map2);
		}

 adapter = new SpecialAdapter(this,fillMaps,R.layout.grid_item,from,to);
		lvCustomList.setAdapter(adapter);		
	}
	public void sortbylen()
	{

		sort3++;
		if(sort3%2!=0){
		Collections.sort((labels),new Comparator<String>()
				{
				  public int compare(String s1,String s2)
				   {
					  if((s1.length() - s2.length()<0)) {return(-1); }
				     if((s1.length() - s2.length()>0)) return(1);  
				    		 else {boolean orderbyalphabet=false, poperednisymvolyok=true;
				    		 
				    			 for (int i=0;i<s1.length();i++)	
								   	{ poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'>=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
								   	if(i==0) poperednisymvolyok=true;
								   		if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')>0&&poperednisymvolyok) {orderbyalphabet=true; } //else  orderbyalphabet=false;
								   	}
				    			 if((s1.length() == s2.length())&&orderbyalphabet) return(1);
				    				else return(-1);}
				    }
				});
		}
		else 
		{Collections.sort((labels),new Comparator<String>()
				{
			  public int compare(String s1,String s2)
			   {
			   	 if((s1.length() - s2.length()<0)) {return(-1); }
			     if((s1.length() - s2.length()>0)) return(1);  
			    		 else {boolean orderbyalphabet=false, poperednisymvolyok=true;
			    		 
			    			 for (int i=0;i<s1.length();i++)	
							   	{ poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'<=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
							   	if(i==0) poperednisymvolyok=true;
							   		if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')<0&&poperednisymvolyok) {orderbyalphabet=true; } //else  orderbyalphabet=false;
							   	}
			    			 //if((s1.charAt(0)-'0')-(s2.charAt(0)-'0')>0) orderbyalphabet=true;
			    			 if((s1.length() == s2.length())&&orderbyalphabet) return(1);
			    				else return(-1);}
			    }
			});
		{labels1.clear();
   		for(int i=0;i<labels.size();i++)
		{
   			labels1.add(labels.get(i));
		}
   		labels.clear();
   		for(int i=labels1.size();i>0;i--)
		{if (labels1.get(i-1)!=null)
   			labels.add(labels1.get(i-1));
		}
   	}
		
		}
	   	fillMaps.clear();
	   	
	   	  map2= new HashMap<String, String>();
		  map2.put("rowid", "#"  );
          map2.put("col_1","Word");
          map2.put("col_2","Value");
          map2.put("col_3","Length");
          fillMaps.add(map2);
	   	
		for(int i=0;i<labels.size();i++)
		{ int chvalue=0;
		map2= new HashMap<String, String>();
			  map2.put("rowid", ""+(i+1)  );
	            map2.put("col_1",""+ labels.get(i));
	            for (int why=0;why<labels.get(i).length();why++){
		            chvalue+=charvalues.get(labels.get(i).charAt(why));
				  }
				  	map2.put("col_2",""+ chvalue);
	            map2.put("col_3",""+ labels.get(i).length());
				  fillMaps.add(map2);
		}

 adapter = new SpecialAdapter(this,fillMaps,R.layout.grid_item,from,to);
		lvCustomList.setAdapter(adapter);	
		int[] colors = {0, 0xFF5B9BD5, 0}; 
		lvCustomList.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		lvCustomList.setDividerHeight(1);
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
			   Toast.makeText(getApplicationContext(), "ну",//((TextView) view).getText(),
					    Toast.LENGTH_SHORT).show();
		
			Cursor   cursor3=db.rawQuery("Select name,value from main",null);
			while (cursor3.moveToNext()) 
			{
			String name = cursor3.getString(cursor3.getColumnIndex("Name"));
			name+=name;		}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}

//String insertQuery = "INSERT INTO " + SQ.TABLE_NAME+ " (" + SQ.CATNAME + ") VALUES ('"	+ txtData1.getText().toString() + "')";
//sqdb.execSQL(insertQuery);