package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Modelo implements IModelo {
	// Establecemos los campos de la clase para los profesores, aulas y reservas
	private IProfesores profesores;
	private IAulas aulas;
	private IReservas reservas;
	
	/* Constructor de la clase a la que se le pasa una fuente de datos para
	 * poder elegir entre los modos de ejecución (fichero - memoria)
	 */
	public Modelo(IFuenteDatos fuenteDatos) {
		profesores = fuenteDatos.crearProfesores();
		aulas = fuenteDatos.crearAulas();
		reservas = fuenteDatos.crearReservas();
	}
	
	// Comenzamos las operaciones de lectura
	@Override
	public void comenzar() {
		profesores.comenzar();
		aulas.comenzar();
		reservas.comenzar();
	}
	
	// Comenzamos las operaciones de escritura
	@Override
	public void terminar() {
		profesores.terminar();
		aulas.terminar();
		reservas.terminar();
	}
	
	// Devuelve una lista con las aulas
	@Override
	public List<Aula> getAulas() {
		return aulas.getAulas();
	}
	
	// Devuelve el número de aulas
	public int getNumAulas() {
		return aulas.getNumAulas();
	}
	
	// Devuelve una lista con la representación de las aulas
	public List<String> representarAulas() {
		return aulas.representar();
	}
	
	// Busca y devuelve un aula pasada por parámetro
	public Aula buscarAula(Aula aula) {
		return aulas.buscar(aula);
	}
	
	// Inserta un aula pasada por parámetro
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		aulas.insertar(aula);
	}
	
	// Elimina un aula pasada por parámetro
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		aulas.borrar(aula);
	}
	
	// Devuelve una lista de profesores
	public List<Profesor> getProfesores() {
		return profesores.getProfesores();
	}
	
	// Devuelve el número de profesores
	public int getNumProfesores() {
		return profesores.getNumProfesores();
	}
	
	// Devuelve una lista con la representación de los profesores
	public List<String> representarProfesores() {
		return profesores.representar();
	}
	
	// Busca y devuelve un profesor pasado por parámetro
	public Profesor buscarProfesor(Profesor profesor) {
		return profesores.buscar(profesor);
	}
	
	// Inserta un profesor pasado por parámetro
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}
	
	// Elimina un profesor pasado por parámetro
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.borrar(profesor);
	}
	
	// Devuelve una lista con las reservas
	public List<Reserva> getReservas() {
		return reservas.getReservas();
	}
	
	// Devuelve el número de reservas
	public int getNumReservas() {
		return reservas.getNumReservas();
	}
	
	// Devuelve una lista con la representación de las reservas
	public List<String> representarReservas() {
		return reservas.representar();
	}
	
	// Busca y devuelve una reserva pasada por parámetro
	public Reserva buscarReserva(Reserva reserva) {
		return reservas.buscar(reserva);
	}
	
	// Inserta una reserva pasada por parámetro
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.insertar(reserva);
	}
	
	// Elimina una reserva pasada por parámetro
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.borrar(reserva);
	}
	
	// Devuelve una lista de reservas por aula
	public List<Reserva> getReservasAula(Aula aula) {
		return reservas.getReservasAula(aula);
	}
	
	// Devuelve una lista de reservas por profesor
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return reservas.getReservasProfesor(profesor);
	}
	
	// Devuelve una lista de reservas por permanencia
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return reservas.getReservasPermanencia(permanencia);
	}
	
	// Devuelve la disponibilidad de las aulas. Se le pasa un aula y una permanenecia como parámetros
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}
}
