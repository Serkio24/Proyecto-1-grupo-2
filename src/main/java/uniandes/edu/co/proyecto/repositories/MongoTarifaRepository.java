package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.Tarifa;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoTarifaRepository extends MongoRepository<Tarifa, String> {

    // Buscar tarifas por tipo de servicio
    List<Tarifa> findByTipoServicio(String tipoServicio);

    // Buscar tarifas por nivel
    List<Tarifa> findByNivel(String nivel);

    // Buscar tarifa por tipo de servicio y nivel
    Optional<Tarifa> findByTipoServicioAndNivel(String tipoServicio, String nivel);
}
