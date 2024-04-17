package bancocrudspringboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bancocrudspringboot.exception.ResourceNotFoundException;
import bancocrudspringboot.model.Cadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import bancocrudspringboot.repository.CadastroRepository;


@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CadastroController {
	
	@Autowired
	private CadastroRepository cadastroRepository;
	
	//pegar todas as contas
	@GetMapping("/cadastros")
	@ResponseStatus(HttpStatus.OK)
	public List<Cadastro> getAllCadastros(){
		return this.cadastroRepository.findAll();
	}
	
	//pegar a conta pelo id
	@GetMapping("/cadastros/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Cadastro> getCadastroById(@PathVariable(value = "id") Long cadastroId)
	    throws ResourceNotFoundException {
		
	    Cadastro cadastro = cadastroRepository.findById(cadastroId)
	      .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada para o ID :: " + cadastroId));
	    return ResponseEntity.ok().body(cadastro);
	    
	}
	
	//salvar conta
	@PostMapping("/cadastros")
	@ResponseStatus(HttpStatus.CREATED)
	public Cadastro createCadastro(@RequestBody Cadastro cadastro) {
		
		return this.cadastroRepository.save(cadastro);
		
	}
	
	//atualizar conta
	@PutMapping("/cadastros/{id}")
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cadastro> updateCadastro(@PathVariable(value = "id") Long cadastroId,
    	@Validated @RequestBody Cadastro cadastroCaracteristicas) throws ResourceNotFoundException {
        Cadastro cadastro = cadastroRepository.findById(cadastroId)
        .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada para o ID :: " + cadastroId));
        
        cadastro.setCpf(cadastroCaracteristicas.getCpf());
        cadastro.setEmail(cadastroCaracteristicas.getEmail());
        cadastro.setNome(cadastroCaracteristicas.getNome());
        cadastro.setDataNascimento(cadastroCaracteristicas.getDataNascimento());
        
        return ResponseEntity.ok(this.cadastroRepository.save(cadastro));
        
    }
	
	//deletar conta
	@DeleteMapping("/cadastros/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Boolean> deleteCadastro(@PathVariable(value = "id") Long cadastroId) 
			throws ResourceNotFoundException {
	    Cadastro cadastro = cadastroRepository.findById(cadastroId)
	   .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada para o ID :: " + cadastroId));
	
	    this.cadastroRepository.delete(cadastro);
	    Map<String, Boolean> resposta = new HashMap<>();
	    resposta.put("cadastro deletado", Boolean.TRUE);
	    return resposta;
	}

}
