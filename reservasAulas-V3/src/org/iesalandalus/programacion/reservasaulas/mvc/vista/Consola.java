package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	// Como no se va a instanciar ning�n objeto de esta clase, establecemos el m�todo a privado y lo dejamos vac�o
	private Consola() {
		
	}
	
	// Muestra el men� de opciones por consola
	public static void mostrarMenu() {
		for(Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	// Muestra la cabecera de cada acci�n a realizar
	public static void mostrarCabecera(String cadena) {
		String separador =  "";
		
		for(int i = 0; i < cadena.length(); i++) {
			separador += "+";
		}
		
		System.out.println("");
		System.out.println(separador);
		System.out.println(cadena);
		System.out.println(separador);
		System.out.println("");
	}
	
	// Permite elegir la opci�n que el usuario prefiera
	public static int elegirOpcion() {
		int ordinal = 0;
		do {
			System.out.println("Por favor, elija una opci�n: ");
			ordinal = Entrada.entero();
		} while(!Opcion.esOrdinalValido(ordinal));
		return ordinal;
	}

	// Lee un aula y devuelve una instancia de esta
	public static Aula leerAula() {
		return new Aula(leerNombreAula(), leerNumeroPuestos());
	}
	
	// Pide el n�mero de puestos de cada aula y los devuelve
	public static int leerNumeroPuestos() {
		int puestos = 0;
		
		do {
			System.out.print("Introduzca el n�mero de puestos del aula (10 - 60): ");
			puestos = Entrada.entero();
		} while(puestos < 10 || puestos > 60);
		return puestos;
	}
	
	// Devuelve los datos de un aula ficticia
	public static Aula leerAulaFicticia() {
		Aula aula = Aula.getAulaFicticia(leerNombreAula());
		return aula;
	}
	
	// Permite introducir el nombre del aula por consola
	public static String leerNombreAula() {
		System.out.print("Introduzca el nombre del aula: ");
		return Entrada.cadena();
	}
	
	// Permite introducir los datos del profesor y llama a un constructor u otro en funci�n de los datos introducidos
	public static Profesor leerProfesor() throws IllegalArgumentException {
		String nombre = leerNombreProfesor();
		System.out.print("Introduzca el correo del profesor: ");
		String correo = Entrada.cadena();
		System.out.print("Introduzca el tel�fono del profesor: ");
		String telefono = Entrada.cadena();
		if(telefono == null || telefono == "") {
			return new Profesor(nombre, correo);
		} else {
			return new Profesor(nombre, correo, telefono);
		}
	}
	
	// Pide el nombre del profesor y lo deluelve
	public static String leerNombreProfesor() {
		System.out.print("Introduzca el nombre del profesor: ");
		return Entrada.cadena();
	}
	
	// Devuelve los datos de un profesor ficticio
	public static Profesor leerProfesorFicticio() {
		System.out.print("Introduzca el correo del profesor: ");
		String correo = Entrada.cadena();
		Profesor profesor = Profesor.getProfesorFicticio(correo);
		return profesor;
	}
	
	// Pide un tramo y lo devuelve
	public static Tramo leerTramo() {
		int eligeOpcion = 0;
		do {
			System.out.println("Introduzca el tramo (\"1\" para la ma�ana o \"2\" para la tarde): ");
			eligeOpcion = Entrada.entero();
		} while(eligeOpcion != 1 && eligeOpcion != 2);
		
		if(eligeOpcion == 1) {
			return Tramo.MANANA;
		} else {
			return Tramo.TARDE;
		}
		
	}
	
	// Pide una fecha y la devuelve
	public static LocalDate leerDia() {
		boolean fechaCorrecta = false;
		LocalDate fecha = null;
		
		do {
			try {
				System.out.print("Introduzca la fecha (dd/mm/aaaa): ");
				fecha = LocalDate.parse(Entrada.cadena(), FORMATO_DIA);
				fechaCorrecta = true;
			} catch(DateTimeParseException e) {
				System.out.println(e.getMessage());
			}
		} while(!fechaCorrecta);
		
		return fecha;
	}
	
	// Permite elegir la permanencia
	public static int elegirPermanencia() {
		int tramo = 0;
		do {
			System.out.println("Por favor, elija una permanencia por tramo: (1 - Completo | 2 - Horario)");
			tramo = Entrada.entero();
		} while(tramo != 1 && tramo != 2);
		return tramo;
	}
	
	// Dependiendo de la permanencia elegida, devolver� una por tramo u hora
	public static Permanencia leerPermanencia() {
		int eleccionPermanencia = elegirPermanencia();
		Permanencia permanencia = null;
		
		if(eleccionPermanencia == 1) {
			permanencia = new PermanenciaPorTramo(leerDia(), leerTramo());
		} else {
			permanencia = new PermanenciaPorHora(leerDia(), leerHora());
		}
		return permanencia;
	}
	
	// Devuelve una hora correcta para las reservas
	private static LocalTime leerHora() {
		boolean horaCorrecta = false;
		LocalTime hora = null;
		
		do {
			try {
				System.out.print("Introduzca la hora (8 - 22): ");
				hora = LocalTime.of(Entrada.entero(), 0);
				horaCorrecta = true;
			} catch(DateTimeException e) {
				System.out.println(e.getMessage());
			}
		} while(!horaCorrecta);
		
		return hora;
	}
	
	// Devuelve una reserva
	public static Reserva leerReserva() {
		Consola.mostrarCabecera("Leer una reserva");
		boolean reservaCorrecta = false;
		Reserva reserva = null;
		
		do {
			try {
				reserva = new Reserva(leerProfesorFicticio(), leerAulaFicticia(), leerPermanencia());
				reservaCorrecta = true;
			} catch(NullPointerException | IllegalArgumentException | DateTimeException e) {
				System.out.println(e.getMessage());
			}
		} while(!reservaCorrecta);
		
		return reserva;
	}
	
	// Devuelve una reserva ficticia
	public static Reserva leerReservaFicticia() {
		boolean reservaCorrecta = false;
		Reserva reserva = null;
		
		do {
			try {
				reserva = new Reserva(new Profesor("Laura", "laura@profe.com", "600600600"), leerAulaFicticia(), leerPermanencia());
				reservaCorrecta = true;
			} catch(NullPointerException | IllegalArgumentException | DateTimeException e) {
				System.out.println(e.getMessage());
			}
		} while(!reservaCorrecta);
		
		return reserva;
	}
}

