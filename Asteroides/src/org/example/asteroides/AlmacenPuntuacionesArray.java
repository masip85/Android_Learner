package org.example.asteroides;

import java.util.Vector;

public class AlmacenPuntuacionesArray implements AlmacenPuntuaciones {

	private Vector<String> puntuaciones;

	public AlmacenPuntuacionesArray() {
		puntuaciones = new Vector<String>();
		puntuaciones.add("123000 Pepido Dominguez");
		puntuaciones.add("111000 Pedro Martinez");
		puntuaciones.add("011300 Paco P�rez");
	}
	
	public void guardarPuntuacion(int puntos,String nombre,long fecha){
		puntuaciones.add(0,puntos+" "+nombre);
	}
	
	public Vector<String> listaPuntuaciones(int cantidad){
		return puntuaciones;
	}

}
