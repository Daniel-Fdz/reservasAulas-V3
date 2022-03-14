package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Profesor implements Serializable {
	
	/* A�adimos el ID �nico de la clase para poder serializar y
	 * deserializar los objetos. Asimismo, establecemos los campos de la clase
	 */
	private static final long serialVersionUID = -8554784133199966535L;
	private final static String ER_TELEFONO = "[69]\\d{8}";
	private final static String ER_CORREO = "^\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}$";
	private String nombre;
	private String correo;
	private String telefono;
	
	// Constructor que acepta un nombre y un correo de un profesor como par�metros
	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}
	
	// Constructor que acepta un nombre, un correo y un tel�fono de un profesor como par�metros
	public Profesor(String nombre, String correo, String telefono) {
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);
	}
	
	// Constructor copia
	public Profesor(Profesor profesor) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		} else {
			setNombre(profesor.getNombre());
			setCorreo(profesor.getCorreo());
			setTelefono(profesor.getTelefono());
		}
	}

	// Devuelve el nombre del profesor
	public String getNombre() {
		return nombre;
	}

	// Establece el nombre del profesor
	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		} else if(nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vac�o.");
		} else {
			this.nombre = formateaNombre(nombre);
		}
	}
	
	// Devuelve el nombre formateado para poder mostrarlo correctamente
	private String formateaNombre(String nombre) {
		if(nombre == null || nombre.equals("")) {
			throw new NullPointerException("ERROR: El nombre no puede estar vac�o.");
		} else {
			boolean hayEspacio = true;
			char[] caracteres = nombre.toCharArray();
			
			for(int i = 0; i < caracteres.length; i++) {
				if(Character.isLetter(caracteres[i])) {
					if(hayEspacio) {
						caracteres[i] = Character.toUpperCase(caracteres[i]);
						hayEspacio = false;
					}
				} else {
					hayEspacio = true;
				}
			}
			return nombre = String.valueOf(caracteres);
		}
	}

	// Devuelve el correo del profesor
	public String getCorreo() {
		return correo;
	}

	// Establece el correo del profesor
	public void setCorreo(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		} else if(!correo.matches(ER_CORREO) || correo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es v�lido.");
		} else {
			this.correo = correo;
		}
	}

	// Devuelve el tel�fono del profesor
	public String getTelefono() {
		return telefono;
	}

	//Establece el tel�fono del profesor
	public void setTelefono(String telefono) {
		if(telefono != null && !telefono.matches(ER_TELEFONO) || telefono.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El tel�fono del profesor no es v�lido.");
		} else {
			this.telefono = telefono;
		}
	}

	// Devuelve un profesor ficticio. Se le pasa un correo como par�metro
	public static Profesor getProfesorFicticio(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		} else if(!correo.matches(ER_CORREO) || correo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es v�lido.");
		} else {
			return new Profesor("Laura", correo, "666555444");
		}
	}
	
	// M�todos hashCode/equals
	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(correo, other.correo);
	}

	// M�todo toString que muestra el tel�fono, el nombre y el correo del profesor.
	// En caso de no estar el tel�fono, no se mostrar�.
	@Override
	public String toString() {
		String ponerTelefono = (telefono == null) ? "" : ", tel�fono=" + telefono; 
		return "nombre=" + nombre + ", correo=" + correo + ponerTelefono;
	}
}
