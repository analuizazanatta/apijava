package bancocrudspringboot.repository;

    import bancocrudspringboot.model.Cliente;

    
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    
    @Repository
    public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
        // @Query(value = "select * from Cliente where descricao ilike concat('%', :descricao, '%')", nativeQuery = true)
        // List<Cliente>findClienteByDescricao(@Param("descricao")String descricao);
        
        // List<Cliente>findClienteByEstoque(int estoque);
    
        // // INJECAO DE DEPENDENCIA     
        // // List<Cliente>find Cliente By Preco(double preco);
    
        // List<Cliente>findClienteByPreco(double preco);
    
        // @Query(value = "select * from Cliente where id >= :codigo", nativeQuery = true)
        // List<Cliente> findClienteMaiorIgual(@Param("codigo")long codigo);
        
        // @Query(value = "select * from Cliente where id <= :codigo", nativeQuery = true)
        // List<Cliente> findClienteMenorIgual(@Param("codigo")long codigo);
    
        // @Query(value = "select * from Cliente where id < :codigo", nativeQuery = true)
        // List<Cliente> findClienteMenorQue(@Param("codigo")long codigo);
        
        // @Query(value = "select * from Cliente where id > :codigo", nativeQuery = true)
        // List<Cliente> findClienteMaiorQue(@Param("codigo")long codigo);
        
        // @Query(value = "select * from Cliente where id <> :codigo", nativeQuery = true)
        // List<Cliente> findClienteDiferenteDe(@Param("codigo")long codigo);
        
        // @Query(value = "select * from Cliente where id between :codigo1 and :codigo2", nativeQuery = true)
        // List<Cliente> findClienteEntreValores(@Param("codigo1")long codigo1, @Param("codigo2")long codigo2);
        
        // @Query(value = "select * from Cliente where id in :listaCodigo", nativeQuery = true)
        // List<Cliente> findClienteContidoEm(@Param("listaCodigo")List<Integer>listaCodigo);
    
        // @Query(value = "select * from Cliente where id not in :listaCodigo", nativeQuery = true)
        // List<Cliente> findClienteNaoContidoEm(@Param("listaCodigo")List<Integer>listaCodigo);    

}