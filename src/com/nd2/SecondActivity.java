package com.nd2;


import java.util.Random;












import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility")
public class SecondActivity extends Activity implements OnClickListener,OnLongClickListener,OnTouchListener{
	 private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 250;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	    private GestureDetector gestureDetector;
	    View.OnTouchListener gestureListener;
	//	private ImageView myImage;
		private static final String IMAGEVIEW_TAG = "The Android Logo";
		// Constants

		private static final int HIDE_TRASHCAN_MENU_ID = Menu.FIRST;
		private static final int SHOW_TRASHCAN_MENU_ID = Menu.FIRST + 1;
		private static final int ADD_OBJECT_MENU_ID = Menu.FIRST + 2;
		private static final int CHANGE_TOUCH_MODE_MENU_ID = Menu.FIRST + 3;

		/**
		 */
		// Variables

		private DragController mDragController;   // Object that handles a drag-drop sequence. It intersacts with DragSource and DropTarget objects.
		private DragLayer mDragLayer;             // The ViewGroup within which an object can be dragged.
		private DeleteZone mDeleteZone;           // A drop target that is used to remove objects from the screen.
		private int mImageCount = 0;              // The number of images that have been added to screen.
		private ImageCell mLastNewCell = null; 
		private ImageCell mLastNewCell2 = null;// The last ImageCell added to the screen when Add Image is clicked.
		private boolean mLongClickStartsDrag = false;   // If true, it takes a long click to start the drag operation.
		                                                // Otherwise, any touch event starts a drag.

		public static final boolean Debugging = false;   // Use this to see extra toast messages.

		/**
		 */
		// Methods

		/**
		 * Add a new image so the user can move it around. It shows up in the image_source_frame
		 * part of the screen.
		 * 
		 * @param resourceId int - the resource id of the image to be added
		 */    

		public void addNewImageToScreen (int resourceId)
		{
		    if (mLastNewCell != null) mLastNewCell.setVisibility (View.GONE);

		    FrameLayout imageHolder = (FrameLayout) findViewById (R.id.image_source_frame);
		    if (imageHolder != null) {
		       FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams (LayoutParams.FILL_PARENT, 
		                                                                   LayoutParams.FILL_PARENT, 
		                                                                   Gravity.CENTER);
		       ImageCell newView = new ImageCell (this);
		       newView.setImageResource (resourceId);
		       imageHolder.addView (newView, lp);
		       newView.mEmpty = false;
		       newView.mCellNumber = -1;
		       mLastNewCell = newView;
		       mImageCount++;

		    
		       // Have this activity listen to touch and click events for the view.
		       newView.setOnClickListener(this);
		       newView.setOnLongClickListener(this);
		       newView.setOnTouchListener (this);

		    }
		}
//:::
		public void addNewImageToScreen2 (int resourceId)
		{
		    if (mLastNewCell2 != null) mLastNewCell2.setVisibility (View.GONE);

		   FrameLayout imageHolder2 = (FrameLayout) findViewById (R.id.FrameLayout02);
		    if (imageHolder2 != null) {
		       FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams (LayoutParams.FILL_PARENT, 
		                                                                   LayoutParams.FILL_PARENT, 
		                                                                   Gravity.CENTER);
		       
		       ImageCell newView2 = new ImageCell (this);
		       newView2.setImageResource (resourceId);
		       imageHolder2.addView (newView2, lp2);
		       newView2.mEmpty = false;
		       newView2.mCellNumber = -1;
		       mLastNewCell2 = newView2;
		       mImageCount++;
		       newView2.setOnClickListener(this);
		       newView2.setOnLongClickListener(this);
		       newView2.setOnTouchListener (this);

		    }		
		
		}
		
//:::
		
		
		/**
		 * Add one of the images to the screen so the user has a new image to move around. 
		 * See addImageToScreen.
		 *
		 */    

		public void addNewImageToScreen ()
		{
		    int resourceId = R.drawable.hello;

		    int m = mImageCount % 3;
		    if (m == 1) resourceId = R.drawable.photo1;
		    else if (m == 2) resourceId = R.drawable.photo2;
		    addNewImageToScreen (resourceId);
		}

		/**
		 * Handle a click on a view.
		 *
		 */    

		public void onClick(View v) 
		{
		    if (mLongClickStartsDrag) {
		       // Tell the user that it takes a long click to start dragging.
		       toast ("Press and hold to drag an image.");
		    }
		}

		/**
		 * Handle a click of the Add Image button
		 *
		 */    

		public void onClickAddImage (View v) 
		{
		    addNewImageToScreen ();
		}

		/**
		 * Handle a click on the Wglxy views at the bottom.
		 *
		 */    

		public void onClickWglxy (View v) {
		    Resources res = getResources ();
		    toast (res.getString (R.string.demo_toast));
		    Intent viewIntent = new Intent ("android.intent.action.VIEW", 
		                                    Uri.parse (res.getString (R.string.demo_uri)));
		    startActivity(viewIntent);
		    
		}

		/**
		 * onCreate - called when the activity is first created.
		 * 
		 * Creates a drag controller and sets up three views so click and long click on the views are sent to this activity.
		 * The onLongClick method starts a drag sequence.
		 *
		 */

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
	        GridView gridView = (GridView) findViewById(R.id.image_grid_view);

