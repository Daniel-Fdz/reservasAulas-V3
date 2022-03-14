package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

// Interfaz para la clase profesores
public interface IProfesores {

	List<Profesor> getProfesores();

	void comenzar();
	
	void terminar();
	
	int getNumProfesores();
	
	void insertar(Profesor profesor) throws OperationNotSupportedException;
	
	Profesor buscar(Profesor profesor);
	
	void borrar(Profesor profesor) throws OperationNotSupportedException;
	
	List<String> representar();
}
