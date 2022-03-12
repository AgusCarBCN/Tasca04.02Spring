package carnerero.agustin.Tasca04.Spring.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Empleado {

	private static int counter;
	private Integer id;
	@NotNull
	@Size(min = 2, message = "El nombre es un campo requerido")
	@Pattern(regexp = "[A-Z,Ñ]{1}[A-Z,a-z,ñ,\b]{1,20}", message = "El nombre debe empezar por mayúsculas y contener letras")
	private String nombre;
	private String trabajo;
	private String foto;
	private Double salario;
	static {
		counter = 1;
	}

	public Empleado(Integer id, String nombre, String trabajo,String foto) {

		this.id = counter++;
		this.nombre = nombre;
		this.trabajo = trabajo;		
		this.foto=foto;
		if (trabajo.equals("operario")) {
			salario = 20000.0;
		} else if (trabajo.equals("tecnico")) {
			salario = 30000.0;
		} else if (trabajo.equals("administrativo")) {
			salario = 25000.0;
		} else if (trabajo.equals("gerente")) {
			salario = 60000.0;
		}
	}

}
