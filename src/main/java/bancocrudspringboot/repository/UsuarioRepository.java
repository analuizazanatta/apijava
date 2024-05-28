package bancocrudspringboot.repository;

import bancocrudspringboot.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{


    // Criar uma consulta personalizada de usuarios
    // video - https://www.youtube.com/watch?v=YWTVxWjCt8Y&list=PLqq_mNkalpQfKWSxPfTqCKpGbSfEfdYD2&index=57

    // query nativa
    //    @Query(value="select * from usuario", nativeQuery = true)
    //    List<Usuario> findUsuario();

    // Filtra apenas Adriano
    // @Query(value="select u from usuario u where u.name = 'adriano'", nativeQuery = false)
    @Query(value="select u from Usuario u where u.nome like '%adriano%'", nativeQuery = false)
    List<UsuarioEntity>findUsuario();

    @Query(value="select u from Usuario u where u.nome like '%Julio%'", nativeQuery = false)
    List<UsuarioEntity>findUsuarioPersonalizado();

    @Query(value="select u from Usuario u where lower(u.nome) like lower(concat(:parametro_nome, '%'))", nativeQuery = false)
    List<UsuarioEntity>findUsuarioPersonalizado2(@Param("parametro_nome")String parametro_nome);

}
