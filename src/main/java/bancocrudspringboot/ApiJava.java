package bancocrudspringboot;

import bancocrudspringboot.model.Usuario;
import bancocrudspringboot.repository.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiJava {

	public static void main(String[] args) {
		SpringApplication.run(ApiJava.class, args);
	}
}
