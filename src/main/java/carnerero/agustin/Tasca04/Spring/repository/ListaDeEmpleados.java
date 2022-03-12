package carnerero.agustin.Tasca04.Spring.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import carnerero.agustin.Tasca04.Spring.model.Empleado;

public class ListaDeEmpleados {
	private List<Empleado> listaEmpleados = new ArrayList<>();
	private List<Empleado> listaPorTrabajos = new ArrayList<>();
	private static ListaDeEmpleados lista;

	// Base de datos inicial
	private ListaDeEmpleados() {

		listaEmpleados.add(new Empleado(1, "Jose", "administrativo", "imagen.webp"));
		listaEmpleados.add(new Empleado(2, "Ana", "administrativo", "imagen.webp"));
		listaEmpleados.add(new Empleado(3, "Sergio", "operario", "imagen.webp"));
		listaEmpleados.add(new Empleado(4, "Salvador", "operario", "imagen.webp"));
		listaEmpleados.add(new Empleado(5, "Ambrosio", "operario", "imagen.webp"));
		listaEmpleados.add(new Empleado(6, "Julia", "tecnico", "imagen.webp"));
		listaEmpleados.add(new Empleado(7, "Adela", "gerente", "imagen.webp"));
	}

	public static ListaDeEmpleados getInstance() {
		if (lista == null) {
			lista = new ListaDeEmpleados();
		}
		return lista;
	}

	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	/**
	 * Método que agrega un empleado a la lista de empleados
	 * 
	 * @param empleado
	 */
	public void insertar(Empleado empleado) {
		listaEmpleados.add(empleado);
	}

	/**
	 * Método que retorna lista de empleados por puesto de trabajo.
	 * 
	 * @param empleo
	 * @return Retorna lista de empleados por puesto de trabajo.
	 */
	public List<Empleado> buscarPorEmpleo(String empleo) {
		listaPorTrabajos.clear();
		for (Empleado empleado : listaEmpleados) {
			if (empleo.equals(empleado.getTrabajo())) {
				listaPorTrabajos.add(empleado);
			}
		}
		return listaPorTrabajos;
	}

	/**
	 * Método que elimina un empleado.
	 * 
	 * 
	 * @param id id del empleado.
	 */
	public void eliminar(int id) {
		boolean encontrado = false;
		Empleado empleado;
		Iterator<Empleado> it = listaEmpleados.iterator();
		while (it.hasNext() && !encontrado) {
			empleado = it.next();
			if (empleado.getId() == id) {
				it.remove();
				encontrado = true;

			}
		}
		if (!encontrado) {
			System.err.println("No se ha encontrado ningun empleado con este id.");
		}

	}

	/**
	 * Método que busca un empleado por su id.
	 * 
	 * @param id
	 * @return Retorna empleado por id.
	 */
	public Empleado buscarEmpleado(int id) {
		boolean encontrado = false;
		Empleado empleado = null;
		Iterator<Empleado> it = listaEmpleados.iterator();
		while (it.hasNext() && !encontrado) {
			empleado = it.next();
			if (empleado.getId() == id) {
				encontrado = true;

			}
		}
		if (!encontrado) {
			System.err.println("No se ha encontrado ningun operario con ese identidad.");
		}
		return empleado;
	}

	/**
	 * 
	 * Método que modifica los atributos de un empleado.Se usará para cambiar el
	 * puesto de trabajo del empleado
	 * 
	 * @param empleado
	 */
	public void editaEmpleado(Empleado empleado) {
		boolean encontrado = false;
		Iterator<Empleado> it = listaEmpleados.iterator();
		while (it.hasNext() && !encontrado) {
			Empleado emp = it.next();
			if (emp.getId() == empleado.getId()) {
				encontrado = true;
				emp.setId(empleado.getId());
				emp.setNombre(empleado.getNombre());
				emp.setTrabajo(empleado.getTrabajo());
				emp.setSalario(empleado.getSalario());

			}
		}
		if (!encontrado) {
			System.err.println("No se ha encontrado ningun operario con ese identidad.");
		}
	}

	public String guardarArchivo(MultipartFile multiPart, String ruta) {
		// Obtenemos el nombre original del archivo.
		String nombreOriginal = multiPart.getOriginalFilename();
		try {
			// Formamos el nombre del archivo para guardarlo en el disco duro.
			File imageFile = new File(ruta + nombreOriginal);
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			// Guardamos fisicamente el archivo en Disco duro.
			multiPart.transferTo(imageFile);
			return nombreOriginal;
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
			return null;
		}
	}

}
