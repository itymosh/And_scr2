package com.nd2;


import java.util.Random;




import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity implements OnClickListener{
	 private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 250;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	    private GestureDetector gestureDetector;
	    View.OnTouchListener gestureListener;
		private ImageView myImage;
		private static final String IMAGEVIEW_TAG = "The Android Logo";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {

        Bundle genericBundle = this.getIntent().getExtras();
        gestureDetector = new GestureDetector(new MyGestureDetector(genericBundle));
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		//   TextView t = (TextView) findViewById(R.id.numberxx);
	        Random rnd = new Random();
	        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
	  //      t.setBackgroundColor(color);     
	       findViewById(R.id.activitysecond).setOnClickListener(this); 
	        findViewById(R.id.activitysecond).setOnTouchListener(gestureListener);
		
	        myImage = (ImageView)findViewById(R.id.image);
		    // Sets the tag
		    myImage.setTag(IMAGEVIEW_TAG);
		    
		    // set the listener to the dragging data
		    myImage.setOnLongClickListener(new MyClickListener());
		   
		    findViewById(R.id.toplinear).setOnDragListener(new MyDragListener());
		    findViewById(R.id.bottomlinear).setOnDragListener(new MyDragListener());
		
	        
	
	}
	
	private final class MyClickListener implements OnLongClickListener {

	    // called when the item is long-clicked
		@Override
		public boolean onLongClick(View view) {
		// TODO Auto-generated method stub
		
			// create it from the object's tag
			ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());

	        String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
	        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
	        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
	   
	        view.startDrag( data, //data to be dragged
	        				shadowBuilder, //drag shadow
	        				view, //local data about the drag and drop operation
	        				0   //no needed flags
	        			  );
	        
	        
	        view.setVisibility(View.INVISIBLE);
	        return true;
		}	
	}

	class MyDragListener implements OnDragListener {
		Drawable normalShape = getResources().getDrawable(R.drawable.normal_shape);
		Drawable targetShape = getResources().getDrawable(R.drawable.target_shape);

		@Override
		public boolean onDrag(View v, DragEvent event) {
	  
			// Handles each of the expected events
		    switch (event.getAction()) {
		    
		    //signal for the start of a drag and drop operation.
		    case DragEvent.ACTION_DRAG_STARTED:
		        // do nothing
		        break;
		        
		    //the drag point has entered the bounding box of the View
		    case DragEvent.ACTION_DRAG_ENTERED:
		        v.setBackground(targetShape);	//change the shape of the view
		        break;
		        
		    //the user has moved the drag shadow outside the bounding box of the View
		    case DragEvent.ACTION_DRAG_EXITED:
		        v.setBackground(normalShape);	//change the shape of the view back to normal
		        break;
		        
		    //drag shadow has been released,the drag point is within the bounding box of the View
		    case DragEvent.ACTION_DROP:
		        // if the view is the bottomlinear, we accept the drag item
		    	  if(v == findViewById(R.id.imageView10)) {
		    		  View view = (View) event.getLocalState();
		    		  ViewGroup viewgroup = (ViewGroup) view.getParent();
		    		  viewgroup.removeView(view);
		        
		    		  //change the text
		    		  TextView text = (TextView) v.findViewById(R.id.text);
		    		  text.setText("The item is dropped");
		    		  ImageView l =(ImageView) findViewById(R.id.imageView10);
		    		  l.setImageResource(R.drawable.ic_launcher);
		    		  GridLayout containView = (GridLayout) v;
		    		  containView.addView(view);
		    		  
		    		  view.setVisibility(View.VISIBLE);
		    	  } else {
		    		  View view = (View) event.getLocalState();
		    		  view.setVisibility(View.VISIBLE);
		    		  Context context = getApplicationContext();
		    		  Toast.makeText(context, "You can't drop the image here", 
                                                 Toast.LENGTH_LONG).show();
		    		  break;
		    	   }
		    	  break;
		    	  
		    //the drag and drop operation has concluded.
		    case DragEvent.ACTION_DRAG_ENDED:
		        v.setBackground(normalShape);	//go back to normal shape
		    
		    default:
		        break;
		    }
		    return true;
		}
	}

	 class MyGestureDetector extends SimpleOnGestureListener {
	    	private Bundle b;
	    	public MyGestureDetector(Bundle b){
	    		super();
	    		this.b = b;
	    	}
	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            try {
	                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                    return false;
	                // right to left swipe
	                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                    Toast.makeText(SecondActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
	                    Intent i = new Intent (SecondActivity.this, MainActivity.class);
	            /*       if(b.getInt("current") < b.getInt("max")){
	                    i.putExtra("current", b.getInt("current")+1);
	                   }else{
	                	   i.putExtra("current", 1); 
	                   }
	                    i.putExtra("max", 2);*/
	                    SecondActivity.this.startActivity(i);
	                    finish();
	                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                    Toast.makeText(SecondActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
	                    Intent i = new Intent (SecondActivity.this, MainActivity.class);
	                 /*  if(b.getInt("current") > 1){
	                    i.putExtra("current", b.getInt("current")-1);
	                   }else{
	                	   i.putExtra("current", 2); 
	                   }
	                    i.putExtra("max", 2);*/
	                    SecondActivity.this.startActivity(i);
	                    finish();
	                }
	            } catch (Exception e) {
	                // nothing
	            }
	            return false;
	        }

	    }
	    
	    	public void onClick(View v) {
				// TODO Auto-generated method stub
	    	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
