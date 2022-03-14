package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

// Indicamos que esta clase será serializable
public class Aula implements Serializable {
	
	/* Añadimos el ID único de la clase para poder serializar y
	 * deserializar los objetos. Asimismo, establecemos los campos de la clase
	 */
	private static final long serialVersionUID = 4057617185008142987L;
	private static final float PUNTOS_POR_PUESTO = 0.5f;
	private static final int MIN_PUESTOS = 10;
	private static final int MAX_PUESTOS = 60;
	private String nombre;
	private int puestos;
	
	// Constructor que acepta un nombre de aula como parámetro
	public Aula(String nombre, int puestos) {
		setNombre(nombre);
		setPuestos(puestos);
	}
	
	// Constructor copia
	public Aula(Aula aula) {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		} else {
			setNombre(aula.getNombre());
			setPuestos(aula.getPuestos());
		}
	}

	// Devuelve el nombre del aula
	public String getNombre() {
		return nombre;
	}

	// Establece el nombre del aula
	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		} else if(nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre del aula no puede estar vacío.");
		} else {
			this.nombre = nombre;
		}
	}

	// Devuelve los puestos del aula
	public int getPuestos() {
		return puestos;
	}
	
	// Establece los puestos que tendrá un aula
	private void setPuestos(int puestos) {
		if(puestos < MIN_PUESTOS || puestos > MAX_PUESTOS) {
			throw new IllegalArgumentException("ERROR: El número de puestos no es correcto.");
		} else {
			this.puestos = puestos;
		}
	}
	
	// Devuelve los puntos por puesto
	public float getPuntos() {
		return PUNTOS_POR_PUESTO * getPuestos();
	}
	
	// Crea y devuelve un aula ficiticia con el nombre pasado por parámetro 
	public static Aula getAulaFicticia(String aula) {
		return new Aula(aula, 30);
	}
	
	// Métodos hashCode/equals
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(nombre, other.nombre);
	}

	// Método toString que nos muestra el nombre del aula así como sus puestos
	@Override
	public String toString() {
		return "nombre=" + nombre + ", puestos=" + puestos;
	}
}
