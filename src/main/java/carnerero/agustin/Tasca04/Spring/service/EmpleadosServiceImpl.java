package carnerero.agustin.Tasca04.Spring.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import carnerero.agustin.Tasca04.Spring.model.Empleado;

@Service
public class EmpleadosServiceImpl implements IEmpleadosService {
	private List<Empleado> listaEmpleados=null;
	private List<Empleado> listaPorTrabajos=null;

	private EmpleadosServiceImpl() {
		listaEmpleados = new ArrayList<>();
		listaPorTrabajos = new ArrayList<>();
		listaEmpleados.add(new Empleado(1, "Jose", "administrativo", 1));
		listaEmpleados.add(new Empleado(2, "Ana", "administrativo", 1));
		listaEmpleados.add(new Empleado(3, "Sergio", "operario", 1));
		listaEmpleados.add(new Empleado(4, "Salvador", "operario", 0));
		listaEmpleados.add(new Empleado(5, "Ambrosio", "operario", 1));
		listaEmpleados.add(new Empleado(6, "Julia", "tecnico", 0));
		listaEmpleados.add(new Empleado(7, "Adela", "gerente", 0));
	}

	@Override
	public List<Empleado> listaEmpleados() {
		return listaEmpleados;
	}

	@Override
	public void insertar(Empleado empleado) {
		listaEmpleados.add(empleado);
	}

	@Override
	public List<Empleado> buscarPorEmpleo(String empleo) {
		listaPorTrabajos.clear();
		for (Empleado empleado : listaEmpleados) {
			if (empleo.equals(empleado.getTrabajo())) {
				listaPorTrabajos.add(empleado);
			}
		}
		return listaPorTrabajos;
	}

	@Override
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
				emp.setDisponible(empleado.getDisponible());

			}
		}
		if (!encontrado) {
			System.err.println("No se ha encontrado ningun operario con ese identidad.");
		}
	}

	@Override
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

	@Override
	public void eliminar(Integer id) {
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

	@Override
	public Empleado buscarEmpleado(Integer id) {
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

}
