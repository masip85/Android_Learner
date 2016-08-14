package com.comunicaciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Confirmacion extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validar);

    Bundle extras = getIntent().getExtras();
    String str = "Hola " + extras.getString("nombre") + " ¿Aceptas las condiciones?";
    ((TextView) findViewById(R.id.textPreguntaConfirmacion)).setText(str);
    }
    
    public void procesar(View v) {
    	System.out.println("Dentro de la funcion procesar");
    	Intent i = new Intent();
    	i.putExtra("contestacion", v.getTag().toString());
    	setResult(RESULT_OK, i);
    	finish();
    }
}