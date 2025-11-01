package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;

public interface RFCRepository extends JpaRepository<UsuarioEntity, Long> {

    // RFC1
    @Query(value = "SELECT s.idServicio, v.fechaHoraInicio AS fecha, ROUND(v.longitudTrayecto, 2) AS longitudTrayecto, " +
                   "ROUND((v.longitudTrayecto * t.precioPorKm), 2) AS costoTotal, s.tipoServicio, v.idVehiculo " +
                   "FROM usuarios du " +
                   "INNER JOIN servicios s ON s.idCliente = du.idUsuario " +
                   "INNER JOIN viajes v ON s.idServicio = v.idServicio " +
                   "INNER JOIN tarifas t ON s.tipoServicio = t.tipoServicio AND t.nivel = s.nivelRequerido " +
                   "AND v.fechaHoraInicio BETWEEN t.vigenciaDesde AND t.vigenciaHasta " +
                   "WHERE du.idUsuario = :idUsuario", 
           nativeQuery = true)
    Collection<Object[]> consultarViajesCliente(@Param("idUsuario") Long idUsuario);

    // RFC2
    @Query(value = "SELECT u.idUsuario AS idConductor, u.nombre, COUNT(v.idViaje) AS total_viajes " +
                   "FROM viajes v " +
                   "INNER JOIN usuarios u ON v.idConductor = u.idUsuario " +
                   "GROUP BY u.idUsuario, u.nombre " +
                   "ORDER BY total_viajes DESC " +
                   "FETCH FIRST 20 ROWS ONLY", 
           nativeQuery = true)
    Collection<Object[]> top20Conductores();

    // RFC3
    @Query(value = "SELECT v.idVehiculo, s.tipoServicio, v.idConductor AS idUsuario, " +
                   "ROUND(SUM(v.longitudTrayecto * t.precioPorKm)) AS costo_total, " +
                   "ROUND(SUM(v.longitudTrayecto * t.precioPorKm) * (1 - 0.2)) AS ganancia " +
                   "FROM viajes v " +
                   "INNER JOIN servicios s ON v.idServicio = s.idServicio " +
                   "INNER JOIN tarifas t ON s.tipoServicio = t.tipoServicio AND t.nivel = s.nivelRequerido " +
                   "AND v.fechaHoraInicio BETWEEN t.vigenciaDesde AND t.vigenciaHasta " +
                   "WHERE v.idConductor = :idConductor " +
                   "GROUP BY v.idVehiculo, s.tipoServicio, v.idConductor " +
                   "ORDER BY v.idVehiculo, s.tipoServicio", 
           nativeQuery = true)
    Collection<Object[]> gananciasVehiculoConductor(@Param("idConductor") Long idConductor);

    // RFC4
    @Query(value = "SELECT c.nombre AS ciudad, s.tipoServicio, s.nivelRequerido, COUNT(*) AS total_servicios, " +
                   "ROUND(100 * COUNT(*) / SUM(COUNT(*)) OVER (), 2) AS porcentaje " +
                   "FROM servicios s " +
                   "INNER JOIN puntos_geograficos p ON s.idPuntoPartida = p.idPunto " +
                   "INNER JOIN ciudades c ON p.idCiudad = c.idCiudad " +
                   "WHERE c.idCiudad = :idCiudad AND s.fechaHora BETWEEN TO_TIMESTAMP(:fechaInicio,'YYYY-MM-DD HH24:MI:SS.FF3') " +
                   "AND TO_TIMESTAMP(:fechaFin,'YYYY-MM-DD HH24:MI:SS.FF3') " +
                   "GROUP BY c.nombre, s.tipoServicio, s.nivelRequerido " +
                   "ORDER BY porcentaje DESC", 
           nativeQuery = true)
    Collection<Object[]> distribucionServiciosCiudad(
        @Param("idCiudad") Long idCiudad, 
        @Param("fechaInicio") String fechaInicio,
        @Param("fechaFin") String fechaFin
    );
}
