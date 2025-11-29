package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoUsuarioRepository extends MongoRepository<Usuario, String> {

    // Buscar todos los usuarios
    @Override
    List<Usuario> findAll();

    // Buscar usuario por ID
    @Override
    Optional<Usuario> findById(String id);

    // Buscar usuarios por nombre (usando expresión regular para búsqueda parcial)
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    List<Usuario> findByNombreContaining(String nombre);

    // Buscar usuario por correo electrónico
    Optional<Usuario> findByCorreoElectronico(String correo);

    // Buscar usuarios por tipo
    List<Usuario> findByTipoUsuario(String tipoUsuario);

    // Buscar usuario por número de cédula
    Optional<Usuario> findByNumeroCedula(String numeroCedula);

    // Eliminar usuario por ID (ya viene con MongoRepository)
    @Override
    void deleteById(String id);
}
