package org.example.gestures;

import java.util.ArrayList;
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.TextView;

public class Gestures extends Activity implements OnGesturePerformedListener {

	private GestureLibrary libreria;
	private TextView salida;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Se apunta a la libreria
		libreria = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!libreria.load()) {
			finish();
		}
		GestureOverlayView gesturesView = (GestureOverlayView) findViewById(R.id.gestures);
		gesturesView.addOnGesturePerformedListener(this);
		salida = (TextView) findViewById(R.id.salida);
	}

	public void onGesturePerformed(GestureOverlayView ov, Gesture gesture) {

		// Devuelve las gestures que mas se parezcan
		ArrayList<Prediction> predictions = libreria.recognize(gesture);
		salida.setText("");

		for (Prediction prediction : predictions) {
			salida.append(prediction.name + " " + prediction.score + "\n");
		}
	}
}