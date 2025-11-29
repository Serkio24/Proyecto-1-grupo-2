package uniandes.edu.co.proyecto.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, Long> {

    // create
    @Query("{ $insert: { _id: ?0, nombre: ?1, numeroCelular: ?2, numeroCedula: ?3, correoElectronico: ?4, tipoUsuario: ?5 } }")
    void insertarUsuario(Long id, String nombre, String numeroCelular, String numeroCedula, String correoElectronico, String tipoUsuario);

    // read
    @Query(value = "{}")
    List<UsuarioEntity> buscarTodosLosUsuarios();

    // read
    @Query("{ _id: ?0 }")
    UsuarioEntity buscarPorId(Long id);

    // read
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    List<UsuarioEntity> buscarPorNombre(String nombre);

    // update
    @Query("{ _id: ?0 }")
    @Update("{ $set: { nombre: ?1, numeroCelular: ?2, numeroCedula: ?3, correoElectronico: ?4, tipoUsuario: ?5 } }")
    void actualizarUsuario(Long id, String nombre, String numeroCelular, String numeroCedula, String correoElectronico, String tipoUsuario);

    // delete
    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarUsuarioPorId(Long id);

    // ultimo id
    UsuarioEntity findTopByOrderByIdUsuarioDesc();
}
