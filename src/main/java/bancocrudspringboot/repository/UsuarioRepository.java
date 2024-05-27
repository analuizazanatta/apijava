package bancocrudspringboot.repository;

import bancocrudspringboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


    // Criar uma consulta personalizada de usuarios
    // video - https://www.youtube.com/watch?v=YWTVxWjCt8Y&list=PLqq_mNkalpQfKWSxPfTqCKpGbSfEfdYD2&index=57

    // query nativa
    //    @Query(value="select * from usuario", nativeQuery = true)
    //    List<Usuario> findUsuario();

    // Filtra apenas Adriano
    // @Query(value="select u from usuario u where u.name = 'adriano'", nativeQuery = false)
    @Query(value="select * from usuario where nome ilike '%adriano%'", nativeQuery = true)
    List<Usuario> findUsuario();

}
