package org.example.asteroides;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Juego extends Activity {
	private VistaJuego vistaJuego;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);
		
		vistaJuego=(VistaJuego) findViewById(R.id.VistaJuego);
	}
	
	protected void onDestroy() {
		vistaJuego.getThread().detener();
		
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
	
	protected void onResume() {
		super.onResume();
		Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		vistaJuego.getThread().reanudar();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		vistaJuego.getThread().pausar();
	}
}