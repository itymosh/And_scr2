package com.nd2;


import java.util.Random;




import android.annotation.SuppressLint;
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

@SuppressLint("ClickableViewAccessibility")
public class SecondActivity extends Activity implements OnClickListener{
	 private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 250;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	    private GestureDetector gestureDetector;
	    View.OnTouchListener gestureListener;
	//	private ImageView myImage;
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
		
	  //      myImage = (ImageView)findViewById(R.id.image);
		    // Sets the tag
	//	    myImage.setTag(IMAGEVIEW_TAG);
		    
		    // set the listener to the dragging data
	//	    myImage.setOnLongClickListener(new MyClickListener());
		   
	//	    findViewById(R.id.toplinear).setOnDragListener(new MyDragListener());
	//	    findViewById(R.id.bottomlinear).setOnDragListener(new MyDragListener());
		
	        
	
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
