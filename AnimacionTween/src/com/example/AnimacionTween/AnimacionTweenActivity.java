package com.example.AnimacionTween;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class AnimacionTweenActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView texto=(TextView) findViewById(R.id.textView);
        Animation animacion=AnimationUtils.loadAnimation(this,R.anim.tweenanimation);
        texto.startAnimation(animacion);
    }
}