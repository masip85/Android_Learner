package org.example.asteroides;

import java.util.List;
import java.util.Vector;

import android.content.Context;
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
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;




public class VistaJuego extends View implements SensorEventListener {

	// MULTIMEDIA
	SoundPool soundPool;
	int idDisparo, idExplosion;

	// MISILES
	private Vector<Misiles> misiles;
	private static int PASO_VELOCIDAD_MISIL = 12;
	private int NUMERO_MISILES = 15;

	// SENSORES
	private boolean hayValorInicial = false;
	private float valorInicial;

	private boolean hayAcelInicial = false;
	private float AcelxInicial;
	private float AcelyInicial;

	public int x, y;

	private float mX, mY = 0;
	private boolean disparo = false;

	// THREAD Y TIEMPO
	private ThreadJuego thread = new ThreadJuego();
	private static int PERIODO_PROCESO = 50; // ms
	private long ultimoProceso = 0;

	// //// ASTEROIDES //////
	private Vector<Grafico> Asteroides; // Vector con los Asteroides
	private int numAsteroides = 5; // Número inicial de asteroides
	private int numFragmentos = 3; // Fragmentos en que se divide

	// //// NAVE //////
	private Grafico nave; // Gráfico de la nave
	private int giroNave; // Incremento de dirección
	private float aceleracionNave; // aumento de velocidad

