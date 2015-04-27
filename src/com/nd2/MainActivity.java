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

import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends ActionBarActivity {
	    Spinner s;
		EditText txtData1;	
		DataBaseHelper dbHelper;
		SQLiteDatabase db;
		int click;
		int sort1=0,sort2=0,sort3=0;
		double timesum=0;
		String valToSet, query="";
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
	{//set up notitle 
        
        //set up full screen
         
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		super.onCreate(savedInstanceState);
		//android.app.ActionBar actionBar = getActionBar();
		//  actionBar.hide();
		setContentView(R.layout.activity_main);
		txtData1 = (EditText) findViewById(R.id.editText1);
		txtData1.setInputType(InputType.TYPE_NULL);
		
		/*final OnTouchListener otl = new OnTouchListener() {
			public boolean onTouch (View v, MotionEvent event) {
			        return true; // the listener has consumed the event
			}
			};
		
			txtData1.setOnTouchListener(otl);
		*/
		
		
	/*		txtData1.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
				int inType = txtData1.getInputType(); // backup the input type
				txtData1.setInputType(InputType.TYPE_NULL); // disable soft input
				txtData1.onTouchEvent(event); // call native handler
				txtData1.setInputType(inType); // restore input type
				return true; // consume touch even
				}
				});
		*/
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
			case R.id.seven_letters:
			{ 
				labels.clear();
				sort1=0;sort2=0;sort3=0;
			click++;
			s = (Spinner) findViewById(R.id.spinner1);
			//ListView mylist =  (ListView) this.findViewById(R.id.listView1);
			lvCustomList = (ListView) findViewById(R.id.listView1);
	fillMaps = new ArrayList<HashMap<String, String>>();
		//	ContactListAdapter contactListAdapter = new ContactListAdapter(
		//		    MainActivity.this, cocktailListView);
			   ArrayAdapter<String> dataAdapter;
				dataAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, labels);
			
			
			int i;
			String q="" ,qq="",name,timestart,timeend, charthatwedontneed="",word1="";
			
	//		String word=txtData1.getText().toString();
			String word="протимс";
		    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date(); //get current date time with Date()
			timestart=(dateFormat.format(date));
		 chars = new HashMap<Character, String>();
			charvalues = new HashMap<Character, Integer>();
			//HashSet<String> byorder = new HashSet<String>();
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
			Cursor cursor1 = db.rawQuery("SELECT Code, Name, Value FROM Main",null);// where name in(\'"+q+"\')", null);
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
			
			 
		//			  lvCustomList.setAdapter(contactListAdapter);
					 
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
			sortbylen();
		//	labels.add("4434");	labels.add("121");labels.add("22");labels.add("9");labels.add("4446");  labels.add("5448"); labels.add("2447");
	/*		Collections.sort((labels),new Comparator<String>()
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
			
///=======================		in map	
			
		   	fillMaps.clear();
			for(i=0;i<labels.size();i++)
			{
				//rowid++;
				map2= new HashMap<String, String>();
				  map2.put("rowid", ""+i  );
		            map2.put("col_1",""+ labels.get(i));
		            map2.put("col_3",""+ labels.get(i).length());
					  fillMaps.add(map2);
			}
			 adapter = new SpecialAdapter(this,fillMaps,R.layout.grid_item,from,to);
				lvCustomList.setAdapter(adapter);
			
			*/
///==================			
			//Collections.sort(labels);
			//labels1.addAll(((labels)));
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dataAdapter.notifyDataSetChanged();
			s.setAdapter(dataAdapter);
		//	mylist.setAdapter(dataAdapter);


		  
         //   map.put("col_2", "col_2_item_" );
         //   map.put("col_3", "col_3_item_");
		    
	//	    SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_item, from, to);
			
		//    ArrayAdapter<String> adapter = new ArrayAdapter<String>
		//	(this, android.R.layout.simple_list_item_1, labels);
		//	lvCustomList.setAdapter(adapter);
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
			
			lvCustomList.setOnItemClickListener(new OnItemClickListener() {
				
				  public void onItemClick(AdapterView<?> adapter, View view, int position, long id) 
				  {
					  
					/*  то є зміна коліру рядку в listview
					 *private View mviewPre;
				private int posSel = -1;
				private int positionPre = -1;
			    private int FirstItemPos; 
					 * posSel = position;              
		                FirstItemPos = adapter.getFirstVisiblePosition();
		                mviewPre = adapter.getChildAt(positionPre - FirstItemPos);
		              
		                if (mviewPre != null) {
		                    mviewPre
		                            .setBackgroundResource((positionPre & 1) == 1 ? R.color.material_blue_grey_900   : R.color.material_deep_teal_200);
		                }
		         
		                if (posSel != positionPre) {
		                	adapter.getChildAt(posSel - FirstItemPos)
		                            .setBackgroundResource(
		                                    (posSel & 1) == 1 ? R.color.material_blue_grey_900   : R.color.material_deep_teal_200);
		                }
		 
		                positionPre = position;
		                 adapter.setBackgroundResource(R.color.material_deep_teal_500);
		        */
		               
		                 
		            //    setTitle( position );
				    TextView item2 = (TextView)view.findViewById(R.id.item2);
				    item2.setTextColor(android.graphics.Color.YELLOW);
					String word_with_apostrof=item2.getText().toString();
					word_with_apostrof=(word_with_apostrof.indexOf("'")>0 ? word_with_apostrof.substring(0,word_with_apostrof.indexOf("'"))+"&apos;"+word_with_apostrof.substring(word_with_apostrof.indexOf("'")+1) :word_with_apostrof);
					
				    query = "SELECT full FROM tbl"+chars.get(item2.getText().toString().charAt(0))+"x"+chars.get(item2.getText().toString().charAt(1))+" where word like '"+word_with_apostrof+"'";
				   Cursor cursor2 = db.rawQuery(query, null);
					
					
			    
			
			    while (cursor2.moveToNext()) 
						{

								Toast.makeText(getApplicationContext(), cursor2.getString(cursor2.getColumnIndex("full")),Toast.LENGTH_LONG).show();
						}
				    
				    
				  }
				});	
	//	OnItemClickListener itemClickedListener = new OnItemClickListener() {
			
		//		  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			//	    TextView item2 = (TextView)view.findViewById(R.id.item2);
			//	    item2.setTextColor(android.graphics.Color.YELLOW);
		////		    }
		//		};
	//		lvCustomList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	//		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//	//		        Object item = parent.getItemAtPosition(pos);
	//		        valToSet = lvCustomList.getSelectedItem().toString();
					
			//    }
		//	    public void onNothingSelected(AdapterView<?> parent) {
			//    }
			//});
			break;}
			case R.id.imageButton1:{
				sort1++;
				Collections.sort((labels),new Comparator<String>()
						{
						  public int compare(String s1,String s2)
						   {
						   	
						{boolean orderbyalphabet=false, poperednisymvolyok=true;
						    		// int length_of_shorter_word= (s1.length()<s2.length() ? s1.length() : s2.length());
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
										   	{ poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'>=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
										   	if(i==0) poperednisymvolyok=true;
										   		if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')>0&&poperednisymvolyok) {orderbyalphabet=true; } //else  orderbyalphabet=false;
										   	}
						    			 if(orderbyalphabet) return(1);
						    				else
						    				return(-1);}
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
				{int chvalue=0;
					//rowid++;
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
				break;}
			case R.id.ImageButton01:

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
										   	{ poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'>=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
										   	if(i==0) poperednisymvolyok=true;
										   		if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')>0&&poperednisymvolyok) {orderbyalphabet=true; } //else  orderbyalphabet=false;
										   	}
						    			 if(chvalue1>chvalue2||(chvalue1==chvalue2&&orderbyalphabet)) return(1);
						    				else
						    				return(-1);}
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
											   	{ poperednisymvolyok=(i>0&&(s1.charAt(i-1)-'0'<=s2.charAt(i-1)-'0')&&poperednisymvolyok ? true : false);
											   	if(i==0) poperednisymvolyok=true;
											   		if((s1.charAt(i)-'0')-(s2.charAt(i)-'0')<0&&poperednisymvolyok) {orderbyalphabet=true; } //else  orderbyalphabet=false;
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
				break;
			
			case R.id.ImageButton02:
				sortbylen();
				break;
			case R.id.letter_q:
				 Toast.makeText(getApplicationContext(), "ну",Toast.LENGTH_SHORT).show();
				break;
		}
		
		
}
	
	public void sortbylen()
	{

		sort3++;
		if(sort3%2!=0){
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
		}
		else 
		{Collections.sort((labels),new Comparator<String>()
				{
			  public int compare(String s1,String s2)
			   {
			   	
				  //int x1 =(s1.charAt(0)- '0'); 
			//		   int x2=( s2.charAt(0)- '0');
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
			//rowid++;
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
		int[] colors = {0, 0xFF5B9BD5, 0}; // red for the example
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