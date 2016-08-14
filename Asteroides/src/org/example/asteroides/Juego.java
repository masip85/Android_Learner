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
		
		vistaJuego = (VistaJuego) this.findViewById(R.id.VistaJuego);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		vistaJuego.getThread().reanudar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		vistaJuego.getThread().pausar();
	}

	@Override
	protected void onDestroy() {
		vistaJuego.getThread().detener();
		super.onDestroy();
	}
}