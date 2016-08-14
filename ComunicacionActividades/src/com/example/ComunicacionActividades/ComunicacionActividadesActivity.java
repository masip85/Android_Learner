package com.example.ComunicacionActividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ComunicacionActividadesActivity extends Activity {
	private EditText editNombre;
	private TextView resultado;
	private String S;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editNombre   = (EditText) findViewById(R.id.editNombre);           
        
    }
    
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
    	
    	if (requestCode==1234 & resultCode==RESULT_OK){
    		S=data.getExtras().getString("respuesta");
    		resultado   = (TextView) findViewById(R.id.textResultadoConfirmacion);   
    		resultado.setText(S.toString());
    	}
    }
    
    public void pedirConfirmacion(View view){
    	
    	Intent i = new Intent(this,Verificar.class);
    	i.putExtra("campoEdit",editNombre.getText().toString());
          startActivityForResult(i,1234);
          
    }
    
 
}