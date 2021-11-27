package com.example.mytests.views;

/*import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OnSwipeTouchListener implements View.OnTouchListener  {

    private final String TAG = OnSwipeTouchListener.class.getSimpleName();
    ListView list;
    private GestureDetector gestureDetector;
    private Context context;


    public OnSwipeTouchListener(Context ctx, ListView list) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        context = ctx;
        this.list = list;
    }

    public OnSwipeTouchListener(FragmentActivity activity, RecyclerView recyclerListSubject) {
        super();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void onSwipeRight(int pos) {
        //Do what you want after swiping left to right

        Log.e(TAG, "onSwipeRight: " + pos);

        //SubjectAdapter.swipeRight(pos);
    }

    public void onSwipeLeft(int pos) {

        //Do what you want after swiping right to left
        Log.e(TAG, "onSwipeLeft: " + pos);
        //SubjectAdapter.swipeLeft(pos);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        private int getPosition(MotionEvent e1) {
            return list.pointToPosition((int) e1.getX(), (int) e1.getY());
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY)
                    && Math.abs(distanceX) > SWIPE_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight(getPosition(e1));
                else
                    onSwipeLeft(getPosition(e1));
                return true;
            }
            return false;
        }

    }
}*/

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.mytests.R;

public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private ListView list;

    private static final int SWIPE_MIN_DISTANCE = 80;
    private static final int SWIPE_THRESHOLD_VELOCITY = 50;
    private static final int PEOPLE_FRAGMENT = 0;
    private static final int PLACES_FRAGMENT = 2;

    public MyGestureDetector(ListView list) {
        this.list = list;
    }

    //CONDITIONS ARE TYPICALLY VELOCITY OR DISTANCE
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //if (INSERT_CONDITIONS_HERE)
        //    if (showDeleteButton(e1))
        //        return true;
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            Log.d("SWIPE", "right to left");
            return true; //Right to left
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            Log.d("SWIPE", "left to right");
            return true; //Left to right
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    private boolean showDeleteButton(MotionEvent e1) {
        int pos = list.pointToPosition((int)e1.getX(), (int)e1.getY());
        return showDeleteButton(pos);
    }

    private boolean showDeleteButton(int pos) {
        View child = list.getChildAt(pos);
        if (child != null){
            Button delete = (Button) child.findViewById(R.id.delete_button_id);
            if (delete != null)
                if (delete.getVisibility() == View.INVISIBLE)
                    delete.setVisibility(View.VISIBLE);
                else
                    delete.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }
}
