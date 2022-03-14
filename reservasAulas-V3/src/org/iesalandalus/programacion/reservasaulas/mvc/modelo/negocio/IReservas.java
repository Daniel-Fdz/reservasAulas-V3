package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

// Interfaz para la clase Reservas
public interface IReservas {

	List<Reserva> getReservas();
	
	void comenzar();
	
	void terminar();
	
	int getNumReservas();
	
	void insertar(Reserva reserva) throws OperationNotSupportedException;
	
	Reserva buscar(Reserva reserva);
	
	void borrar(Reserva reserva) throws OperationNotSupportedException;
	
	List<String> representar();
	
	List<Reserva> getReservasProfesor(Profesor profesor);
	
	List<Reserva> getReservasAula(Aula aula);
	
	List<Reserva> getReservasPermanencia(Permanencia permanencia);
	
	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);
}
