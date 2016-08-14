package com.example.transition;

import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class TransitionActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView image=new ImageView(this);
        setContentView(image);
        TransitionDrawable transition=(TransitionDrawable) getResources().getDrawable(R.drawable.transicion);
        image.setImageDrawable(transition);
        transition.startTransition(2000);
    }
}