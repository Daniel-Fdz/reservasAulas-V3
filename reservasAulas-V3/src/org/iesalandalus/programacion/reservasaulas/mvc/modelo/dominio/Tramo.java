package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

public enum Tramo {

	MANANA("Ma�ana"), TARDE("Tarde");
	
	private String cadenaAMostrar;
	
	// Constructor del enum que asigna la cadena a mostrar
	private Tramo(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	// M�todo toString que devuelve la cadena
	@Override
	public String toString() {
		return cadenaAMostrar;
	}
}
