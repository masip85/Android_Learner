package com.example.pruebazoom;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {
	 ViewGroup viewMain;
	 float mScaleFactor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMain=(ViewGroup) findViewById(R.id.RelativeLayout01);
        viewMain.setScaleX((float) 0.5);
        viewMain.setScaleY((float) 0.5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    


    
}
