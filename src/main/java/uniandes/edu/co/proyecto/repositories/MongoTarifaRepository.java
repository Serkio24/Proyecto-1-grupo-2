package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.TarifaEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoTarifaRepository extends MongoRepository<TarifaEntity, String> {

    // Buscar tarifas por tipo de servicio
    List<TarifaEntity> findByTipoServicio(String tipoServicio);

    // Buscar tarifas por nivel
    List<TarifaEntity> findByNivel(String nivel);

    // Buscar tarifa por tipo de servicio y nivel
    Optional<TarifaEntity> findByTipoServicioAndNivel(String tipoServicio, String nivel);
}
