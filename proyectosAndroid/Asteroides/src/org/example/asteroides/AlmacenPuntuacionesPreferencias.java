package org.example.asteroides;

import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;

public class AlmacenPuntuacionesPreferencias implements AlmacenPuntuaciones {

	private static String PREFERENCIAS = "puntuaciones";
	private Context context;

	public AlmacenPuntuacionesPreferencias(Context context) {
		this.context = context;
	}

	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		SharedPreferences preferencias = context.getSharedPreferences(
				PREFERENCIAS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferencias.edit();

		for (int n = 9; n >= 1; n--) {
			editor.putString("puntuacion" + n,
					preferencias.getString("puntuacion" + (n - 1), ""));
		}
		editor.putString("puntuacion0", puntos + " " + nombre);
		editor.commit();
	}

	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCIAS,
				Context.MODE_PRIVATE);
		for(int n = 9; n >= 1; n--){
			String s = pref.getString("puntuacion" + n, "");
			if (s != "")
				result.add(s);
		}
		return result;
	}
}
