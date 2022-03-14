package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class Controlador implements IControlador {
	// Preparamos los campos de la clase para el modelo y la vista 
	private IModelo modelo;
	private IVista vista;
	
	// Constructor que acepta como parámetros un modelo y una vista
	public Controlador(IModelo modelo, IVista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
	}
	
	// Establece el comienzo de las operaciones del modelo y de la vista
	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	}
	
	// Establece la finalización de las operaciones del modelo
	public void terminar() {
		modelo.terminar();
		System.exit(0);
	}
	
	// Inserta un aula pasada por parámetro
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		modelo.insertarAula(aula);
	}
	
	// Inserta un profesor pasado por parámetro
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.insertarProfesor(profesor);
	}
	
	// Elimina un aula pasada por parámetro
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		modelo.borrarAula(aula);
	}
	
	// Elimina un profesor pasado por parámetro
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.borrarProfesor(profesor);
	}
	
	// Busca un aula pasada por parámetro
	public Aula buscarAula(Aula aula) {
		return modelo.buscarAula(aula);
	}
	
	// Busca un profesor pasado por parámetro
	public Profesor buscarProfesor(Profesor profesor) {
		return modelo.buscarProfesor(profesor);
	}
	
	// Devuelve una representación de las aulas
	public List<String> representarAulas() {
		return modelo.representarAulas();
	}
	
	// Devuelve una representación de los profesores
	public List<String> representarProfesores() {
		return modelo.representarProfesores();
	}
	
	// Devuelve una representación de las reservas
	public List<String> representarReservas() {
		return modelo.representarReservas();
	}
	
	// Nos permite realizar una reserva que le pasamos por parámetro
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}
	
	// Permite anular una reserva que le pasamos por parámetro
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.anularReserva(reserva);
	}
	
	// Devuelve la lista de reservas por aula pasada por parámetro
	public List<Reserva> getReservasAula(Aula aula) {
		return modelo.getReservasAula(aula);
	}
	
	// Devuelve la lista de reservas por profesor pasado por parámetro
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return modelo.getReservasProfesor(profesor);
	}
	
	// Devuelve la lista de reservas por permanencia pasada por parámetro
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservasPermanencia(permanencia);
	}
	
	// Devuelve la disponibilidad de las aulas. Se le pasa por parámetro el aula y la permanencia
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanecia) {
		return modelo.consultarDisponibilidad(aula, permanecia);
	}
}

