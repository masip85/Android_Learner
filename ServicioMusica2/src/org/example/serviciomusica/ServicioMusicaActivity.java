package org.example.serviciomusica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServicioMusicaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button arrancar=(Button)findViewById(R.id.boton_arrancar);
        arrancar.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		startService(new Intent(ServicioMusicaActivity.this,ServicioMusica.class));
        	}
        	});
        Button detener=(Button)findViewById(R.id.boton_detener);
        detener.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		stopService(new Intent(ServicioMusicaActivity.this,ServicioMusica.class));
        	}
        	});
    }
}