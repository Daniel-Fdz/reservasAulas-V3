package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;

// Interfaz para la clase Vista
public interface IVista {

	void setControlador(IControlador controlador);
	
	void comenzar();
	
	void salir();
}
