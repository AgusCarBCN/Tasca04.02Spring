package carnerero.agustin.Tasca04.Spring.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import carnerero.agustin.Tasca04.Spring.model.Empleado;
import carnerero.agustin.Tasca04.Spring.repository.ListaDeEmpleados;

@Controller
public class EmpleadosController {

	ListaDeEmpleados repoEmpleados = ListaDeEmpleados.getInstance();
	String ruta = "C:/empleados/img-fotos/";

	@GetMapping("/")
	public String mostrarTodos(Model model) {
		List<Empleado> listEmployees = repoEmpleados.getListaEmpleados();

		model.addAttribute("empleados", listEmployees);
		return "listadeempleados";
	}

	@GetMapping("/{trabajo}")
	public String buscarPorTrabajo(@PathVariable String trabajo, Model model) {

		List<Empleado> listEmployeesByJob = repoEmpleados.buscarPorEmpleo(trabajo);
		model.addAttribute("empleados", listEmployeesByJob);
		return "listadeempleados";
	}

	@PostMapping("/agregar")
	public String agregar(@Valid Empleado empleado, BindingResult result, Model model,
			@RequestParam("fotoEmpleado") MultipartFile foto) {
		if (result.hasErrors()) {
			return "formulario";
		}
		if (!foto.isEmpty()) {
			String nombreFoto = repoEmpleados.guardarArchivo(foto, ruta);
			if (nombreFoto != null) {
				empleado.setFoto(nombreFoto);
			}
		}
		repoEmpleados.insertar(empleado);
		model.addAttribute("empleados", repoEmpleados.getListaEmpleados());
		return "listadeempleados";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable Integer id, Model model) {
		List<Empleado> listEmployees = repoEmpleados.getListaEmpleados();
		repoEmpleados.eliminar(id);
		model.addAttribute("empleados", listEmployees);
		return "listadeempleados";

	}

	@GetMapping("/modificar/{id}")
	public String editarEmpleado(@PathVariable Integer id, Model model) {
		Empleado empleado = repoEmpleados.buscarEmpleado(id);
		model.addAttribute("empleados", repoEmpleados.getListaEmpleados());
		model.addAttribute("empleado", empleado);
		return "listadeempleados";
	}

	@PostMapping("/modificar")
	public String modificar(Empleado empleado, Model model) {
		repoEmpleados.editaEmpleado(empleado);
		model.addAttribute("empleados", repoEmpleados.getListaEmpleados());
		return "listadeempleados";
	}

}
