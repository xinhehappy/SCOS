package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class SCOSEntry extends Activity implements View.OnTouchListener,GestureDetector.OnGestureListener {

    public GestureDetector myGestureDetector;
    ImageView backgroundImg ;
//    public static final int LOAD_DISPLAY_TIME = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        backgroundImg = (ImageView)this.findViewById(R.id.background_img);
        backgroundImg.setImageDrawable(getResources().getDrawable(R.drawable.scoslogo));

        myGestureDetector = new GestureDetector((GestureDetector.OnGestureListener) this);
        backgroundImg.setOnTouchListener(this);
        backgroundImg.setLongClickable(true);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent mainIntent = new Intent(SCOSEntry.this,MainScreen.class);
//                SCOSEntry.this.startActivity(mainIntent);
//                SCOSEntry.this.finish();
//            }
//        },LOAD_DISPLAY_TIME);


    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private int verticalMinDistance = 20;
    private int minVelocity = 0;
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX()-e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity){
            //向左滑的手势
            Intent mainIntent = new Intent(SCOSEntry.this,MainScreen.class);
            mainIntent.putExtra("value","FromEntry");
            SCOSEntry.this.startActivity(mainIntent);
            SCOSEntry.this.finish();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return myGestureDetector.onTouchEvent(event);
    }
}
