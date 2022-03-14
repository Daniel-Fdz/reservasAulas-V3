package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas {

	/* Establecemos la constante que contendr� la ruta de nuestro fichero
	 * as� como la lista de reservas y la constante con el m�ximo de puntos por profesor y mes
	 */
	private List<Reserva> coleccionReservas;
	private static final String NOMBRE_FICHERO_RESERVAS = "datos/FichReservas.dat";
	private static final float MAX_PUNTOS_PROFESOR_MES = 200f;

	// Constructor por defecto que crea un ArrayList
	public Reservas() {
		coleccionReservas = new ArrayList<Reserva>();
	}

	// Constructor copia
	public Reservas(Reservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		} else {
			setReservas(reservas);
		}
	}
	
	// M�todo que dar� comienzo a la lectura
	@Override
	public void comenzar() {
		leer();
	}
	
	// Implementaci�n del m�todo que leer� un archivo
	private void leer() {
		File archivo = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
			Reserva reserva = null;
			
			do {
				reserva = (Reserva) entrada.readObject();
				insertar(reserva);
			} while(reserva != null);
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
		File archivo = new File(NOMBRE_FICHERO_RESERVAS);
		
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {			
			for (Reserva reserva : coleccionReservas) {
				salida.writeObject(reserva);
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo crear el fichero.");
		} catch (IOException e) {
			System.out.println("Error de I/O.");
		}
	}

	// M�todo que establece las reservas
	private void setReservas(Reservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden establecer reservas nulas.");
		} else {
			coleccionReservas = reservas.getReservas();
		}
	}

	// Implementaci�n del m�todo de copia profunda
	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		List<Reserva> copia = new ArrayList<>();
		Iterator<Reserva> it = reservas.iterator();

		while (it.hasNext()) {
			copia.add(new Reserva(it.next()));
		}
		return copia;
	}

	// M�todo que devuelve una copia profunda de las reservas y las ordena
	public List<Reserva> getReservas() {
		List<Reserva> lista = copiaProfundaReservas(coleccionReservas);
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacion = -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());
			}
			return comparacion;
		};
		lista.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia,
				comparadorPermanencia));
		return lista;
	}

	// Devuelve el n�mero de reservas
	public int getNumReservas() {
		return coleccionReservas.size();
	}

	// Inserta una reserva pasada por par�metro
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		} else if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"ERROR: S�lo se pueden hacer reservas para el mes que viene o posteriores.");
		}

		Reserva reservaAula = getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		if (reservaAula != null) {
			if (reservaAula.getPermanencia() instanceof PermanenciaPorHora
					&& reserva.getPermanencia() instanceof PermanenciaPorTramo
					|| reservaAula.getPermanencia() instanceof PermanenciaPorTramo
							&& reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException(
						"ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este d�a.");
			}
		}

		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"ERROR: S�lo se pueden hacer reservas para el mes que viene o posteriores.");
		}

		if (getPuntosGastadosReserva(reserva) + reserva.getPuntos() > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException(
					"ERROR: Esta reserva excede los puntos m�ximos por mes para dicho profesor.");
		}

		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
		}

		coleccionReservas.add(reserva);
	}

	// Comprueba que la reserva pasada como par�metro es una reserva para el mes
	// siguiente o posteriores.
	// En caso afirmativo devolver� true mientras que en caso negativo devolver�
	// false
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: La reserva no puede estar nula.");
		}
		LocalDate mesSiguiente = LocalDate.now().plusMonths(1);
		LocalDate primerDiaMesSiguiente = LocalDate.of(mesSiguiente.getYear(), mesSiguiente.getMonth(), 1);
		return reserva.getPermanencia().getDia().isAfter(primerDiaMesSiguiente.plusDays(-1));
	}

	// Devuelve los puntos que se han gastado por reserva
	private float getPuntosGastadosReserva(Reserva reserva) {
		List<Reserva> reservas = getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia());
		float puntos = 0;
		Iterator<Reserva> it = reservas.iterator();

		while (it.hasNext()) {
			Reserva proxReserva = it.next();
			puntos += proxReserva.getPuntos();
		}
		return puntos;
	}

	// Devuelve una lista de todas la reservas del profesor para el mes pasado por
	// par�metro
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate mes) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}

		if (mes == null) {
			throw new NullPointerException("ERROR: El mes no puede ser nulo.");
		}

		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();

		while (it.hasNext()) {
			Reserva proxReserva = it.next();
			if (proxReserva.getProfesor().equals(profesor)
					&& proxReserva.getPermanencia().getDia().getMonth().equals(mes.getMonth())) {
				reservas.add(new Reserva(proxReserva));
			}
		}
		return reservas;
	}

	// Devuelve una reserva si existe para el aula indicado como par�metro
	private Reserva getReservaAulaDia(Aula aula, LocalDate dia) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}

		if (dia == null) {
			throw new NullPointerException("ERROR: El d�a no puede ser nulo.");
		}

		Reserva reservaDia = null;
		Iterator<Reserva> it = coleccionReservas.iterator();

		while (it.hasNext()) {
			Reserva proxReserva = it.next();
			if (proxReserva.getAula().equals(aula) && proxReserva.getPermanencia().getDia().equals(dia)) {
				reservaDia = new Reserva(proxReserva);
			}
		}
		return reservaDia;
	}

	// Busca la reserva pasada por par�metro y la devuelve
	public Reserva buscar(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}
		if (coleccionReservas.isEmpty()) {
			return null;
		}

		int indice = coleccionReservas.indexOf(reserva);

		if (coleccionReservas.contains(reserva)) {
			return new Reserva(coleccionReservas.get(indice));
		} else {
			return null;
		}
	}

	// Elimina una reserva
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
		} else if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"ERROR: S�lo se pueden anular reservas para el mes que viene o posteriores.");
		} else if (buscar(reserva) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
		} else {

			if (coleccionReservas.contains(reserva)) {
				coleccionReservas.remove(reserva);
			}
		}
	}

	// Devuelve las reservas realizadas por un profesor
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();

		while (it.hasNext()) {
			Reserva proxReserva = it.next();
			if (profesor.equals(proxReserva.getProfesor())) {
				reservas.add(new Reserva(proxReserva));
			}
		}
		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getCorreo);
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacion = -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());
			}
			return comparacion;
		};
		reservas.sort(Comparator.comparing(Reserva::getProfesor, comparadorProfesor)
				.thenComparing(Reserva::getAula, comparadorAula)
				.thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservas;
	}

	// Devuelve las reservas por aula ordenada
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}
		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();

		while (it.hasNext()) {
			Reserva proxReserva = it.next();
			if (aula.equals(proxReserva.getAula())) {
				reservas.add(new Reserva(proxReserva));
			}
		}

		Comparator<Aula> compararAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> compararPermanencia = (Permanencia permanencia1, Permanencia permanencia2) -> {
			int comparacion = -1;
			if (permanencia1.getDia().equals(permanencia2.getDia())) {
				if (permanencia1 instanceof PermanenciaPorTramo && permanencia2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) permanencia1).getTramo().ordinal(),
							((PermanenciaPorTramo) permanencia2).getTramo().ordinal());
				} else if (permanencia1 instanceof PermanenciaPorHora && permanencia2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) permanencia1).getHora()
							.compareTo(((PermanenciaPorHora) permanencia2).getHora());
				}
			} else {
				comparacion = permanencia1.getDia().compareTo(permanencia2.getDia());
			}
			return comparacion;
		};
		reservas.sort(Comparator.comparing(Reserva::getAula, compararAula).thenComparing(Reserva::getPermanencia,
				compararPermanencia));
		return reservas;
	}

	// Devuelve las reservas por permanencia
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: La permanencia no puede ser nula.");
		} else {
			List<Reserva> reservas = new ArrayList<>();
			Iterator<Reserva> it = coleccionReservas.iterator();

			while (it.hasNext()) {
				Reserva proxReserva = it.next();
				if (permanencia.equals(proxReserva.getPermanencia())) {
					reservas.add(new Reserva(proxReserva));
				}
			}
			return reservas;
		}
	}

	// Consulta la disponibilidad de una reserva. Para ello, pide un aula y una
	// permanencia
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}

		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}

		Iterator<Reserva> it = coleccionReservas.iterator();

		while (it.hasNext()) {
			Reserva proxReserva = it.next();
			if (aula.equals(proxReserva.getAula()) && permanencia.equals(proxReserva.getPermanencia())) {
				return false;
			}
		}
		return true;
	}

	// Representaci�n de las reservas
	public List<String> representar() {
		List<String> reservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();

		while (it.hasNext()) {
			reservas.add(it.next().toString());
		}
		return reservas;
	}
}
