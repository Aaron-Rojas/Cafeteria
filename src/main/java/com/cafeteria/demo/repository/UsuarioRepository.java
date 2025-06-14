package com.cafeteria.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafeteria.demo.model.User;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {
    // Métodos de búsqueda existentes (derivados de nombres de métodos, no SPs)
    List<User> findByUsernameContainingIgnoreCase(String nombreusuario);
    Optional<User> findByEmail(String correo);

    // Los métodos para procedimientos almacenados se ELIMINAN:
    // @Procedure(procedureName = "panadirusuario")
    // void registrarusuario(@Param("p_nombreusuario") String nomusuari,
    //                       @Param("p_email") String correo,
    //                       @Param("p_password") String contrase,
    //                       @Param("p_rol") Long rol);

    // @Procedure(procedureName = "pactualizarusuario")
    // void actualizarusuario(@Param("p_usuarioid_param") Long id,
    //                        @Param("p_nombreusuario") String nomusuari,
    //                        @Param("p_email") String correo);

    // JpaRepository ya provee:
    // save(User user) para guardar y actualizar
    // deleteById(Long id) para eliminar
    // findAll() para listar todos
    // findById(Long id) para buscar por ID
}
