package org.example.asteroides;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Asteroides extends Activity implements OnGesturePerformedListener {
	private GestureLibrary libreria;
	private MediaPlayer mp;
	

	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView texto = (TextView) findViewById(R.id.textView);
		Animation animacion = AnimationUtils.loadAnimation(this,
				R.anim.giro_con_zoom);
		texto.startAnimation(animacion);
		Button boton = (Button) findViewById(R.id.btnJugar);
		Animation animacion2 = AnimationUtils.loadAnimation(this,
				R.anim.aparecer);
		boton.startAnimation(animacion2);
		Button boton2 = (Button) findViewById(R.id.btnConfigurar);
		Animation animacion3 = AnimationUtils.loadAnimation(this,
				R.anim.desplazamiento_derecha);
		boton2.startAnimation(animacion3);

		Button btnAcercaDe = (Button) findViewById(R.id.btnAcercaDe);
		btnAcercaDe.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lanzarAcercaDe(v);
			}
		});

		libreria = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!libreria.load()) {
			finish();
		}
		GestureOverlayView gesturesView = (GestureOverlayView) findViewById(R.id.gestures);
		gesturesView.addOnGesturePerformedListener(this);

		Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();

		mp = MediaPlayer.create(this, R.raw.audio);
		mp.start();

	}

	public void lanzarAcercaDe(View view) {
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}

	public void lanzarConfiguracion(View view) {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	public void lanzarPuntuaciones(View view) {
		Intent i = new Intent(this, Puntuaciones.class);
		startActivity(i);
	}

	public void lanzarJuego(View view) {
		Intent i = new Intent(this, Juego.class);
		startActivity(i);
	}

	@Override
	// Crea el menu en tiempo de ejecucion a partir del XML
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.acercaDe:
			lanzarAcercaDe(null);
			break;
		case R.id.config:
			Intent i = new Intent(this, Preferencias.class);
			startActivity(i);
			break;

		}
		return true;
	}

	public void onGesturePerformed(GestureOverlayView ov, Gesture gesture) {
		ArrayList<Prediction> predictions = libreria.recognize(gesture);
		if (predictions.size() > 0) {
			String comando = predictions.get(0).name;
			if (comando.equals("play")) {
				lanzarJuego(null);
			} else if (comando.equals("configurar")) {
				lanzarConfiguracion(null);
			} else if (comando.equals("acerca_de")) {
				lanzarAcercaDe(null);
			} else if (comando.equals("cancelar")) {
				finish();
			}
		}
	}

	@Override
	protected void onDestroy() {

		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		mp.start();
		super.onResume();
		Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onStart() {
		super.onStart();
		mp.start();
		Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPause() {
		mp.pause();
		super.onPause();
		Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onSaveInstanceState(Bundle estadoGuardado) {
		super.onSaveInstanceState(estadoGuardado);
		if (mp!=null){
			int pos =mp.getCurrentPosition();
			estadoGuardado.putInt("posicion", pos);
	
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle estadoGuardado) {
		super.onRestoreInstanceState(estadoGuardado);
		if (mp!=null && estadoGuardado!=null){
			int pos =estadoGuardado.getInt("posicion");
	
		}
		
	}

}