	        if (gridView == null) toast ("Unable to find GridView");
	        else {
	             gridView.setAdapter (new ImageCellAdapter(this));
	             // gridView.setOnItemClickListener (this);
	        }

	        mDragController = new DragController(this);
	        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
	        mDragLayer.setDragController (mDragController);
	        mDragLayer.setGridView (gridView);

	        mDragController.setDragListener (mDragLayer);
	        // mDragController.addDropTarget (mDragLayer);

	        mDeleteZone = (DeleteZone) findViewById (R.id.delete_zone_view);

	        // Give the user a little guidance.
	        Toast.makeText (getApplicationContext(), 
	                        getResources ().getString (R.string.instructions),
	                        Toast.LENGTH_LONG).show ();
	        
	
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    
	    menu.add(0, HIDE_TRASHCAN_MENU_ID, 0, "Hide Trashcan").setShortcut('1', 'c');
	    menu.add(0, SHOW_TRASHCAN_MENU_ID, 0, "Show Trashcan").setShortcut('2', 'c');
	    menu.add(0, ADD_OBJECT_MENU_ID, 0, "Add View").setShortcut('9', 'z');
	    menu.add (0, CHANGE_TOUCH_MODE_MENU_ID, 0, "Change Touch Mode");


	    return true;
	//	getMenuInflater().inflate(R.menu.second, menu);

	}


public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
{
    ImageCell i = (ImageCell) v;
    trace ("onItemClick in view: " + i.mCellNumber);
}

/**
 * Handle a long click.
 * If mLongClick only is true, this will be the only way to start a drag operation.
 *
 * @param v View
 * @return boolean - true indicates that the event was handled
 */    

public boolean onLongClick(View v) 
{
    if (mLongClickStartsDrag) {
       
        //trace ("onLongClick in view: " + v + " touchMode: " + v.isInTouchMode ());

        // Make sure the drag was started by a long press as opposed to a long click.
        // (Note: I got this from the Workspace object in the Android Launcher code. 
        //  I think it is here to ensure that the device is still in touch mode as we start the drag operation.)
        if (!v.isInTouchMode()) {
           toast ("isInTouchMode returned false. Try touching the view again.");
           return false;
        }
        return startDrag (v);
    }

    // If we get here, return false to indicate that we have not taken care of the event.
    return false;
}

/**
 * Perform an action in response to a menu item being clicked.
 *
 */

public boolean onOptionsItemSelected (MenuItem item) 
{
    switch (item.getItemId()) {
        case HIDE_TRASHCAN_MENU_ID:
            if (mDeleteZone != null) mDeleteZone.setVisibility (View.INVISIBLE);
            return true;
        case SHOW_TRASHCAN_MENU_ID:
            if (mDeleteZone != null) mDeleteZone.setVisibility (View.VISIBLE);
            return true;
        case ADD_OBJECT_MENU_ID:
            // Add a new object to the screen;
            addNewImageToScreen ();
            return true;
        case CHANGE_TOUCH_MODE_MENU_ID:
            mLongClickStartsDrag = !mLongClickStartsDrag;
            String message = mLongClickStartsDrag ? "Changed touch mode. Drag now starts on long touch (click)." 
                                                  : "Changed touch mode. Drag now starts on touch (click).";
            Toast.makeText (getApplicationContext(), message, Toast.LENGTH_LONG).show ();
            return true;
    }

    return super.onOptionsItemSelected(item);
}

/**
 * Resume the activity.
 */

@Override protected void onResume() {
    super.onResume();

    View v  = findViewById (R.id.wglxy_bar);
    if (v != null) {
       Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
       //anim1.setAnimationListener (new StartActivityAfterAnimation (i));
       v.startAnimation (anim1);
    }
}

/**
 * This is the starting point for a drag operation if mLongClickStartsDrag is false.
 * It looks for the down event that gets generated when a user touches the screen.
 * Only that initiates the drag-drop sequence.
 *
 */    

public boolean onTouch (View v, MotionEvent ev) 
{
    // If we are configured to start only on a long click, we are not going to handle any events here.
    if (mLongClickStartsDrag) return false;

    boolean handledHere = false;

    final int action = ev.getAction();

    // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
    if (action == MotionEvent.ACTION_DOWN) {
       handledHere = startDrag (v);
       if (handledHere) v.performClick ();
    }
    
    return handledHere;
}   

/**
 * Start dragging a view.
 *
 */    

public boolean startDrag (View v)
{
    DragSource dragSource = (DragSource) v;

    // We are starting a drag. Let the DragController handle it.
    mDragController.startDrag (v, dragSource, dragSource, DragController.DRAG_ACTION_MOVE);

    return true;
}

/**
 * Show a string on the screen via Toast.
 * 
 * @param msg String
 * @return void
 */

public void toast (String msg)
{
    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
} // end toast

/**
 * Send a message to the debug log. Also display it using Toast if Debugging is true.
 */

public void trace (String msg) 
{
    Log.d ("DragActivity", msg);
    if (!Debugging) return;
    toast (msg);
}
}
