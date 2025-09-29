package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorPK;

public interface UsuarioConductorRepository extends JpaRepository<UsuarioConductorEntity, UsuarioConductorPK> {

    //Create, remarcar cada parametro con anotacion param
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuarios_conductor (idConductor, idVehiculo) VALUES (:idConductor, :idVehiculo)", nativeQuery = true)
    void insertarUsuarioConductor(@Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    //Read: Get all
    //indicar que consulta es y que es nativa
    @Query(value = "SELECT * FROM usuarios_conductor", nativeQuery = true)
    Collection<UsuarioConductorEntity> darUsuariosConductores();

    //Read: Get one, le pasamos idConductor e idVehiculo por parametro
    @Query(value = "SELECT * FROM usuarios_conductor WHERE idConductor = :idConductor AND idVehiculo = :idVehiculo", nativeQuery = true)
    UsuarioConductorEntity darUsuarioConductor(@Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    //Update
    @Modifying
    @Transactional
    @Query(value= "UPDATE usuarios_conductor SET idConductor = :idConductor, idVehiculo = :idVehiculo WHERE idConductor = :idConductorAnt AND idVehiculo = :idVehiculoAnt", nativeQuery = true)
    void actualizarUsuarioConductor(@Param("idConductorAnt") Long idConductorAnt, @Param("idVehiculoAnt") Long idVehiculoAnt, @Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    //Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usuarios_conductor WHERE idConductor = :idConductor AND idVehiculo = :idVehiculo", nativeQuery = true)
    void eliminarUsuarioConductor(@Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);
}
