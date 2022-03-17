package carnerero.agustin.Tasca04.Spring.model;

import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Empleado {

	
	private int id;
	@NotNull
	@Size(min = 2, message = "El nombre es un campo requerido")
	@Pattern(regexp = "[A-Z,Ñ]{1}[A-Z,a-z,ñ,\b]{1,20}", message = "El nombre debe empezar por mayúsculas y contener letras")
	private String nombre;
	private String trabajo;
	private String foto;
	private Double salario;
	private  Integer disponible;
	

	public Empleado(Integer id, String nombre, String trabajo,Integer disponible) {
		//Para id genero número aleatorio del 1 al 100000
		this.id =(int)(Math. random()*100000+1); 
		this.nombre = nombre;
		this.trabajo = trabajo;	
		//Si no se asigna una foto por defecto saldrá la imagen de foto anonima
		this.foto="imagen.webp";
		this.disponible=disponible;		
		
	}	
	
}
