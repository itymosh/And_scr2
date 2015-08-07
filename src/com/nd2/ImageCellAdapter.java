package com.nd2;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * This class is used with a GridView object. It provides a set of ImageCell objects 
 * that support dragging and dropping.
 * 
 */

public class ImageCellAdapter extends BaseAdapter 
{

// Constants
public static final int DEFAULT_NUM_IMAGES = 8;

/**
 */
// Variables
public ViewGroup mParentView = null;
private Context mContext;

public ImageCellAdapter(Context c) 
{
    mContext = c;
}

/**
 */
// Methods

/**
 * getCount
 */

public int getCount() 
{
    Resources res = mContext.getResources();
    int numImages = res.getInteger (R.integer.num_images);    
    return numImages;
}

public Object getItem(int position) 
{
    return null;
}

public long getItemId(int position) {
    return position;
}

/**
 * getView
 * Return a view object for the grid.
 * 
 * @return ImageCell
 */
public View getView (int position, View convertView, ViewGroup parent) 
{
    mParentView = parent;

    ImageCell v = null;
    if (convertView == null) {
        // If it's not recycled, create a new ImageCell.
        v = new ImageCell (mContext);
        v.setLayoutParams(new GridView.LayoutParams(30, 30));
        v.setScaleType(ImageView.ScaleType.CENTER_CROP);
        v.setPadding(8, 8, 8, 8);

    } else {
        v = (ImageCell) convertView;
    }

    v.mCellNumber = position;
    v.mGrid = (GridView) mParentView;
    v.mEmpty = true;
    if (position%2==0)  	v.setBackgroundResource (R.color.cell_white);
    
    if (position%2==1)  	v.setBackgroundResource (R.color.cell_white2);
    
//    v.setBackgroundResource (R.color.drop_target_enabled);
    if(position==0||position==7||position==14||position==105||position==119||position==210||position==217||position==224)
    	v.setBackgroundResource (R.color.cell_3x_word);
    else
    if(position==20||position==24||position==76||position==88||position==136||position==148||position==204||position==200)
        	v.setBackgroundResource (R.color.cell_2x_word);
        else
        	if(position==3||position==11||position==36||position==38||position==45||position==52||position==59||position==92
        	||position==96||position==98||position==102||position==108||position==116||position==122||position==126||position==128
        	||position==132||position==165||position==172||position==179||position==186||position==188||position==213||position==221)
            	v.setBackgroundResource (R.color.cell_2x_letter);
            else
            	if(position==16||position==32||position==48||position==64||position==70||position==56||position==42||position==28
            			||position==154||position==168||position==182||position==196||position==208||position==192||position==176||position==160) 
       
    v.setBackgroundResource (R.color.cell_3x_letter);
 //  center ||position==112
    //v.mGrid.requestDisallowInterceptTouchEvent (true);

    //v.setImageResource (R.drawable.hello);

    // Set up to relay events to the activity.
    // The activity decides which events trigger drag operations.
    // Activities like the Android Launcher require a long click to get a drag operation started.
    v.setOnTouchListener ((View.OnTouchListener) mContext);
    v.setOnClickListener ((View.OnClickListener) mContext);
    v.setOnLongClickListener ((View.OnLongClickListener) mContext);

    return v;
}


} // end class
