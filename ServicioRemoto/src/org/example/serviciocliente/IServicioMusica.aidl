package org.example.serviciocliente;

interface IServicioMusica {
	String reproduce(in String mensaje);
	
	void setPosicion(int ms);
	int getPosicion();

}