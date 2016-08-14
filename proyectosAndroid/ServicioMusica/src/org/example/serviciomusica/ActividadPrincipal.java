package org.example.serviciomusica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActividadPrincipal extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
   
        Button arrancar = (Button) this.findViewById(R.id.boton_arrancar);
        arrancar.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		startService(new Intent(ActividadPrincipal.this, ServicioMusica.class));
        	}
        });
    
        Button detener = (Button) this.findViewById(R.id.boton_detener);
        detener.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		stopService(new Intent(ActividadPrincipal.this, ServicioMusica.class));
        	}
        });

        Button sos = (Button) this.findViewById(R.id.boton_socorro);
        sos.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		stopService(new Intent(ActividadPrincipal.this, ServicioMusica.class));
        	}
        });
    }
}
