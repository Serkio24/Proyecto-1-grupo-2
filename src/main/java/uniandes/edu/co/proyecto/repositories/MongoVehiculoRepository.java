package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoVehiculoRepository extends MongoRepository<VehiculoEntity, String> {

    // Buscar vehículos por conductor
    List<VehiculoEntity> findByIdConductor(String idConductor);

    // Buscar vehículos por nivel
    List<VehiculoEntity> findByNivelVehiculo(String nivelVehiculo);

    // Buscar vehículo por placa
    Optional<VehiculoEntity> findByPlaca(String placa);

    // Buscar vehículos disponibles
    @Query("{ 'disponibilidad.disponible': 'Y' }")
    List<VehiculoEntity> findVehiculosDisponibles();

    // Buscar vehículos por tipo
    List<VehiculoEntity> findByTipo(String tipo);

    // Buscar vehículos por ciudad de expedición
    List<VehiculoEntity> findByCiudadExpedicion(String ciudadExpedicion);
}
