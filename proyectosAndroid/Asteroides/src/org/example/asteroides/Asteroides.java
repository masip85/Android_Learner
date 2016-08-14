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
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroides extends Activity implements OnGesturePerformedListener {

//	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();
	public static AlmacenPuntuaciones almacen;
	private MediaPlayer mp;
	private GestureLibrary libreria;
	private Button bAcercaDe;
	private Button bJugar;
	private Button bPreferencias;
	private Button bPuntuaciones;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

//		almacen = new AlmacenPuntuacionesPreferencias(this);
//		almacen = new AlmacenPuntuacionesFicheroInterno(this);
//		almacen = new AlmacenPuntuacionesFicheroExterno(this);
//		almacen = new AlmacenPuntuacionesXML_SAX(this);
//		almacen = new AlmacenPuntuacionesXML_DOM(this);
//		almacen = new AlmacenPuntuacionesSQLite(this);
//		almacen = new AlmacenPuntuacionesProvider(this);
//		almacen = new AlmacenPuntuacionesSocket(this);
		almacen = new AlmacenPuntuacionesSerWeb(this);
		
		bAcercaDe = (Button) findViewById(R.id.btnAcercaDe);
		bAcercaDe.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarAcercaDe();
			}
		});

		bJugar = (Button) findViewById(R.id.btnJugar);
		bJugar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarJugar();
			}
		});

		bPreferencias = (Button) findViewById(R.id.btnConfiguracion);
		bPreferencias.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarPreferencias();
			}
		});

		bPuntuaciones = (Button) findViewById(R.id.btnPuntuaciones);
		bPuntuaciones.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarPuntuaciones();
			}
		});

		libreria = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!libreria.load()) {
			finish();
		}
		GestureOverlayView gesturesView = (GestureOverlayView) findViewById(R.id.gestures);
		gesturesView.addOnGesturePerformedListener(this);
	    mp = MediaPlayer.create(this, R.raw.audio);

	    startService(new Intent(this, ServicioMusica.class));
	}

	@Override protected void onResume(){
		super.onResume();
		mp.start();
	}

	@Override protected void onPause(){
		super.onPause();
		mp.pause();
	}
	@Override protected void onDestroy(){
		stopService(new Intent(this, ServicioMusica.class));
		super.onDestroy();
	}
	

	@Override
	protected void onSaveInstanceState(Bundle estadoGuardado){
		super.onSaveInstanceState(estadoGuardado);
		if (mp != null) {
			int pos = mp.getCurrentPosition();
			estadoGuardado.putInt("posicion", pos);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle estadoGuardado){
		super.onRestoreInstanceState(estadoGuardado);
		if (estadoGuardado != null && mp != null) {
			int pos = estadoGuardado.getInt("posicion");
			mp.seekTo(pos);
		}
	}

	public void lanzarAcercaDe() {
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}

	public void lanzarJugar() {
		Intent i = new Intent(this, Juego.class);
		startActivityForResult(i, 1234);
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1234 & resultCode == RESULT_OK & data != null) {
			int puntuacion = data.getExtras().getInt("puntuacion");
			String nombre = "Yo";
			almacen.guardarPuntuacion(puntuacion, nombre, System.currentTimeMillis());
			lanzarPuntuaciones();
		}
	}

	public void lanzarPreferencias() {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	public void lanzarPuntuaciones() {
		Intent i = new Intent(this, Puntuaciones.class);
		startActivity(i);
	}
	
	@Override
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
			lanzarAcercaDe();
			break;
		case R.id.confg:
			lanzarPreferencias();
			break;
		}
		return false;
	}

	public void onGesturePerformed(GestureOverlayView ov, Gesture gesture) {
		ArrayList<Prediction> predictions = libreria.recognize(gesture);
		if (predictions.size() > 0) {
			String comando = predictions.get(0).name;
			if (comando.equals("play")) {
				lanzarJugar();
			} else if (comando.equals("configurar")) {
				lanzarPreferencias();
			} else if (comando.equals("acerca_de")) {
				lanzarAcercaDe();
			} else if (comando.equals("cancelar")) {
				finish();
			}
		}
	}
}
