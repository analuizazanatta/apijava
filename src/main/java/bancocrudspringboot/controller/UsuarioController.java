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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Array;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

	@PersistenceContext
	private EntityManager em;

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

//	// Listar todos os usuarios
//	@GetMapping("/usuariosrelatorio1")
//	@ResponseStatus(HttpStatus.OK)
//	public List<Usuario> getAllUsuarios1(){
//		ConsultasComJPQL cons = new ConsultasComJPQL();
//
//		Usuario usuario = cons.consultarUsuarioPeloNome();
//
//		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
//
//		listaUsuarios.add(usuario);
//
//		return listaUsuarios;
////		return this.usuarioRepository.findUsuario("adriano");
//		// return this.usuarioRepository.findUsuario();
//	}

	// Consulta com SQL completo
	@GetMapping("/usuariosrelatorio2")
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> getAllUsuarios2(){
		System.out.println("Entrou na função getDashboard()");
		/*Query query = em.createQuery("select produto.id,\r\n" + "			   produto.descricao,\r\n"
				+ "			   sum(estoque.quantidade) as qtd \r\n" + "		  from estoque \r\n"
				+ "		  join pedido on (pedido.id = estoque.pedido)\r\n"
				+ "		  join produto on (produto.id = estoque.produto_id)\r\n"
				+ "		 where estoque.operacao = 'Venda'\r\n" + "		   and pedido.status = 1\r\n"
				+ "	  group by produto.id,\r\n" + "			   produto.descricao");*/

		// Query query = em.createQuery("select coalesce(max(previsao),current_date) from pedido");
		Query query = em.createQuery("select u.id,u.email,u.nome,u.senha from public.usuario u");

		System.out.println("Query Dashboard> " + query);

		// documentacao de query em JPA
		// https://docs.oracle.com/javaee%2F6%2Fapi%2F%2F/javax/persistence/Query.html

		List<Usuario> listaDados = query.getResultList();

		return listaDados;


		// NOVO EXEMPLO
		// https://rafaelsakurai.gitbooks.io/desenvolvimento-distribuido/content/chapter3.1.html
		// return query.getMaxResults();



		// exemplo video angelica
		// https://github.com/angelicaweiler/cadastro-usuarios
		// video - https://www.youtube.com/watch?v=XSKDRZBPDII&t=518s


//
//		Usuario usuario = cons.consultarUsuarioPeloNome();
//
//		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
//
//		listaUsuarios.add(usuario);
//
//		return listaUsuarios;
	}
}
//
//@Component
//class ConsultasComJPQL {
//	@PersistenceContext
//	private EntityManager manager;
//
//	public Usuario consultarUsuarioPeloNome(){
//		String nome="Senac";
//		String jpql="SELECT p FROM usuario p where p.nome=:nome";
//		TypedQuery<Usuario> queryUsuario = manager.createQuery(jpql, Usuario.class)
//				.setParameter("nome",nome);
//
//		var retorno =  queryUsuario.getSingleResult();
//
//		System.out.println("DADOS RETORNADOS....");
//		System.out.println(retorno);
//
//		return retorno;
//	}
//}
