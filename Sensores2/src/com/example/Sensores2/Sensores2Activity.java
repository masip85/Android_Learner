package com.example.Sensores2;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Sensores2Activity extends Activity implements SensorEventListener {
	private LinearLayout mLinearLayout;
	private TextView aTextView[][] = new TextView[20][3];
	private SensorManager mSensorManager;
	private Sensor aSensor[] = new Sensor[20];
	private List<Sensor> listSensors;

	private TextView salida;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mLinearLayout = (LinearLayout) findViewById(R.id.LinearLayout01);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		listSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

		int n = 0;
		for (Sensor sensor : listSensors) {
			aSensor[n] = sensor;
			TextView mTextView = new TextView(this);
			mTextView.setText(sensor.getName());
			mLinearLayout.addView(mTextView);
			LinearLayout nLinearLayout = new LinearLayout(this);
			mLinearLayout.addView(nLinearLayout);
			for (int i = 0; i < 3; i++) {
				aTextView[n][i] = new TextView(this);
				aTextView[n][i].setText("?");
				aTextView[n][i].setWidth(87);
			}
			TextView xTextView = new TextView(this);
			xTextView.setText(" X: ");
			nLinearLayout.addView(xTextView);
			nLinearLayout.addView(aTextView[n][0]);
			TextView yTextView = new TextView(this);
			yTextView.setText(" Y: ");
			nLinearLayout.addView(yTextView);
			nLinearLayout.addView(aTextView[n][1]);
			TextView zTextView = new TextView(this);
			zTextView.setText(" Z: ");
			nLinearLayout.addView(zTextView);
			nLinearLayout.addView(aTextView[n][2]);
			mSensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_NORMAL);
			n++;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		synchronized (this) {
			int n = 0;

			for (Sensor sensor : listSensors) {
				if (event.sensor == sensor) {
					for (int i = 0; i < event.values.length; i++) {
						aTextView[n][i]
								.setText(Float.toString(event.values[i]));
					}

				}
				n++;
			}
		}

	}

}