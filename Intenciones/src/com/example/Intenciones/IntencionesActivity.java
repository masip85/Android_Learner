package com.example.Intenciones;

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
    public void pgWeb(View view){
    	Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.androidcurso.com"));
    	startActivity(intent);
    }
    public void llamadaTelefono(View view){
    	Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:963457689"));
    	startActivity(intent);
    }
    public void googleMaps(View view){
    	Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("geo:41.656313,+0.877351"));
    	startActivity(intent);
    }
    public void tomarFoto(View view){
    	Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
    	startActivity(intent);
    }
    public void mandarCorreo(View view){
    	Intent intent=new Intent(Intent.ACTION_SEND);
    	intent.setType("text/plain");
    	intent.putExtra(Intent.EXTRA_SUBJECT,"asunto");
    	intent.putExtra(Intent.EXTRA_TEXT,"texto del correoooooo");
    	intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vimamo1@etsii.upv.es"});
    	startActivity(intent);
    }
}