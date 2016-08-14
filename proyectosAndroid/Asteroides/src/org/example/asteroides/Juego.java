package org.example.asteroides;

import android.app.Activity;
import android.os.Bundle;

public class Juego extends Activity {
	private VistaJuego vistaJuego;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);
		vistaJuego = (VistaJuego) findViewById(R.id.VistaJuego);
		vistaJuego.setPadre(this);
	}

	@Override
	protected void onDestroy() {
		vistaJuego.setCorriendo(false);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		vistaJuego.setPausa(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		vistaJuego.setPausa(false);
	}

}