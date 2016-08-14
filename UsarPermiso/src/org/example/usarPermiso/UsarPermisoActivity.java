package org.example.usarPermiso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class UsarPermisoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button b=(Button)findViewById(R.id.button01);
        b.setOnClickListener(new OnClickListener(){

        	public void onClick(View view){
        		Intent i=new Intent();
        		i.setClassName("com.payperview.servicios","com.payperview.servicios.VerVideo");
        		startActivity(i);
        	}
        	
        	
        	
        });
        
        
        
        
    }
}