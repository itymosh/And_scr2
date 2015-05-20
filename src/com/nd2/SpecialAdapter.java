package com.nd2;

import java.util.HashMap;
import java.util.List;
 
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
 
public class SpecialAdapter extends SimpleAdapter {
    private int[] colors = new int[] { 0xFFDDDB90, 0xFFFAFAC1  };// { 0x00DDDB90, 0x00FAFAC1  }; // { 0xFFDDEBF7, 0xFFFFFFFF  };
     
    public SpecialAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = super.getView(position, convertView, parent);
      if (position<1)
      {
    	  view.setBackgroundColor(0xFF275049);//(0xFF275049);//(0xFF5B9BD5);
      }
      else{
      int colorPos = position % colors.length;
      view.setBackgroundColor(colors[colorPos]);}
      return view;
    }
}