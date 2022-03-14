package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {
	
	// Establecemos la constante que contendr� la ruta de nuestro fichero as� como la lista de profesores
	private static final String NOMBRE_FICHERO_PROFESORES = "datos/FichProfesores.dat";
	private List<Profesor> coleccionProfesores;
	
	// Constructor por defecto que crea un ArrayList
	public Profesores() {
		coleccionProfesores = new ArrayList<Profesor>();
	}
	
	// Constructor copia
	public Profesores(IProfesores profesores) {
		if(profesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		} else {
			setProfesores(profesores);
		}
	}
	
	// M�todo que dar� comienzo a la lectura
	@Override
	public void comenzar() {
		leer();
	}
	
	// Implementaci�n del m�todo que leer� un archivo
	private void leer() {
		File archivo = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
		
			Profesor profesor = null;
			
			do {
				profesor = (Profesor) entrada.readObject();
				insertar(profesor);
			} while(profesor != null);
		} catch(FileNotFoundException e) {
			System.out.println("ERROR: Fichero no encontrado.");
		} catch(EOFException e) {
			System.out.println("Fichero le�do correctamente.");
		} catch(IOException e) {
			System.out.println("Error de I/O.");
		} catch(ClassNotFoundException e) {
			System.out.println("ERROR: No se ha encontrado la clase.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	// M�todo que dar� comienzo a la escritura
	 @Override
	public void terminar() {
		escribir();
	}
	
	// Implementaci�n del m�todo que escribir� un archivo
	private void escribir() {
		File archivo = new File(NOMBRE_FICHERO_PROFESORES);
		
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
			for (Profesor profesor : coleccionProfesores) {
				salida.writeObject(profesor);
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo crear el fichero.");
		} catch (IOException e) {
			System.out.println("Error de I/O.");
		}
	}
	
	// M�todo que establece los profesores
	private void setProfesores(IProfesores profesores) {
		if(profesores == null) {
			throw new NullPointerException("ERROR: No se pueden establecer profesores nulos.");
		} else {
			coleccionProfesores = profesores.getProfesores();
		}
	}
	
	// Implementaci�n del m�todo de copia profunda
	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		List<Profesor> copia = new ArrayList<>();
		Iterator<Profesor> it = profesores.iterator();
		
		while(it.hasNext()) {
			copia.add(new Profesor(it.next()));
		}
		return copia;
	}
	
	// M�todo que devuelve una copia profunda de los profesores
	public List<Profesor> getProfesores() {
		List<Profesor> lista = copiaProfundaProfesores(coleccionProfesores);
		lista.sort(Comparator.comparing(Profesor::getCorreo));
		return lista;
	}
	
	// Devuelve el n�mero de profesores
	public int getNumProfesores() {
		return coleccionProfesores.size();
	}
	
	// Inserta un profesor pasado por par�metro
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		
		if(coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		}
		
		coleccionProfesores.add(new Profesor(profesor));
	}
	
	// Busca el profesor pasado por par�metro y lo devuelve
	public Profesor buscar(Profesor profesor) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		} else if(coleccionProfesores.isEmpty()) {
			return null;
		}
		
		int indice = coleccionProfesores.indexOf(profesor);
		
		if(coleccionProfesores.contains(profesor)) {
		  	return new Profesor(coleccionProfesores.get(indice));
		} else {
			return null;
		}
	}
	
	// Elimina un profesor
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		} else if(buscar(profesor) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ning�n profesor con ese correo.");
		} else {
			coleccionProfesores.remove(profesor);
		}
	}
	
	// Representaci�n de los profesores
	public List<String> representar() {
		List<String> profesores = new ArrayList<>();
		Iterator<Profesor> it = coleccionProfesores.iterator();
		
		while(it.hasNext()) {
			profesores.add(it.next().toString());
		}
		return profesores;
	}
}

