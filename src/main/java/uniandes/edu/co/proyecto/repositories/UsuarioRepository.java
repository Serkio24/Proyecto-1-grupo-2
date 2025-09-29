package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query(value = "SELECT * FROM usuarios", nativeQuery = true)
    Collection<UsuarioEntity> darUsuarios();

    @Query(value = "SELECT * FROM usuarios WHERE idUsuario = :id", nativeQuery = true)
    UsuarioEntity darUsuario(@Param("id") Long id);

    @Query(value = "SELECT * FROM usuarios u WHERE u.nombre LIKE '%' || :nombre || '%'", nativeQuery = true)
    Collection<UsuarioEntity> darUsuariosPorNombre(@Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usuarios WHERE idUsuario = :id", nativeQuery = true)
    void eliminarUsuario(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE usuarios SET nombre = :nombre, numeroCelular = :numeroCelular, numeroCedula = :numeroCedula, correoElectronico = :correo, tipo = :tipo WHERE idUsuario = :id", nativeQuery = true)
    void actualizarUsuario(@Param("id") Long id,
                           @Param("nombre") String nombre,
                           @Param("numeroCelular") String numeroCelular,
                           @Param("numeroCedula") String numeroCedula,
                           @Param("correo") String correo,
                           @Param("tipo") String tipo);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuarios (nombre, numeroCelular, numeroCedula, correoElectronico, tipo) VALUES (:nombre, :numeroCelular, :numeroCedula, :correo, :tipo)", nativeQuery = true)
    void insertarUsuario(@Param("nombre") String nombre,
                         @Param("numeroCelular") String numeroCelular,
                         @Param("numeroCedula") String numeroCedula,
                         @Param("correo") String correo,
                         @Param("tipo") String tipo);
}
