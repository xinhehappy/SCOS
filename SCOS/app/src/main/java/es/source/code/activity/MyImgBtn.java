package es.source.code.activity;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/14.
 */

public class MyImgBtn extends LinearLayout{

    private ImageView imageViewBtn;
    private TextView textView;
    public MyImgBtn(Context context, AttributeSet attrs){
        super(context,attrs);

        imageViewBtn = new ImageView(context,attrs);
        imageViewBtn.setPadding(0,0,0,0);
        textView = new TextView(context, attrs);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setPadding(0,0,0,0);
        setClickable(true);
        setFocusable(true);
        setBackgroundColor(Color.GRAY);
        setOrientation(LinearLayout.VERTICAL);
        addView(imageViewBtn);
        addView(textView);
    }
}
