package bancocrudspringboot.repository;

import bancocrudspringboot.model.Produto;
import scala.collection.immutable.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    @Query(value="select p.* from produto u where descricao like concat(:descricao, '%')", nativeQuery = true)
        List<Produto> findProdutoByDescricao(@Param("descricao") String descricao);
}