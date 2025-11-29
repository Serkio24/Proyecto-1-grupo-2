package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoPuntoGeograficoRepository extends MongoRepository<PuntoGeograficoEntity, String> {

    // Buscar puntos geográficos por nombre
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    List<PuntoGeograficoEntity> findByNombreContaining(String nombre);

    // Buscar puntos geográficos por ciudad
    List<PuntoGeograficoEntity> findByCiudad(String ciudad);

    // Buscar puntos geográficos por dirección
    @Query("{ 'direccionAproximada': { $regex: ?0, $options: 'i' } }")
    List<PuntoGeograficoEntity> findByDireccionContaining(String direccion);
}
