package carnerero.agustin.Tasca04.Spring.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import carnerero.agustin.Tasca04.Spring.model.Empleado;
import carnerero.agustin.Tasca04.Spring.service.IEmpleadosService;

@Controller
@RequestMapping(value = "/empleados")
public class EmpleadosController {
	// Inyectamos clase servicio en controller
	@Autowired
	private IEmpleadosService serviceEmpleados;
	@Value("${tasca0402.path.images}")
	private String ruta="C:/empleados/img-fotos/";

	@GetMapping("/")
	public String mostrarTodos(Model model) {
		model.addAttribute("empleados", serviceEmpleados.listaEmpleados());
		
		return "listadeempleados";
	}

	@GetMapping("/{trabajo}")
	public String buscarPorTrabajo(@PathVariable String trabajo, Model model) {
		model.addAttribute("empleados", serviceEmpleados.buscarPorEmpleo(trabajo));
		return "listadeempleados";
	}

	// Renderiza formulario.htlm
	@GetMapping("/formulario")
	public String getFormulario(Empleado empleado) {
		return "formulario";
	}

	// Método que recibe los valores de los inputs del formulario
	@PostMapping("/agregar")
	public String agregar(@Valid Empleado empleado, BindingResult result, RedirectAttributes attributes,
			@RequestParam("fotoEmpleado") MultipartFile foto) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Ocurrio un error: " + error.getDefaultMessage());
			}

			return "formulario";
		}
		if (!foto.isEmpty()) {
			String nombreFoto = serviceEmpleados.guardarArchivo(foto, ruta);
			if (nombreFoto != null) {
				empleado.setFoto(nombreFoto);
			}
		}
		serviceEmpleados.insertar(empleado);
		attributes.addFlashAttribute("mensaje", "empleado agregado correctamente");				
		System.out.println("Empleado agregado correctamente.");
		return "redirect:/empleados/";
	}

	@GetMapping("/descargar/{id}")
	public ResponseEntity<Resource> descargar(@PathVariable int id) {
		Empleado empleado = serviceEmpleados.buscarEmpleado(id);
		String fotoEmpleado = empleado.getFoto();
		Resource recurso = null;
		Path rutaFoto = null;
		try {
			rutaFoto = Paths.get(ruta).resolve(fotoEmpleado).toAbsolutePath();
			recurso = new UrlResource(rutaFoto.toUri());
			if (!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("Error: No se puede descargar la imagen");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();

		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);

	}

	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable Integer id, Model model) {
		serviceEmpleados.eliminar(id);
		model.addAttribute("empleados", serviceEmpleados.listaEmpleados());
		return "listadeempleados";

	}

	@GetMapping("/modificar/{id}")
	public String editarEmpleado(@PathVariable Integer id, Model model) {
		Empleado empleado = serviceEmpleados.buscarEmpleado(id);
		model.addAttribute("empleados", serviceEmpleados.listaEmpleados());
		model.addAttribute("empleado", empleado);
		return "formulario2";
	}

	@PostMapping("/modificar")
	public String modificar(Empleado empleado, RedirectAttributes attributes) {	
		serviceEmpleados.editaEmpleado(empleado);
		attributes.addFlashAttribute("mensaje2", "modificacion de datos correcta");				
		System.out.println("Modificacion de datos correcta.");
		return "redirect:/empleados/";
	}

	@GetMapping("/cambiarfoto/{id}")
	public String cambiarFoto(@PathVariable Integer id, Model model) {
		Empleado empleado = serviceEmpleados.buscarEmpleado(id);
		model.addAttribute("empleados", serviceEmpleados.listaEmpleados());
		model.addAttribute("empleado", empleado);		
		return "formulario2";
	}
	
	@PostMapping("/cambiarfoto")
	public String modificarfoto(Empleado empleado,RedirectAttributes attributes , @RequestParam("fotoEmpleado") MultipartFile foto)
{
		if (!foto.isEmpty()) {
			String nombreFoto = serviceEmpleados.guardarArchivo(foto, ruta);
			if (nombreFoto != null) {
				empleado.setFoto(nombreFoto);
			}
		}
		serviceEmpleados.cambiaFoto(empleado);
		attributes.addFlashAttribute("mensaje3", "Has cambiado la foto");				
		System.out.println("Cambio de foto correcta.");
		return "redirect:/empleados/";
		
}

@GetMapping("/empleado/{id}")
	public String verDetalle(@PathVariable("id") int id, Model model) {
		Empleado empleado = serviceEmpleados.buscarEmpleado(id);
		model.addAttribute("empleado", empleado);
		return "detalleDeEmpleado";

	}

}
