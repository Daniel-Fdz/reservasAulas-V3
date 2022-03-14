package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

public class PermanenciaPorTramo extends Permanencia {

	/* Añadimos el ID único de la clase para poder serializar y
	 * deserializar los objetos. Asimismo, establecemos los campos de la clase
	 */
	private static final long serialVersionUID = 3408235890433306505L;
	private static final int PUNTOS = 10;
	private Tramo tramo;
	
	// Constructor que acepta un día y un tramo como parámetro
	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		super(dia);
		setTramo(tramo);
	}
	
	// Constructor copia
	public PermanenciaPorTramo(PermanenciaPorTramo permanenciaPorTramo) {
		super(permanenciaPorTramo);
		setTramo(permanenciaPorTramo.getTramo());
	}
	
	// Devuelve el tramo
	public Tramo getTramo() {
		return tramo;
	}

	// Establece el tramo
	private void setTramo(Tramo tramo) {
		if(tramo == null) {
			throw new NullPointerException("ERROR: El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo = tramo;
	}
	
	// Devuelve los puntos
	@Override
	public int getPuntos() {
		return PUNTOS;
	}
	
	// Métodos hashCode e equals
	@Override
	public int hashCode() {
		return Objects.hash(getDia(), tramo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		return Objects.equals(getDia(), other.getDia()) && tramo == other.tramo;
	}

	// Muestra el toString del padre junto con el tramo
	@Override
	public String toString() {
		return super.toString() + ", tramo=" + tramo;
	}
}

