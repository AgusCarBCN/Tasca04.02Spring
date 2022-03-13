package carnerero.agustin.Tasca04.Spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Permite configurar un archivo externo donde se almacenaran las fotos de los empleados 
@Configuration
public class ImgConfig implements WebMvcConfigurer
{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/img-fotos/**").addResourceLocations("file:/C:/empleados/img-fotos/");
	}
	
	
	
}
