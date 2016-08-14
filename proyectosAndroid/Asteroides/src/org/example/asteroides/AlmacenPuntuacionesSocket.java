package org.example.asteroides;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import android.app.Activity;
import android.util.Log;

public class AlmacenPuntuacionesSocket implements AlmacenPuntuaciones {

	private Activity activity;
	
	public AlmacenPuntuacionesSocket(Activity activity) {
		this.activity = activity;
	}

	public void guardarPuntuacion(int puntos, String nombre, long fecha) {
		try{
			Socket s = new Socket("158.42.146.127", 1234);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
			salida.println(puntos + " " + nombre);
			String respuesta = entrada.readLine();
			if(!respuesta.equals("OK")) {
				Log.e("Asteroides", "Error: respuesta de servidor incorrecta");
			}
			s.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.toString(), e);
		}
	}

	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();
		try{
			Socket s = new Socket("158.42.146.127", 1234);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
			salida.println("PUNTUACIONES");
			int n = 0;
			String respuesta;
			do {
				respuesta = entrada.readLine();
				if(respuesta != null) {
					result.add(respuesta);
					n++;
				}
			} while(n<cantidad && respuesta != null);
			s.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.toString(), e);
		}
		return result;
	}
}
