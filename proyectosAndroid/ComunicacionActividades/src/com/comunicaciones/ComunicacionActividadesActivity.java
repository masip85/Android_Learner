package com.comunicaciones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*
 * 	CODIGOS DE LAS ACTIVIDADES
 * 	111 -> Es la que pide aceptar las condiciones
 */


public class ComunicacionActividadesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void pedirConfirmacion(View view) {
    	Intent i = new Intent(this, Confirmacion.class);
    	i.putExtra("nombre", ((EditText) findViewById(R.id.editNombre)).getText().toString());
    	startActivityForResult(i,111);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if(requestCode == 111 && resultCode == RESULT_OK) {
    		String str = "Resultado " + data.getExtras().getString("contestacion");
    		((TextView) this.findViewById(R.id.textResultadoConfirmacion)).setText(str);
    	}
    	
    }
    
}