	// Incremento estándar de giro y aceleración
	private static final int PASO_GIRO_NAVE = 5;
	private static final float PASO_ACELERACION_NAVE = 0.5f;

	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);

		Drawable drawableNave, drawableAsteroide, drawableMisil;
		SharedPreferences pref = context.getSharedPreferences(
				"org.example.asteroides_preferences", Context.MODE_PRIVATE);

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
			ShapeDrawable dAsteroide = new ShapeDrawable(new PathShape(
					pathAsteroide, 1, 1));
			dAsteroide.getPaint().setColor(Color.WHITE);
			dAsteroide.getPaint().setStyle(Style.STROKE);
			dAsteroide.setIntrinsicWidth(50);
			dAsteroide.setIntrinsicHeight(50);
			drawableAsteroide = dAsteroide;

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

			// MISILES
			ShapeDrawable dMisil = new ShapeDrawable(new RectShape());
			dMisil.getPaint().setColor(Color.WHITE);
			dMisil.getPaint().setStyle(Style.STROKE);
			dMisil.setIntrinsicWidth(15);
			dMisil.setIntrinsicHeight(3);
			drawableMisil = dMisil;
		} else {
			drawableAsteroide = context.getResources().getDrawable(
					R.drawable.asteroide1);
			drawableNave = context.getResources().getDrawable(R.drawable.nave);
			drawableMisil = context.getResources().getDrawable(
					R.drawable.misil1);
		}

		nave = new Grafico(this, drawableNave);
		Asteroides = new Vector<Grafico>();

		for (int i = 0; i < numAsteroides; i++) {
			Grafico asteroide = new Grafico(this, drawableAsteroide);
			asteroide.setIncY(Math.random() * 4 - 2);
			asteroide.setIncX(Math.random() * 4 - 2);
			asteroide.setAngulo((int) (Math.random() * 360));
			asteroide.setRotacion((int) (Math.random() * 8 - 4));
			Asteroides.add(asteroide);
		}

		// SENSORES
		SensorManager mSensorManager = (SensorManager) context
				.getSystemService(context.SENSOR_SERVICE);
		List<Sensor> listSensors = mSensorManager
				.getSensorList(Sensor.TYPE_ALL);
		if (!listSensors.isEmpty()) {
			listSensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
			if (!listSensors.isEmpty()) {
				Sensor orientationSensor = listSensors.get(0);
				mSensorManager.registerListener(this, orientationSensor,
						SensorManager.SENSOR_DELAY_UI);
			}
			listSensors = mSensorManager
					.getSensorList(Sensor.TYPE_ACCELEROMETER);
			if (!listSensors.isEmpty()) {
				Sensor acelerometerSensor = listSensors.get(0);
				mSensorManager.registerListener(this, acelerometerSensor,
						SensorManager.SENSOR_DELAY_UI);
			}
		}

		misiles = new Vector<Misiles>();
		for (int i = 0; i < NUMERO_MISILES; i++) {
			Grafico misil = new Grafico(this, drawableMisil);
			misiles.add(new Misiles(misil));
		}

		// MULTIMEDIA
		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		idDisparo = soundPool.load(context, R.raw.disparo, 0);
		idExplosion = soundPool.load(context, R.raw.explosion, 0);

	}

	@Override
	protected void onSizeChanged(int ancho, int alto, int ancho_anter,
			int alto_anter) {

		super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
		// Una vez que conocemos nuestro ancho y alto.
		for (Grafico asteroide : Asteroides) {
			do {
				asteroide.setPosX(Math.random()
						* (ancho - asteroide.getAncho()));
				asteroide.setPosY(Math.random() * (alto - asteroide.getAlto()));
			} while (asteroide.distancia(nave) < (ancho + alto) / 5);
		}
		nave.setPosX((double) (ancho / 2));
		nave.setPosY((double) (alto / 2));

		thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (Grafico asteroide : Asteroides) {
			asteroide.dibujaGrafico(canvas);
			nave.dibujaGrafico(canvas);
		}
		for (Misiles misil : misiles) {
			if (misil.getMisilActivo()) {
				misil.getGraficoMisil().dibujaGrafico(canvas);
			}
		}
	}

	protected void actualizaFisica() {

		long ahora = System.currentTimeMillis();

		// No hagas nada si el período de proceso no se ha cumplido.
		if (ultimoProceso + PERIODO_PROCESO > ahora) {
			return;
		}

		// Para una ejecución en tiempo real calculamos retardo
		double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;

		// Actualizamos posición nave
		nave.setAngulo((int) (nave.getAngulo() + giroNave * retardo));
		double nIncX = nave.getIncX() + aceleracionNave
				* Math.cos(Math.toRadians(nave.getAngulo())) * retardo;
		double nIncY = nave.getIncY() + aceleracionNave
				* Math.sin(Math.toRadians(nave.getAngulo())) * retardo;

		// Actualizamos si el módulo de la velocidad no excede el máximo
		if (Grafico.distanciaE(0, 0, nIncX, nIncY) <= Grafico.getMaxVelocidad()) {
			nave.setIncX(nIncX);
			nave.setIncY(nIncY);
			
			

		}
		// Actualizamos posiciones X e Y
		nave.incrementaPos();
		for (Grafico asteroide : Asteroides) {
			asteroide.incrementaPos();
			ultimoProceso = ahora;
		}

		// MISIL
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

	}

	private void destruyeAsteroide(int i, Misiles misil) {
		Asteroides.remove(i);
		misil.setMisilActivo(false);
		soundPool.play(idExplosion, 1, 1, 0, 0, 1);
	}

	private void ActivaMisil() {

		for (Misiles m : misiles) {

			if (m.getMisilActivo() == false) {
				soundPool.play(idDisparo, 1, 1, 1, 0, 1);

				Grafico misil = m.getGraficoMisil();

				misil.setPosX(nave.getPosX() + nave.getAncho() / 2
						- misil.getAncho() / 2);
				misil.setPosY(nave.getPosY() + nave.getAlto() / 2
						- misil.getAlto() / 2);
				misil.setAngulo(nave.getAngulo());
				misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo()))
						* PASO_VELOCIDAD_MISIL);
				misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo()))
						* PASO_VELOCIDAD_MISIL);
				int distanciaMisil = (int) Math.min(
						this.getWidth() / Math.abs(misil.getIncX()),
						this.getHeight() / Math.abs(misil.getIncY())) - 2;
				m.setDistanciaMisil(distanciaMisil);
				m.setMisilActivo(true);

				break;
			}
		}
	}

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

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ORIENTATION:
			float valor = event.values[1];
			if (!hayValorInicial) {
				valorInicial = valor;
				hayValorInicial = true;
			}
			giroNave = (int) (valor - valorInicial) / 3;
			break;

		case Sensor.TYPE_ACCELEROMETER:
			double aux = 0.1;

			float Acelx = (float)event.values[1];
			// values[2] can be -90 to 90
			float Acely = (float)event.values[2];
			float Acelz = (float)event.values[3];
		
				aceleracionNave=(float)Math.sqrt(Math.pow(Acelx, 2)+Math.pow(Acely, 2)+Math.pow(Acelz, 2));
			break;
		}
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

	public class ThreadJuego extends Thread {

		private boolean pausa, corriendo;

		public synchronized void pausar() {
			pausa = true;
		}

		public synchronized void reanudar() {
			pausa = false;
			notify();
		}

		public void detener() {
			corriendo = false;
			if (pausa)
				reanudar();
		}

		@Override
		public void run() {
			corriendo=true;
			while (corriendo) {
				actualizaFisica();
				synchronized (this){
					while(pausa){
						try{
							wait();
						}catch(Exception e){}
					}
					}
			}
		}

	}

	public ThreadJuego getThread() {
		return thread;
	}

}