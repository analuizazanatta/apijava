package bancocrudspringboot.repository;

import bancocrudspringboot.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

    @Query(value="select u.* from usuario u where email like concat(:email, '%')", nativeQuery = true)
    List<UsuarioEntity>findUsuarioByEmail(@Param("email")String email);

    @Query(value="select u.* from usuario u where nome like concat(:nome, '%')", nativeQuery = true)
    List<UsuarioEntity>findUsuarioByNome(@Param("nome")String nome);

}
