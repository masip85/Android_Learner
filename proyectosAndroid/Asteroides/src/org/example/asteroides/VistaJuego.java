package org.example.asteroides;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class VistaJuego extends View implements SensorEventListener {

	private int puntuacion = 0;
	private Activity padre;
	
	// THREAD Y TIEMPO
	private boolean corriendo;
	private boolean pausa;
	private ThreadJuego thread = new ThreadJuego();
	private static int PERIODO_PROCESO = 50;
	private long ultimoProceso = 0;

	// ASTEROIDES
	private Vector<Grafico> Asteroides;
	private int numAsteroides = 5;
	private Drawable drawableAsteroide[] = new Drawable[3];
	private int numFragmentos;

	// NAVE
	private Grafico nave;
	private int giroNave;
	private float aceleracionNave;
	private static final int PASO_GIRO_NAVE = 5;
	private static final float PASO_ACELERACION_NAVE = 0.5f;

	// MISIL
	private Vector<Misiles> misiles;
	private static int PASO_VELOCIDAD_MISIL = 12;
	private int NUMERO_MISILES = 15;

	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);
		Drawable drawableNave, drawableMisil;
		SharedPreferences pref = context.getSharedPreferences(
				"org.example.asteroides_preferences", Context.MODE_PRIVATE);

		try {
			numFragmentos = Integer.parseInt(pref.getString("fragmentos", "3"));
		} catch (Exception e) {
			numFragmentos = 3;
		}
		
		if (pref.getString("graficos", "0").equals("0")) {
			Path pathAsteroide = new Path();
			pathAsteroide.moveTo((float) 0.3, (float) 0.0);
			pathAsteroide.lineTo((float) 0.6, (float) 0.0);
			pathAsteroide.lineTo((float) 0.6, (float) 0.3);
			pathAsteroide.lineTo((float) 0.8, (float) 0.2);
			pathAsteroide.lineTo((float) 1.0, (float) 0.4);
			pathAsteroide.lineTo((float) 0.8, (float) 0.6);
			pathAsteroide.lineTo((float) 0.9, (float) 0.9);
			pathAsteroide.lineTo((float) 0.8, (float) 1.0);
			pathAsteroide.lineTo((float) 0.4, (float) 1.0);
			pathAsteroide.lineTo((float) 0.0, (float) 0.6);
			pathAsteroide.lineTo((float) 0.0, (float) 0.2);
			pathAsteroide.lineTo((float) 0.3, (float) 0.0);
			for (int i = 0; i < 3; i++) {
				ShapeDrawable dAsteroide = new ShapeDrawable(new PathShape(
						pathAsteroide, 1, 1));
				dAsteroide.getPaint().setColor(Color.WHITE);
				dAsteroide.getPaint().setStyle(Style.STROKE);
				dAsteroide.setIntrinsicWidth(50 - i * 14);
				dAsteroide.setIntrinsicHeight(50 - i * 14);
				drawableAsteroide[i] = dAsteroide;
			}
			Path pathNave = new Path();
			pathNave.moveTo((float) 0.0, (float) 0.0);
			pathNave.lineTo((float) 1.0, (float) 0.5);
			pathNave.lineTo((float) 0.0, (float) 1.0);
			pathNave.lineTo((float) 0.0, (float) 0.0);
			ShapeDrawable dNave = new ShapeDrawable(new PathShape(pathNave, 1,
					1));
			dNave.getPaint().setColor(Color.WHITE);
			dNave.getPaint().setStyle(Style.STROKE);
			dNave.setIntrinsicWidth(20);
			dNave.setIntrinsicHeight(15);
			drawableNave = dNave;

			ShapeDrawable dMisil = new ShapeDrawable(new RectShape());
			dMisil.getPaint().setColor(Color.WHITE);
			dMisil.getPaint().setStyle(Style.STROKE);
			dMisil.setIntrinsicWidth(15);
			dMisil.setIntrinsicHeight(3);
			drawableMisil = dMisil;

			setBackgroundColor(Color.BLACK);
		} else {
			drawableAsteroide[0] = context.getResources().getDrawable(
					R.drawable.asteroide1);
			drawableAsteroide[1] = context.getResources().getDrawable(
					R.drawable.asteroide2);
			drawableAsteroide[2] = context.getResources().getDrawable(
					R.drawable.asteroide3);

			drawableNave = context.getResources().getDrawable(R.drawable.nave);
			drawableMisil = context.getResources().getDrawable(
					R.drawable.misil1);
		}

		Asteroides = new Vector<Grafico>();
		for (int i = 0; i < numAsteroides; i++) {
			Grafico asteroide = new Grafico(this, drawableAsteroide[0]);
			asteroide.setIncY(Math.random() * 4 - 2);
			asteroide.setIncX(Math.random() * 4 - 2);
			asteroide.setAngulo((int) (Math.random() * 360));
			asteroide.setRotacion((int) (Math.random() * 8 - 4));
			Asteroides.add(asteroide);
		}
		nave = new Grafico(this, drawableNave);

		misiles = new Vector<Misiles>();
		for (int i = 0; i < NUMERO_MISILES; i++) {
			Grafico misil = new Grafico(this, drawableMisil);
			misiles.add(new Misiles(misil));
		}

		SensorManager mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> listSensors = mSensorManager
				.getSensorList(Sensor.TYPE_ORIENTATION);
		if (!listSensors.isEmpty()) {
			Sensor orientationSensor = listSensors.get(0);
			mSensorManager.registerListener(this, orientationSensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	protected void onSizeChanged(int ancho, int alto, int ancho_anter,
			int alto_anter) {
		super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);

		nave.setPosX((ancho - nave.getAncho()) / 2);
		nave.setPosY((alto - nave.getAlto()) / 2);
		for (Grafico asteroide : Asteroides) {
			do {
				asteroide.setPosX(Math.random()
						* (ancho - asteroide.getAncho()));
				asteroide.setPosY(Math.random() * (alto - asteroide.getAlto()));
			} while (asteroide.distancia(nave) < (ancho + alto) / 5);
		}
		thread.start();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		nave.dibujaGrafico(canvas);
		for (Grafico asteroide : Asteroides) {
			asteroide.dibujaGrafico(canvas);
		}
		for (Misiles misil : misiles) {
			if (misil.getMisilActivo()) {
				misil.getGraficoMisil().dibujaGrafico(canvas);
			}
		}
	}

	class ThreadJuego extends Thread {
		@Override
		public void run() {
			corriendo = true;
			while (corriendo) {
				if (!pausa)
					actualizaFisica();
			}
		}
	}

	protected synchronized void actualizaFisica() {
		long ahora = System.currentTimeMillis();

		if (ultimoProceso + PERIODO_PROCESO > ahora) {
			return;
		}

		double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
		// Actualizar posición de la nave
		nave.setAngulo((int) (nave.getAngulo() + giroNave * retardo));
		double nIncX = nave.getIncX() + aceleracionNave
				* Math.cos(Math.toRadians(nave.getAngulo())) * retardo;
		double nIncY = nave.getIncY() + aceleracionNave
				* Math.sin(Math.toRadians(nave.getAngulo())) * retardo;
		if (Grafico.distanciaE(0, 0, nIncX, nIncY) <= Grafico.getMaxVelocidad()) {
			nave.setIncX(nIncX);
			nave.setIncY(nIncY);
		}
		// Actualizamos posición de asteroides
		nave.incrementaPos();
		for (Grafico asteroide : Asteroides) {
			asteroide.incrementaPos();
		}
		// Actualizamos posición de misil
		for (Misiles misil : misiles) {
			if (misil.getMisilActivo()) {
				misil.getGraficoMisil().incrementaPos();
				misil.decrementarDistanciaMisil();
			}
			if (misil.getDistanciaMisil() < 0) {
				misil.setMisilActivo(false);
			} else {
				for (int i = 0; i < Asteroides.size(); i++) {
					if (misil.getGraficoMisil().verificaColision(
							Asteroides.elementAt(i))) {
						destruyeAsteroide(i, misil);
						break;
					}
				}
			}
		}

		// Colisión de asteroide con la nave
		for (Grafico asteroide : Asteroides) {
			if (asteroide.verificaColision(nave)) {
				terminarPartida();
			}
		}
		ultimoProceso = ahora;
	}

	private void destruyeAsteroide(int i, Misiles misil) {
		int tam; 
		puntuacion += 1000;
		if(Asteroides.get(i).getDrawable()!=drawableAsteroide[2]) { 
			if(Asteroides.get(i).getDrawable()==drawableAsteroide[1]) { 
				tam=2; 
			} else { 
				tam=1; 
			} 
		 	
			for(int n=0;n<numFragmentos;n++) { 
				Grafico asteroide = new Grafico(this,drawableAsteroide[tam]); 
				asteroide.setPosX(Asteroides.get(i).getPosX()); 
				asteroide.setPosY(Asteroides.get(i).getPosY()); 
				asteroide.setIncX(Math.random()*7-2-tam); 
				asteroide.setIncY(Math.random()*7-2-tam); 
				asteroide.setAngulo((int)(Math.random()*360)); 
				asteroide.setRotacion((int)(Math.random()*8 - 4)); 
				Asteroides.add(asteroide); 
			} 
		} 
		Asteroides.remove(i);
		misil.setMisilActivo(false);
		if (Asteroides.isEmpty()) {
			terminarPartida();
		}
	}

	private void terminarPartida() {
		Bundle bundle = new Bundle();
		bundle.putInt("puntuacion", puntuacion);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		padre.setResult(Activity.RESULT_OK, intent);
		padre.finish();
	}

	private void ActivaMisil() {
		for (Misiles m : misiles) {

			if (m.getMisilActivo() == false) {

				Grafico misil = m.getGraficoMisil();

				misil.setPosX(nave.getPosX() + nave.getAncho() / 2
						- misil.getAncho() / 2);
				misil.setPosY(nave.getPosY() + nave.getAlto() / 2
						- misil.getAlto() / 2);
				misil.setAngulo(nave.getAngulo());
				misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo()))
						* PASO_VELOCIDAD_MISIL * 2);
				misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo()))
						* PASO_VELOCIDAD_MISIL * 2);
				int distanciaMisil = (int) Math.min(
						this.getWidth() / Math.abs(misil.getIncX()),
						this.getHeight() / Math.abs(misil.getIncY())) - 2;
				m.setDistanciaMisil(distanciaMisil);
				m.setMisilActivo(true);
				break;
			}
		}
	}

	// MANEJO DE LA NAVE CON TECLADO
	@Override
	public boolean onKeyDown(int codigoTecla, KeyEvent evento) {
		super.onKeyDown(codigoTecla, evento);
		// Suponemos que vamos a procesar la pulsación
		boolean procesada = true;
		switch (codigoTecla) {
		case KeyEvent.KEYCODE_DPAD_UP:
			aceleracionNave = +PASO_ACELERACION_NAVE;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			giroNave = -PASO_GIRO_NAVE;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			giroNave = +PASO_GIRO_NAVE;
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_ENTER:
			ActivaMisil();
			break;
		default:
			// Si estamos aquí, no hay pulsación que nos interese
			procesada = false;
			break;
		}
		return procesada;
	}

	@Override
	public boolean onKeyUp(int codigoTecla, KeyEvent evento) {
		super.onKeyUp(codigoTecla, evento);
		// Suponemos que vamos a procesar la pulsación
		boolean procesada = true;
		switch (codigoTecla) {
		case KeyEvent.KEYCODE_DPAD_UP:
			aceleracionNave = 0;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			giroNave = 0;
			break;
		default:
			// Si estamos aquí, no hay pulsación que nos interese
			procesada = false;
			break;
		}
		return procesada;
	}

	// MANEJO DE LA NAVE CON PANTALLA TÁCTIL
	private float mX = 0, mY = 0;
	private boolean disparo = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			disparo = true;
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dy < 6 && dx > 6) {
				giroNave = Math.round((x - mX) / 2);
				disparo = false;
			} else if (dx < 6 && dy > 6) {
				aceleracionNave = Math.round((mY - y) / 25);
				disparo = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			giroNave = 0;
			aceleracionNave = 0;
			if (disparo) {
				ActivaMisil();
			}
			break;
		}
		mX = x;
		mY = y;
		return true;
	}

	// MANEJO DE LA NAVE CON SENSORES
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	private boolean hayValorInicial = false;
	private float valorInicial;

	public void onSensorChanged(SensorEvent event) {
		float valor = event.values[1];
		if (!hayValorInicial) {
			valorInicial = valor;
			hayValorInicial = true;
		}
		giroNave = (int) (valor - valorInicial) / 3;
	}

	public void setCorriendo(boolean corriendo) {
		this.corriendo = corriendo;
	}

	public void setPausa(boolean pausa) {
		this.pausa = pausa;
	}

	public void setPadre(Activity padre) {
		this.padre = padre;
	}

	public class Misiles {
		Grafico misil;
		private boolean misilActivo = false;
		private int distanciaMisil;

		public Misiles(Grafico grafico) {
			misil = grafico;
		}

		public boolean getMisilActivo() {
			return misilActivo;
		}

		public void setMisilActivo(boolean valor) {
			misilActivo = valor;
		}

		public Grafico getGraficoMisil() {
			return misil;
		}

		public int getDistanciaMisil() {
			return distanciaMisil;
		}

		public void setDistanciaMisil(int d) {
			distanciaMisil = d;
		}

		public void decrementarDistanciaMisil() {
			distanciaMisil--;
		}
	}
	
}