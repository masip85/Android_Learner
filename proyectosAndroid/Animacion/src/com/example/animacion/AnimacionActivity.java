package com.example.animacion;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AnimacionActivity extends Activity {
    
	AnimationDrawable animacion;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animacion = (AnimationDrawable) getResources().getDrawable(R.drawable.animacion);
        
        ImageView vista = new ImageView(this);
        vista.setBackgroundColor(Color.WHITE);
        vista.setImageDrawable(animacion);
        vista.setOnClickListener(new OnClickListener(){
        	public void onClick (View view) {
        		animacion.start();
        	}
        });
        
        setContentView(vista);
    }
}