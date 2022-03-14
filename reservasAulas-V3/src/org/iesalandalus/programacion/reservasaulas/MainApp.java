package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;

public class MainApp {

	public static void main(String[] args) {
		/* Implementamos la funcionalidad de los ficheros para poder llevar a cabo
		 * el cometido de esta tarea. No obstante, dejo la línea de la fuente de
		 * datos de la memoria comentada por si quisiese hacerse uso de esta.*/ 
		IModelo modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		//IModelo modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crear());
		IVista vista = new Vista();
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}

}
