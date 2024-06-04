package bancocrudspringboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import bancocrudspringboot.exception.ResourceNotFoundException;
import bancocrudspringboot.model.ConsultaPadrao;
import bancocrudspringboot.model.Produto;
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

	@PostMapping("/consultaproduto")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> consultaCadastro(@Validated @RequestBody ConsultaPadrao cadastro) throws ResourceNotFoundException {

		String campoUsuario = cadastro.getCampo();
		List<Produto> listaProduto = new ArrayList<>();

		if (cadastro.getValor1() == null || 
			cadastro.getValor1().equals("")) {
			return this.produtoRepository.findAll();
		}


		switch (campoUsuario) {
			case "codigoConsulta":
				Produto produto = produtoRepository.findById(Long.parseLong(cadastro.getValor1()))
						.orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado para o ID :: " + cadastro.getValor1()));
				listaProduto.add(produto);
				break;			
			case "descricaoConsulta":
				listaProduto = this.produtoRepository.findProdutoByDescricao(cadastro.getValor1());
				break;			
		}

		return listaProduto;
	}


}