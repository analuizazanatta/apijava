package bancocrudspringboot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SpringBootApplication
public class ApijavasenacApplication {

    // video da explicacao
    // SPRING BOOT NA PRATICA EM 10 MINUTOS
    // https://www.youtube.com/watch?v=Hwqb12zPT1U
	public static void main(String[] args) {
		SpringApplication.run(ApijavasenacApplication.class, args);
	}
}

@Entity
class Demo {

    @Id
    Long id;
    String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

@RestController
@RequestMapping("/api/v1")
class DemoController {

    DemoService demoService;

    @Autowired
    private DemoRepository demoRepository;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/demo/{id}")
    public Demo getDemo(@PathVariable("id") Long id){
        return demoService.getDemo(id).orElse(null);
    }

    @GetMapping("/demo")
    public List<Demo> getAllDemo(){
        return demoRepository.findAll();
    }

    // salvar conta
    @PostMapping("/demo")
    @ResponseStatus(HttpStatus.CREATED)
    public Demo createCadastro(@RequestBody Demo cadastro) {

        System.out.println("Descricao:" + cadastro.getDescription());

        return this.demoRepository.save(cadastro);
    }
}

@Service
class DemoService{

    DemoRepository demoRepository;

    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public Optional<Demo> getDemo(Long id){
        return demoRepository.findById(id);
    }

    public List<Demo> getAllDemo(){
        return demoRepository.findAll();
    }
}

interface DemoRepository extends JpaRepository<Demo, Long>{

}

//@RestController
//@RequestMapping("/api/v1")
//class ProdutoController {
//
//    DemoService demoService;
//
//    @Autowired
//    private DemoRepository demoRepository;
//
//    public ProdutoController(DemoService demoService) {
//        this.demoService = demoService;
//    }
//
//    @GetMapping("/demo/{id}")
//    public Demo getDemo(@PathVariable("id") Long id){
//        return demoService.getDemo(id).orElse(null);
//    }
//
//    @GetMapping("/demo")
//    public List<Demo> getAllDemo(){
//        return demoRepository.findAll();
//    }
//
//    // salvar conta
//    @PostMapping("/demo")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Demo createCadastro(@RequestBody Demo cadastro) {
//
//        System.out.println("Descricao:" + cadastro.getDescription());
//
//        return this.demoRepository.save(cadastro);
//    }
//}
