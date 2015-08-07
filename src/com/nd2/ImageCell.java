package com.nd2;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This subclass of ImageView is used to display an image on an GridView.
 * An ImageCell knows which cell on the grid it is showing and which grid it is attached to
 * Cell numbers are from 0 to NumCells-1.
 * It also knows if it is empty.
 *
 * <p> Image cells are places where images can be dragged from and dropped onto.
 * Therefore, this class implements both the DragSource and DropTarget interfaces.
 * 
 */

public class ImageCell extends ImageView 
    implements DragSource, DropTarget
{
    public boolean mEmpty = true;
    public int mCellNumber = -1;
    public GridView mGrid;

/**
 * Constructors
 */
// Constructors

public ImageCell (Context context) {
	super (context);
}
public ImageCell (Context context, AttributeSet attrs) {
	super (context, attrs);
}
public ImageCell (Context context, AttributeSet attrs, int style) {
	super (context, attrs, style);
}

/**
 */
// DragSource interface methods

/**
 * This method is called to determine if the DragSource has something to drag.
 * 
 * @return True if there is something to drag
 */

public boolean allowDrag () {
    // There is something to drag if the cell is not empty.
    return !mEmpty;
}

/**
 * setDragController
 *
 */

public void setDragController (DragController dragger)
{
    // Do nothing. We do not need to know the controller object.
}

/**
 * onDropCompleted
 *
 */

public void onDropCompleted (View target, boolean success)
{
    // If the drop succeeds, the image has moved elsewhere. 
    // So clear the image cell.
    if (success) {
       mEmpty = true;
       if (mCellNumber >= 0) {
    	   int bg;
    	  bg= setcolor(mCellNumber);
           bg = mEmpty ? bg : R.color.cell_filled;
          setBackgroundResource (bg);
          setImageDrawable (null);
       } else {
         // For convenience, we use a free-standing ImageCell to
         // take the image added when the Add Image button is clicked.
         setImageResource (0);
       }
    }
}

public int setcolor(int mcellnumber2)

{ int bg=R.color.cell_2x_letter;
if (mcellnumber2%2==0)  	bg= (R.color.cell_white);

if (mcellnumber2%2==1)  	bg= (R.color.cell_white2);

//bg= (R.color.drop_target_enabled);
if(mcellnumber2==0||mcellnumber2==7||mcellnumber2==14||mcellnumber2==105||mcellnumber2==119||mcellnumber2==210||mcellnumber2==217||mcellnumber2==224)
	bg= (R.color.cell_3x_word);
else
if(mcellnumber2==20||mcellnumber2==24||mcellnumber2==76||mcellnumber2==88||mcellnumber2==136||mcellnumber2==148||mcellnumber2==204||mcellnumber2==200)
    	bg= (R.color.cell_2x_word);
    else
    	if(mcellnumber2==3||mcellnumber2==11||mcellnumber2==36||mcellnumber2==38||mcellnumber2==45||mcellnumber2==52||mcellnumber2==59||mcellnumber2==92
    	||mcellnumber2==96||mcellnumber2==98||mcellnumber2==102||mcellnumber2==108||mcellnumber2==116||mcellnumber2==122||mcellnumber2==126||mcellnumber2==128
    	||mcellnumber2==132||mcellnumber2==165||mcellnumber2==172||mcellnumber2==179||mcellnumber2==186||mcellnumber2==188||mcellnumber2==213||mcellnumber2==221)
        	bg= (R.color.cell_2x_letter);
        else
        	if(mcellnumber2==16||mcellnumber2==32||mcellnumber2==48||mcellnumber2==64||mcellnumber2==70||mcellnumber2==56||mcellnumber2==42||mcellnumber2==28
        			||mcellnumber2==154||mcellnumber2==168||mcellnumber2==182||mcellnumber2==196||mcellnumber2==208||mcellnumber2==192||mcellnumber2==176||mcellnumber2==160) 
   
bg= (R.color.cell_3x_letter);  
	return bg;
	}

/**
 */
// DropTarget interface implementation

/**
 * Handle an object being dropped on the DropTarget.
 * This is the where the drawable of the dragged view gets copied into the ImageCell.
 * 
 * @param source DragSource where the drag started
 * @param x X coordinate of the drop location
 * @param y Y coordinate of the drop location
 * @param xOffset Horizontal offset with the object being dragged where the original
 *          touch happened
 * @param yOffset Vertical offset with the object being dragged where the original
 *          touch happened
 * @param dragView The DragView that's being dragged around on screen.
 * @param dragInfo Data associated with the object being dragged
 * 
 */
public void onDrop(DragSource source, int x, int y, int xOffset, int yOffset,
        DragView dragView, Object dragInfo)
{
    // Mark the cell so it is no longer empty.
    mEmpty = false;
    int bg;
    bg= setcolor(mCellNumber);
     bg = mEmpty ? R.color.cell_empty : bg;
    setBackgroundResource (bg);

    // The view being dragged does not actually change its parent and switch over to the ImageCell.
    // What we do is copy the drawable from the source view.
    ImageView sourceView = (ImageView) source;
    Drawable d = sourceView.getDrawable ();
    if (d != null) {
       this.setImageDrawable (d);
    }

    // toast ("onDrop cell " + mCellNumber);

}

/**
 * React to a dragged object entering the area of this DropSpot.
 * Provide the user with some visual feedback.
 */    
public void onDragEnter(DragSource source, int x, int y, int xOffset, int yOffset,
        DragView dragView, Object dragInfo)
{
	int bg; 
	  bg= setcolor(mCellNumber);
//    bg = mEmpty ? R.color.cell_empty_hover : R.color.cell_filled_hover;
    bg = mEmpty ? bg : R.color.cell_filled_hover;
    setBackgroundResource (bg);
}

/**
 * React to something being dragged over the drop target.
 */    
public void onDragOver(DragSource source, int x, int y, int xOffset, int yOffset,
        DragView dragView, Object dragInfo)
{
}

/**
 * React to a drag 
 */    
public void onDragExit(DragSource source, int x, int y, int xOffset, int yOffset,
        DragView dragView, Object dragInfo)
{
	int bg;
	  bg= setcolor(mCellNumber);
	bg = mEmpty ? bg : R.color.cell_filled;
    setBackgroundResource (bg);
}

/**
 * Check if a drop action can occur at, or near, the requested location.
 * This may be called repeatedly during a drag, so any calls should return
 * quickly.
 * 
 * @param source DragSource where the drag started
 * @param x X coordinate of the drop location
 * @param y Y coordinate of the drop location
 * @param xOffset Horizontal offset with the object being dragged where the
 *            original touch happened
 * @param yOffset Vertical offset with the object being dragged where the
 *            original touch happened
 * @param dragView The DragView that's being dragged around on screen.
 * @param dragInfo Data associated with the object being dragged
 * @return True if the drop will be accepted, false otherwise.
 */
public boolean acceptDrop(DragSource source, int x, int y, int xOffset, int yOffset,
        DragView dragView, Object dragInfo)
{
    // An ImageCell accepts a drop if it is empty and if it is part of a grid.
    // A free-standing ImageCell does not accept drops.
    return mEmpty  && (mCellNumber >= 0);
}

/**
 * Estimate the surface area where this object would land if dropped at the
 * given location.
 * 
 * @param source DragSource where the drag started
 * @param x X coordinate of the drop location
 * @param y Y coordinate of the drop location
 * @param xOffset Horizontal offset with the object being dragged where the
 *            original touch happened
 * @param yOffset Vertical offset with the object being dragged where the
 *            original touch happened
 * @param dragView The DragView that's being dragged around on screen.
 * @param dragInfo Data associated with the object being dragged
 * @param recycle {@link Rect} object to be possibly recycled.
 * @return Estimated area that would be occupied if object was dropped at
 *         the given location. Should return null if no estimate is found,
 *         or if this target doesn't provide estimations.
 */
public Rect estimateDropLocation(DragSource source, int x, int y, int xOffset, int yOffset,
            DragView dragView, Object dragInfo, Rect recycle)
{
    return null;
}

/**
 */
// Other Methods

/**
 * Return true if this cell is empty.
 * If it is, it means that it will accept dropped views.
 * It also means that there is nothing to drag.
 * 
 * @return boolean
 */

public boolean isEmpty ()
{
    return mEmpty;
}

/**
 * Call this view's onClick listener. Return true if it was called.
 * Clicks are ignored if the cell is empty.
 * 
 * @return boolean
 */

public boolean performClick ()
{
    if (!mEmpty) return super.performClick ();
    return false;
}

/**
 * Call this view's onLongClick listener. Return true if it was called.
 * Clicks are ignored if the cell is empty.
 * 
 * @return boolean
 */

public boolean performLongClick ()
{
    if (!mEmpty) return super.performLongClick ();
    return false;
}

/**
 * Show a string on the screen via Toast if DragActivity.Debugging is true.
 * 
 * @param msg String
 * @return void
 */

public void toast (String msg)
{
    if (!SecondActivity.Debugging) return;
    Toast.makeText (getContext (), msg, Toast.LENGTH_SHORT).show ();
} // end toast

} // end ImageCell
