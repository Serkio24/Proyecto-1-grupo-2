package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    //Create, remarcar cada parametro con anotacion param
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuarios (nombre, numeroCelular, numeroCedula, correoElectronico) VALUES (:nombre, :numeroCelular, :numeroCedula, :correoElectronico)", nativeQuery=true)
    void insertarUsuario(@Param("nombre") String nombre, @Param("numeroCelular") String numeroCelular, @Param("numeroCedula") String numeroCedula, @Param("correoElectronico") String correoElectronico);

    //Read: Get all
    //indicar que consulta es y que es nativa
    @Query(value = "SELECT * FROM usuarios", nativeQuery=true)
    Collection<UsuarioEntity> darUsuarios();

    //Read: Get one, le pasamos id por parametro
    @Query(value = "SELECT * FROM usuarios WHERE idUsuario=:id", nativeQuery=true)
    UsuarioEntity darUsuario(@Param("id") Long idUsuario);

    //Update
    @Modifying
    @Transactional
    @Query(value= "UPDATE usuarios SET nombre=:nombre, numeroCelular=:numeroCelular, numeroCedula=:numeroCedula, correoElectronico=:correoElectronico WHERE idUsuario=:id", nativeQuery=true)
    void actualizarUsuario(@Param("id") Long idUsuario, @Param("nombre") String nombre, @Param("numeroCelular") String numeroCelular, @Param("numeroCedula") String numeroCedula, @Param("correoElectronico") String correoElectronico);

    //Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usuarios WHERE idUsuario=:id", nativeQuery=true)
    void eliminarUsuario(@Param("id") Long idUsuario);

}
