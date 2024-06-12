package bancocrudspringboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import bancocrudspringboot.exception.ResourceNotFoundException;
import bancocrudspringboot.model.ConsultaPadrao;
import bancocrudspringboot.model.OperadoresConsulta;
import bancocrudspringboot.model.Produto;
import bancocrudspringboot.model.UsuarioEntity;
import bancocrudspringboot.repository.ProdutoRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	// Listar todos os produtos
	@GetMapping("/produto")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> getAll(){
		return this.produtoRepository.findAll();
	}

	// Listar um produto
	@GetMapping("/produto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Produto> getCadastroById(@PathVariable(value = "id") Long cadastroId)
	throws ResourceNotFoundException {
		Produto cadastro = produtoRepository.findById(cadastroId)
				.orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado para o ID :: " + cadastroId));		
		return ResponseEntity.ok().body(cadastro);
	}

	// Inserir produto
	@PostMapping("/produto")
	@ResponseStatus(HttpStatus.CREATED)
	public Produto createCadastro(@RequestBody Produto cadastro) {
		return this.produtoRepository.save(cadastro);
	}

	// alterar produto
	@PutMapping("/produto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Produto> updateCadastro(@PathVariable(value = "id") Long cadastroId,
												  @Validated 
												  @RequestBody Produto cadastroCaracteristicas) throws ResourceNotFoundException {
		Produto cadastro = produtoRepository.findById(cadastroId)
				.orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado para o ID :: " + cadastroId));

        // metodos modificadores													
		cadastro.setDescricao(cadastroCaracteristicas.getDescricao());
		cadastro.setEstoque(cadastroCaracteristicas.getEstoque());
		cadastro.setPreco(cadastroCaracteristicas.getPreco());

		return ResponseEntity.ok(this.produtoRepository.save(cadastro));
	}
	
	@DeleteMapping("/produto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Boolean> deleteCadastro(@PathVariable(value = "id") Long cadastroId)
			throws ResourceNotFoundException {
		Produto cadastro = produtoRepository.findById(cadastroId)
				.orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado para o ID :: " + cadastroId));

		this.produtoRepository.delete(cadastro);

		Map<String, Boolean> resposta = new HashMap<>();

		resposta.put("cadastro deletado", Boolean.TRUE);

		return resposta;
	}

	@PostMapping("/consultaproduto")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> consultaCadastro(@Validated @RequestBody ConsultaPadrao cadastro) throws ResourceNotFoundException {

		String campoConsulta = cadastro.getCampo();
		List<Produto> listaProduto = new ArrayList<>();

		if(cadastro.getValor1() == null){
			return this.produtoRepository.findAll();
		} else if(cadastro.getValor1().equals("")){
			return this.produtoRepository.findAll();
		}
			
		// OPERADOR -> TODOS
		String operador = cadastro.getOperador();
		if(operador.equals(OperadoresConsulta.OPERADOR_TODOS)){
			return this.produtoRepository.findAll();
		}

		if(operador.equals(OperadoresConsulta.OPERADOR_IGUAL)){
			switch (campoConsulta) {
				case "codigoConsulta":
					Produto produto = produtoRepository.findById(Long.parseLong(cadastro.getValor1()))
							.orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado para o ID: " + cadastro.getValor1()));
					listaProduto.add(produto);
					break;
				case "descricaoConsulta":
					listaProduto = this.produtoRepository.findProdutoByDescricao(cadastro.getValor1());
					break;
				case "estoqueConsulta":
					listaProduto = this.produtoRepository.findProdutoByEstoque(Integer.parseInt(cadastro.getValor1()));
					break;
				case "precoConsulta":
					listaProduto = this.produtoRepository.findProdutoByPreco(Double.parseDouble(cadastro.getValor1()));
					break;					
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_MAIOR_OU_IGUAL)){
			switch (campoConsulta) {
				case "codigoConsulta":
				    listaProduto = this.produtoRepository.findProdutoMaiorIgual(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_MENOR_IGUAL)){
			switch (campoConsulta) {
				case "codigoConsulta":
				    listaProduto = this.produtoRepository.findProdutoMenorIgual(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_MENOR_QUE)){
			switch (campoConsulta) {
				case "codigoConsulta":
				    listaProduto = this.produtoRepository.findProdutoMenorQue(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		}  else if(operador.equals(OperadoresConsulta.OPERADOR_MAIOR_QUE)){
			switch (campoConsulta) {
				case "codigoConsulta":
				    listaProduto = this.produtoRepository.findProdutoMaiorQue(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_DIFERENTE_DE)){
			switch (campoConsulta) {
				case "codigoConsulta":
				    listaProduto = this.produtoRepository.findProdutoDiferenteDe(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_ENTRE)){
			switch (campoConsulta) {
				case "codigoConsulta":
				    listaProduto = this.produtoRepository.findProdutoEntreValores(Long.parseLong(cadastro.getValor1()), Long.parseLong(cadastro.getValor2()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_CONTIDO_EM)){
			switch (campoConsulta) {
				case "codigoConsulta":
					List<Integer>listaCodigos = new ArrayList<>();
					String[]lista = cadastro.getValor1().split(",");
					for(String codigoAtual:lista){
						listaCodigos.add(Integer.parseInt(codigoAtual));						
					}

				    listaProduto = this.produtoRepository.findProdutoContidoEm(listaCodigos);
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_NAO_CONTIDO_EM)){
			switch (campoConsulta) {
				case "codigoConsulta":
					List<Integer>listaCodigos = new ArrayList<>();
					String[]lista = cadastro.getValor1().split(",");
					for(String codigoAtual:lista){
						listaCodigos.add(Integer.parseInt(codigoAtual));						
					}

				    listaProduto = this.produtoRepository.findProdutoNaoContidoEm(listaCodigos);
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());
			}
		} else {
			throw new ResourceNotFoundException("Operador não desenvolvido!" + cadastro.getOperador());				
		}

		return listaProduto;
	}
}