package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.ConductorVehiculoEntity;
import uniandes.edu.co.proyecto.entities.ConductorVehiculoPK;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ConductorVehiculoRepository extends JpaRepository<ConductorVehiculoEntity, ConductorVehiculoPK> {

    @Query(value = "SELECT * FROM conductor_vehiculos", nativeQuery = true)
    Collection<ConductorVehiculoEntity> darConductorVehiculos();

    @Query(value = "SELECT * FROM conductor_vehiculos WHERE idConductor = :idConductor AND idVehiculo = :idVehiculo", nativeQuery = true)
    ConductorVehiculoEntity darConductorVehiculoPorId(@Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM conductor_vehiculos WHERE idConductor = :idConductor AND idVehiculo = :idVehiculo", nativeQuery = true)
    void eliminarConductorVehiculo(@Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO conductor_vehiculos (idConductor, idVehiculo) VALUES (:idConductor, :idVehiculo)", nativeQuery = true)
    void insertarConductorVehiculo(@Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE conductor_vehiculos SET idConductor = :idConductorNuevo, idVehiculo = :idVehiculoNuevo WHERE idConductor = :idConductor AND idVehiculo = :idVehiculo", nativeQuery = true)
    void actualizarConductorVehiculo(@Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo, @Param("idConductorNuevo") Long idConductorNuevo, @Param("idVehiculoNuevo") Long idVehiculoNuevo);

    @Query("""
           SELECT CASE WHEN COUNT(cv) > 0 THEN true ELSE false END
           FROM ConductorVehiculoEntity cv
           WHERE cv.pk.idConductor.id = :idConductor
             AND cv.pk.idVehiculo.idVehiculo = :idVehiculo
           """)
    boolean existsByConductorAndVehiculo(
            @Param("idConductor") Long idConductor,
            @Param("idVehiculo") Long idVehiculo
    );

    

}
