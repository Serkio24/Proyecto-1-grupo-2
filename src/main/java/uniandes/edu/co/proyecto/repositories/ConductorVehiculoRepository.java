package uniandes.edu.co.proyecto.repositories;

import uniandes.edu.co.proyecto.entities.ConductorVehiculoEntity;
import uniandes.edu.co.proyecto.entities.ConductorVehiculoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConductorVehiculoRepository extends JpaRepository<ConductorVehiculoEntity, ConductorVehiculoPK> {

    // Buscar todos los vehículos de un conductor
    @Query(value = "SELECT * FROM ConductorVehiculo WHERE idConductor = :idConductor", nativeQuery = true)
    List<ConductorVehiculoEntity> findByConductor(@Param("idConductor") Long idConductor);

    // Buscar la relación específica por conductor y vehículo
    @Query(value = "SELECT * FROM ConductorVehiculo WHERE idConductor = :idConductor AND idVehiculo = :idVehiculo", nativeQuery = true)
    ConductorVehiculoEntity findByConductorAndVehiculo(
            @Param("idConductor") Long idConductor,
            @Param("idVehiculo") Long idVehiculo
    );

    // Eliminar todas las relaciones de un conductor
    @Query(value = "DELETE FROM ConductorVehiculo WHERE idConductor = :idConductor", nativeQuery = true)
    void deleteByConductor(@Param("idConductor") Long idConductor);

    @Query(value = "SELECT * FROM ConductorVehiculo", nativeQuery = true)
    List<ConductorVehiculoEntity> findAllNative();
}
