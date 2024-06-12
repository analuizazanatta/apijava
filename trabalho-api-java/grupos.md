
Atividade 01
Criar a consulta completa para a tabela de Clientes.
Usar como base a classe ModelCliente.

Criar apenas o metodo "consultaCliente"
Criar consulta por id, para todos os operadores como foi feito no produto.
Criar consulta por todos os campos para operadores "IGUAL"

Criar "ClienteController.java"
Criar "ClienteRepository.java"

# LISTA DE GRUPOS DE ALUNOS

## Grupo 01
GABRIELE
ADRIANO 
DIEGO RICARDO

## Grupo 02
Cauê
Bruna
Júlio

## Grupo 03
Eduarda
Romulo
Helton

## Grupo 04
Yan Carlos Shcafer
Vinicius 
Ryan 

## Grupo 05
Mikael e Ana Luiza Zanatta

code - ModelCliente abaixo
```
package bancocrudspringboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Cliente")
public class Cliente {

    private long id;
    private String nome;
    private String cpf;
    private String endereco;
    private int numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }    
}

```








