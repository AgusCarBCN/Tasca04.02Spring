package carnerero.agustin.Tasca04.Spring.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;

@Controller
public class EmpleadosErrorController implements ErrorController {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(EmpleadosErrorController.class);

	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		String paginaError = "error";
		String pageTitle = "Error";
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				pageTitle = "Página no encontrada";
				paginaError = "error/404";
				LOGGER.error("Error 404");
			}
			else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				pageTitle = "Error en el servidor";
				paginaError = "error/500";
				LOGGER.error("Error 500");
			}
		}
		model.addAttribute("pageTitle", pageTitle);
		return paginaError;
		
	}
}
