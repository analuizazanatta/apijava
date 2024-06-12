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
import bancocrudspringboot.model.Cliente;
import bancocrudspringboot.repository.ClienteRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	// Listar todos os Clientes
	@GetMapping("/cliente")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> getAll(){
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
				case "consultaCliente":
					Cliente Cliente = clienteRepository.findById(Long.parseLong(cadastro.getValor1()))
							.orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado para o ID: " + cadastro.getValor1()));
					listaCliente.add(Cliente);
					break;
				case "consultaClienteNome":
					listaCliente = this.clienteRepository.findClienteByNome(cadastro.getValor1());
					break;		
                case "consultaClienteCPF":
					listaCliente = this.clienteRepository.findClienteByCpf(cadastro.getValor1());
					break;		
                case "consultaClienteEndereco":
					listaCliente = this.clienteRepository.findClienteByEndereco(cadastro.getValor1());
					break;			
                case "consultaClienteNumero":
					listaCliente = this.clienteRepository.findClienteByNumero(Integer.parseInt(cadastro.getValor1()));
					break;			
                case "consultaClienteComplemento":
					listaCliente = this.clienteRepository.findClienteByComplemento(cadastro.getValor1());
					break;			
                case "consultaClienteBairro":
					listaCliente = this.clienteRepository.findClienteByBairro(cadastro.getValor1());
					break;			
                case "consultaClienteCEP":
					listaCliente = this.clienteRepository.findClienteByCep(cadastro.getValor1());
			    	break;			
                case "consultaClienteCidade":
					listaCliente = this.clienteRepository.findClienteByCidade(cadastro.getValor1());
					break;			
                case "consultaClienteEstado":
					listaCliente = this.clienteRepository.findClienteByEstado(cadastro.getValor1());
					break;		
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_MAIOR_OU_IGUAL)){
			switch (campoConsulta) {
				case "consultaCliente":
				    listaCliente = this.clienteRepository.findClienteMaiorIgual(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_MENOR_IGUAL)){
			switch (campoConsulta) {
				case "consultaCliente":
				    listaCliente = this.clienteRepository.findClienteMenorIgual(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_MENOR_QUE)){
			switch (campoConsulta) {
				case "consultaCliente":
				    listaCliente = this.clienteRepository.findClienteMenorQue(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		}  else if(operador.equals(OperadoresConsulta.OPERADOR_MAIOR_QUE)){
			switch (campoConsulta) {
				case "consultaCliente":
				    listaCliente = this.clienteRepository.findClienteMaiorQue(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_DIFERENTE_DE)){
			switch (campoConsulta) {
				case "consultaCliente":
				    listaCliente = this.clienteRepository.findClienteDiferenteDe(Long.parseLong(cadastro.getValor1()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_ENTRE)){
			switch (campoConsulta) {
				case "consultaCliente":
				    listaCliente = this.clienteRepository.findClienteEntreValores(Long.parseLong(cadastro.getValor1()), Long.parseLong(cadastro.getValor2()));
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_CONTIDO_EM)){
			switch (campoConsulta) {
				case "consultaCliente":
					List<Integer>listaCodigos = new ArrayList<>();
					String[]lista = cadastro.getValor1().split(",");
					for(String codigoAtual:lista){
						listaCodigos.add(Integer.parseInt(codigoAtual));						
					}

				    listaCliente = this.clienteRepository.findClienteContidoEm(listaCodigos);
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());
			}
		} else if(operador.equals(OperadoresConsulta.OPERADOR_NAO_CONTIDO_EM)){
			switch (campoConsulta) {
				case "consultaCliente":
					List<Integer>listaCodigos = new ArrayList<>();
					String[]lista = cadastro.getValor1().split(",");
					for(String codigoAtual:lista){
						listaCodigos.add(Integer.parseInt(codigoAtual));						
					}

				    listaCliente = this.clienteRepository.findClienteNaoContidoEm(listaCodigos);
					break;
				default:
					throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());
			}
		} else {
			throw new ResourceNotFoundException("Operador não desenvolvido!" + cadastro.getOperador());				
		}

		return listaCliente;
	}
}