package com.example.intenciones;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class IntencionesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void pgWeb(View view) {
    	Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("www.androidcurso.com"));
    	startActivity(i);
    }

    public void llamadaTelefono(View view) {
    	Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:651535226"));
    	startActivity(i);
    }

    public void tomarFoto(View view) {
    	Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    	startActivity(i);
    }

    public void mandarCorreo(View view) {
    	Intent i = new Intent(Intent.ACTION_SEND);
    	i.setType("text/plain");
    	i.putExtra(Intent.EXTRA_SUBJECT, "asunto");
    	i.putExtra(Intent.EXTRA_TEXT, "texto del correo");
    	i.putExtra(Intent.EXTRA_EMAIL, new String[] { "jobelal2@inf.upv.es" });
    	startActivity(i);
    }
    
    
    
}