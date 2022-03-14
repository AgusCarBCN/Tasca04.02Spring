package carnerero.agustin.Tasca04.Spring.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import carnerero.agustin.Tasca04.Spring.model.Empleado;

public interface IEmpleadosService {
	List<Empleado> listaEmpleados();

	List<Empleado> buscarPorEmpleo(String trabajo);

	String guardarArchivo(MultipartFile foto, String ruta);

	void insertar(@Valid Empleado empleado);

	void eliminar(Integer id);

	Empleado buscarEmpleado(Integer id);

	void editaEmpleado(Empleado empleado);

}
