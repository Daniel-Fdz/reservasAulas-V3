package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Permanencia implements Serializable {

	/* A�adimos el ID �nico de la clase para poder serializar y
	 * deserializar los objetos. Asimismo, establecemos los campos de la clase
	 */
	private static final long serialVersionUID = -6595137455665251220L;
	private LocalDate dia;
	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	// Constructor que acepta un d�a y un tramo como par�metros
	public Permanencia(LocalDate dia) {
		setDia(dia);
	}
	
	// Constructor copia
	public Permanencia(Permanencia permanencia) {
		if(permanencia == null) {
			throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
		} else {
			setDia(permanencia.getDia());
		}
	}

	// Devuelve el d�a
	public LocalDate getDia() {
		return dia;
	}

	// Establece el d�a
	private void setDia(LocalDate dia) {
		if(dia == null) {
			throw new NullPointerException("ERROR: El d�a de una permanencia no puede ser nulo.");
		} else {
			this.dia = dia;
		}
	}
	
	/* Dejamos listos los m�todos abstractos que heredar�n las clases
	 * PermanenciaPorHora y PermanenciaPorTramo
	 */
	public abstract int getPuntos();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	// M�todo toString que nos muestra el d�a y el tramo
	@Override
	public String toString() {
		return "d�a=" + dia.format(FORMATO_DIA);
	}
}
