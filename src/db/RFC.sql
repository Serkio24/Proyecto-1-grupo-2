

---RFC 1
SELECT s.idServicio, v.fechaHoraInicio AS fecha, ROUND(v.longitudTrayecto, 2) AS longitudTrayecto,
ROUND((v.longitudTrayecto * t.precioPorKm), 2) AS costoTotal, s.tipoServicio, v.idVehiculo FROM usuarios du
INNER JOIN servicios s ON s.idCliente = du.idUsuario
INNER JOIN viajes v ON s.idServicio = v.idServicio
INNER JOIN tarifas t ON s.tipoServicio = t.tipoServicio AND t.nivel = s.nivelRequerido AND v.fechaHoraInicio 
BETWEEN t.vigenciaDesde AND t.vigenciaHasta
WHERE du.idUsuario = 239;


---RFC 2
SELECT u.idUsuario AS idConductor, u.nombre, COUNT(v.idViaje) AS total_viajes FROM viajes v
INNER JOIN usuarios u ON v.idConductor = u.idUsuario
GROUP BY u.idUsuario, u.nombre
ORDER BY total_viajes DESC
FETCH FIRST 20 ROWS ONLY; 

---RFC 3
SELECT v.idVehiculo, s.tipoServicio, v.idConductor AS idUsuario, ROUND(SUM(v.longitudTrayecto * t.precioPorKm)) AS costo_total,
ROUND(SUM(v.longitudTrayecto * t.precioPorKm) * (1 - 0.2)) AS ganancia FROM viajes v
INNER JOIN servicios s ON v.idServicio = s.idServicio
INNER JOIN tarifas t ON s.tipoServicio = t.tipoServicio AND t.nivel = s.nivelRequerido
AND v.fechaHoraInicio BETWEEN t.vigenciaDesde AND t.vigenciaHasta
WHERE v.idConductor = 1
GROUP BY v.idVehiculo, s.tipoServicio, v.idConductor
ORDER BY v.idVehiculo, s.tipoServicio;


---RFC 4
SELECT c.nombre AS ciudad, s.tipoServicio, s.nivelRequerido, COUNT(*) AS total_servicios,
 ROUND(100 * COUNT(*) / SUM(COUNT(*)) OVER (), 2) AS porcentaje FROM servicios s
INNER JOIN puntos_geograficos p ON s.idPuntoPartida = p.idPunto
INNER JOIN ciudades c ON p.idCiudad = c.idCiudad
WHERE c.idCiudad = 10 AND s.fechaHora BETWEEN TO_TIMESTAMP('2024-07-04 23:54:14.000','YYYY-MM-DD HH24:MI:SS.FF3') 
AND TO_TIMESTAMP('2025-09-12 03:41:28.000','YYYY-MM-DD HH24:MI:SS.FF3')
GROUP BY c.nombre, s.tipoServicio, s.nivelRequerido
ORDER BY porcentaje DESC; 


