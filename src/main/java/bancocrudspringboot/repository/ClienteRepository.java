package bancocrudspringboot.repository;

import bancocrudspringboot.model.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    // Consulta com SQL
    // @Query(value = "select * from cliente where nome ilike concat('%', :nome, '%')", nativeQuery = true)
    // List<Cliente>findClienteByNomeContem(@Param("nome") String nome);
    
    // Consulta pré-pronta Java (apenas operador =)
    List<Cliente>findClienteByNome(String nome);
    List<Cliente>findClienteByCpf(String cpf);
    List<Cliente>findClienteByEndereco(String endereco);
    List<Cliente>findClienteByNumero(int numero);
    List<Cliente>findClienteByComplemento(String complemento);
    List<Cliente>findClienteByBairro(String bairro);
    List<Cliente>findClienteByCep(String cep);
    List<Cliente>findClienteByCidade(String cidade);
    List<Cliente>findClienteByEstado(String estado);

    // Consulta padrão por ID (utilizando dos operadores)
    @Query(value = "select * from cliente where id >= :codigo", nativeQuery = true)
    List<Cliente> findClienteMaiorIgual(@Param("codigo")long codigo);
    
    @Query(value = "select * from cliente where id <= :codigo", nativeQuery = true)
    List<Cliente> findClienteMenorIgual(@Param("codigo")long codigo);

    @Query(value = "select * from cliente where id < :codigo", nativeQuery = true)
    List<Cliente> findClienteMenorQue(@Param("codigo")long codigo);
    
    @Query(value = "select * from cliente where id > :codigo", nativeQuery = true)
    List<Cliente> findClienteMaiorQue(@Param("codigo")long codigo);
    
    @Query(value = "select * from cliente where id <> :codigo", nativeQuery = true)
    List<Cliente> findClienteDiferenteDe(@Param("codigo")long codigo);
    
    @Query(value = "select * from cliente where id between :codigo1 and :codigo2", nativeQuery = true)
    List<Cliente> findClienteEntreValores(@Param("codigo1")long codigo1, @Param("codigo2")long codigo2);
    
    @Query(value = "select * from cliente where id in :listaCodigo", nativeQuery = true)
    List<Cliente> findClienteContidoEm(@Param("listaCodigo")List<Integer>listaCodigo);

    @Query(value = "select * from cliente where id not in :listaCodigo", nativeQuery = true)
    List<Cliente> findClienteNaoContidoEm(@Param("listaCodigo")List<Integer>listaCodigo);
}