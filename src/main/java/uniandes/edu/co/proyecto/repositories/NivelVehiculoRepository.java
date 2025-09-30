package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.NivelVehiculoEntity;
import uniandes.edu.co.proyecto.entities.NivelVehiculoPK;

public interface NivelVehiculoRepository extends JpaRepository<NivelVehiculoEntity, NivelVehiculoPK> {

    // Create
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO niveles_vehiculo (modelo, capacidadPasajeros, nivel) VALUES (:modelo, :capacidadPasajeros, :nivel)", nativeQuery = true)
    void insertarNivelVehiculo(@Param("modelo") String modelo, @Param("capacidadPasajeros") int capacidadPasajeros, @Param("nivel") String nivel);

    // Read: Get all
    @Query(value = "SELECT * FROM niveles_vehiculo", nativeQuery = true)
    Collection<NivelVehiculoEntity> darNivelesVehiculo();

    // Read: Get one
    @Query(value = "SELECT * FROM niveles_vehiculo WHERE modelo = :modelo AND capacidadPasajeros = :capacidadPasajeros", nativeQuery = true)
    NivelVehiculoEntity darNivelVehiculo(@Param("modelo") String modelo, @Param("capacidadPasajeros") int capacidadPasajeros);

    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE niveles_vehiculo SET nivel = :nivel WHERE modelo = :modelo AND capacidadPasajeros = :capacidadPasajeros", nativeQuery = true)
    void actualizarNivelVehiculo(@Param("modelo") String modelo, @Param("capacidadPasajeros") int capacidadPasajeros, @Param("nivel") String nivel);

    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM niveles_vehiculo WHERE modelo = :modelo AND capacidadPasajeros = :capacidadPasajeros", nativeQuery = true)
    void eliminarNivelVehiculo(@Param("modelo") String modelo, @Param("capacidadPasajeros") int capacidadPasajeros);
}
