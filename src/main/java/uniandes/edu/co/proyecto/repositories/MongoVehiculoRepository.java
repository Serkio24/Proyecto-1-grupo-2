package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.Vehiculo;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoVehiculoRepository extends MongoRepository<Vehiculo, String> {

    // Buscar vehículos por conductor
    List<Vehiculo> findByIdConductor(String idConductor);

    // Buscar vehículos por nivel
    List<Vehiculo> findByNivelVehiculo(String nivelVehiculo);

    // Buscar vehículo por placa
    Optional<Vehiculo> findByPlaca(String placa);

    // Buscar vehículos disponibles
    @Query("{ 'disponibilidad.disponible': 'Y' }")
    List<Vehiculo> findVehiculosDisponibles();

    // Buscar vehículos por tipo
    List<Vehiculo> findByTipo(String tipo);

    // Buscar vehículos por ciudad de expedición
    List<Vehiculo> findByCiudadExpedicion(String ciudadExpedicion);
}
