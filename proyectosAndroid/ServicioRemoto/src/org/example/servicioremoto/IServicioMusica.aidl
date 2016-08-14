package org.example.servicioremoto;

interface IServicioMusica {
	String reproduce(in String mensaje);
	void setPosicion(int ms);
	int getPosicion();
}