package bancocrudspringboot.controller;

import bancocrudspringboot.exception.ResourceNotFoundException;
import bancocrudspringboot.model.Cadastro;
import bancocrudspringboot.model.Usuario;
import bancocrudspringboot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// Listar todos os usuarios
	@GetMapping("/usuarios")
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> getAllCadastros(){
		return this.usuarioRepository.findAll();
	}
	
	// pegar o usuario pelo id
	@GetMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Usuario> getCadastroById(@PathVariable(value = "id") Long cadastroId)
	    throws ResourceNotFoundException {

		Usuario cadastro = usuarioRepository.findById(cadastroId)
	      .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID :: " + cadastroId));
	    return ResponseEntity.ok().body(cadastro);
	    
	}
	
	// Salvar dados
	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario createCadastro(@RequestBody Usuario cadastro) {
		return this.usuarioRepository.save(cadastro);
	}
	
	// atualizar dados
	@PutMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Usuario> updateCadastro(@PathVariable(value = "id") Long cadastroId,
    	@Validated @RequestBody Usuario cadastroCaracteristicas) throws ResourceNotFoundException {
		Usuario cadastro = usuarioRepository.findById(cadastroId)
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID :: " + cadastroId));
        
        cadastro.setEmail(cadastroCaracteristicas.getEmail());
        cadastro.setNome(cadastroCaracteristicas.getNome());
		cadastro.setSenha(cadastroCaracteristicas.getSenha());

        return ResponseEntity.ok(this.usuarioRepository.save(cadastro));
    }
	
	//deletar conta
	@DeleteMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Boolean> deleteCadastro(@PathVariable(value = "id") Long cadastroId) 
			throws ResourceNotFoundException {
		Usuario cadastro = usuarioRepository.findById(cadastroId)
	   .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID :: " + cadastroId));
	
	    this.usuarioRepository.delete(cadastro);

		Map<String, Boolean> resposta = new HashMap<>();

		resposta.put("cadastro deletado", Boolean.TRUE);

	    return resposta;
	}


	// Criar uma consulta personalizada de usuarios
	// video - https://www.youtube.com/watch?v=YWTVxWjCt8Y&list=PLqq_mNkalpQfKWSxPfTqCKpGbSfEfdYD2&index=57

	// Listar todos os usuarios
	@GetMapping("/usuariosrelatorio")
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> getAllUsuarios(){
//		return this.usuarioRepository.findUsuario("adriano");
		return this.usuarioRepository.findUsuario();
	}

	// Listar todos os usuarios
	@GetMapping("/usuariosrelatorio1")
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> getAllUsuarios1(){
		ConsultasComJPQL cons = new ConsultasComJPQL();

		Usuario usuario = cons.consultarUsuarioPeloNome();

		List<Usuario> listaUsuarios = new ArrayList<Usuario>();

		listaUsuarios.add(usuario);

		return listaUsuarios;
//		return this.usuarioRepository.findUsuario("adriano");
		// return this.usuarioRepository.findUsuario();
	}
}

@Component
class ConsultasComJPQL {
	@PersistenceContext
	private EntityManager manager;

	public Usuario consultarUsuarioPeloNome(){
		String nome="Senac";
		String jpql="SELECT p FROM usuario p where p.nome=:nome";
		TypedQuery<Usuario> queryUsuario = manager.createQuery(jpql, Usuario.class)
				.setParameter("nome",nome);

		var retorno =  queryUsuario.getSingleResult();

		System.out.println("DADOS RETORNADOS....");
		System.out.println(retorno);

		return retorno;
	}
}
