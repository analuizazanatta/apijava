package bancocrudspringboot.repository;

import bancocrudspringboot.model.Produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    @Query(value = "select * from produto where descricao ilike concat('%', :descricao, '%')", nativeQuery = true)
    List<Produto>findProdutoByDescricao(@Param("descricao")String descricao);
    
    List<Produto>findProdutoByEstoque(int estoque);

    List<Produto>findProdutoByPreco(double preco);

    @Query(value = "select * from produto where id >= :codigo", nativeQuery = true)
    List<Produto> findProdutoMaiorIgual(@Param("codigo")long codigo);
    
    @Query(value = "select * from produto where id <= :codigo", nativeQuery = true)
    List<Produto> findProdutoMenorIgual(@Param("codigo")long codigo);

    @Query(value = "select * from produto where id < :codigo", nativeQuery = true)
    List<Produto> findProdutoMenorQue(@Param("codigo")long codigo);
    
    @Query(value = "select * from produto where id > :codigo", nativeQuery = true)
    List<Produto> findProdutoMaiorQue(@Param("codigo")long codigo);
    
    @Query(value = "select * from produto where id <> :codigo", nativeQuery = true)
    List<Produto> findProdutoDiferenteDe(@Param("codigo")long codigo);
    
    @Query(value = "select * from produto where id between :codigo1 and :codigo2", nativeQuery = true)
    List<Produto> findProdutoEntreValores(@Param("codigo1")long codigo1, @Param("codigo2")long codigo2);
    
    @Query(value = "select * from produto where id in :listaCodigo", nativeQuery = true)
    List<Produto> findProdutoContidoEm(@Param("listaCodigo")List<Integer>listaCodigo);

    @Query(value = "select * from produto where id not in :listaCodigo", nativeQuery = true)
    List<Produto> findProdutoNaoContidoEm(@Param("listaCodigo")List<Integer>listaCodigo);
}