package org.example.asteroides;

import java.util.Vector;

public class AlmacenPuntuacionesArray implements AlmacenPuntuaciones {

	private Vector<String> puntuaciones;

	public AlmacenPuntuacionesArray() {

		puntuaciones = new Vector<String>();
		puntuaciones.add("123001 Vicente Masip");
		puntuaciones.add("123000 Chipy");
		puntuaciones.add("000000 Jose Beltrán");

	}
	
	public void guardarPuntuacion(int puntos,String nombre,long fecha){
		puntuaciones.add(0,puntos+" "+nombre);
	}
	
	public Vector<String> listaPuntuaciones(int cantidad){
		return puntuaciones;
	}

}
