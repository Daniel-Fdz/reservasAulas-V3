package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
// Interfaz para la clase Aulas
public interface IAulas {

	List<Aula> getAulas();
	
	void comenzar();
	
	void terminar();
	
	int getNumAulas();
	
	void insertar(Aula aula) throws OperationNotSupportedException;
	
	Aula buscar(Aula aula);
	
	void borrar(Aula aula) throws OperationNotSupportedException;
	
	List<String> representar();
}
