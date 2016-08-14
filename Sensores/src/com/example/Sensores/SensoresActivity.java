package com.example.Sensores;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensoresActivity extends Activity implements SensorEventListener {
	private TextView salida;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		salida = (TextView) findViewById(R.id.salida);
		SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> listSensors = mSensorManager
				.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : listSensors) {
			log(sensor.getName());
		}

		listSensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (!listSensors.isEmpty()) {
			Sensor orientationSensor = listSensors.get(0);
			mSensorManager.registerListener(this, orientationSensor,
					SensorManager.SENSOR_DELAY_UI);
		}
		listSensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (!listSensors.isEmpty()) {
			Sensor acelerometerSensor = listSensors.get(0);
			mSensorManager.registerListener(this, acelerometerSensor,
					SensorManager.SENSOR_DELAY_UI);
		}
		listSensors = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
		if (!listSensors.isEmpty()) {
			Sensor magneticSensor = listSensors.get(0);
			mSensorManager.registerListener(this, magneticSensor,
					SensorManager.SENSOR_DELAY_UI);
		}
		listSensors = mSensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
		if (!listSensors.isEmpty()) {
			Sensor temperatureSensor = listSensors.get(0);
			mSensorManager.registerListener(this, temperatureSensor,
					SensorManager.SENSOR_DELAY_UI);
		}
	}

	private void log(String string) {
		salida.append(string + "\n");
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	
	public void onSensorChanged(SensorEvent event) {
		// Cada sensor puede provocar que un thread pase por aquí
		// así que sincronizamos el acceso
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ORIENTATION:
				for (int i = 0; i < 3; i++) {
					log("Orientación " + i + ": " + event.values[i]);
				}
				break;
			case Sensor.TYPE_ACCELEROMETER:
				for (int i = 0; i < 3; i++) {
					log("Acelerómetro " + i + ": " + event.values[i]);
				}
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				for (int i = 0; i < 3; i++) {
					log("Magnetismo " + i + ": " + event.values[i]);
				}
				break;
			default:
				for (int i = 0; i < event.values.length; i++) {
					log("Temperatura " + i + ": " + event.values[i]);
				}
			}
		}
	}
}