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
import bancocrudspringboot.model.Cliente;
import bancocrudspringboot.model.ConsultaPadrao;
import bancocrudspringboot.model.OperadoresConsulta;
import bancocrudspringboot.repository.ClienteRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/cliente")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> getAll() {
        return this.clienteRepository.findAll();
    }

    // Inserir
    @PostMapping("/cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCadastro(@RequestBody Cliente cadastro) {
        return this.clienteRepository.save(cadastro);
    }

    // alterar
    @PutMapping("/cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> updateCadastro(@PathVariable(value = "id")
                                                  Long cadastroId,
                                                  @Validated
                                                  @RequestBody Cliente cadastroCaracteristicas) throws ResourceNotFoundException {
        Cliente cadastro = clienteRepository.findById(cadastroId)
            .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado para o ID: " + cadastroId));

        cadastro.setNome(cadastroCaracteristicas.getNome());
        cadastro.setCpf(cadastroCaracteristicas.getCpf());
        cadastro.setEndereco(cadastroCaracteristicas.getEndereco());
        
        cadastro.setNumero(cadastroCaracteristicas.getNumero());
        cadastro.setComplemento(cadastroCaracteristicas.getComplemento());
        cadastro.setBairro(cadastroCaracteristicas.getBairro());
        
        cadastro.setCidade(cadastroCaracteristicas.getCidade());
        cadastro.setEstado(cadastroCaracteristicas.getEstado());

        return ResponseEntity.ok(this.clienteRepository.save(cadastro));
    }

    @DeleteMapping("/cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> deleteCadastro(@PathVariable(value = "id") Long cadastroId) throws ResourceNotFoundException {
        Cliente cadastro = clienteRepository.findById(cadastroId)
            .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado para o ID: " + cadastroId));

        this.clienteRepository.delete(cadastro);

        Map<String, Boolean> resposta = new HashMap<>();

        resposta.put("cadastro deletado", Boolean.TRUE);

        return resposta;
    }

    @PostMapping("/consultacliente")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> consultaCadastro(@Validated @RequestBody ConsultaPadrao cadastro) throws ResourceNotFoundException {

		String campoConsulta = cadastro.getCampo();
		List<Cliente> listaCliente = new ArrayList<>();

		if(cadastro.getValor1() == null){
			return this.clienteRepository.findAll();
		} else if(cadastro.getValor1().equals("")){
			return this.clienteRepository.findAll();
		}
			
		// OPERADOR -> TODOS
		String operador = cadastro.getOperador();
		if(operador.equals(OperadoresConsulta.OPERADOR_TODOS)){
			return this.clienteRepository.findAll();
		}

		if(operador.equals(OperadoresConsulta.OPERADOR_IGUAL)){
			switch (campoConsulta) {
				case "codigoConsulta":
					Cliente cliente = clienteRepository.findById(Long.parseLong(cadastro.getValor1()))
							.orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado para o ID: " + cadastro.getValor1()));
                            listaCliente.add(cliente);
					break;                    
			}
		} else {
			throw new ResourceNotFoundException("Operador não desenvolvido!" + cadastro.getOperador());				
		}
		return listaCliente;
	}

}