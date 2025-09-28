--- Consultas y operaciones que es bueno tener a mano para ir mirando
SELECT * FROM Servicio;
SELECT * FROM DetallesServicio;
SELECT * FROM Vehiculo;
SELECT * FROM ciudad;
SELECT * FROM PuntoUbicacion;
SELECT * FROM ConductorVehiculo;
SELECT * FROM Tarifa;
SELECT * FROM DatosUsuario;
COMMIT;

--- RFC1
SELECT  s.idServicio, ds.fecha, ds.longitudTrayecto, ds.costoTotal, ds.tipoServicio, cv.idVehiculo FROM DatosUsuario du
    INNER JOIN Servicio s ON du.IDUSUARIO = s.IDUSUARIO
    INNER JOIN DetallesServicio ds ON s.IDSERVICIO = ds.IDSERVICIO
    INNER JOIN ConductorVehiculo cv ON s.idVehiculo = cv.idVehiculo
    WHERE du.IDUSUARIO = 'U00003';


--- RFC2
SELECT du.idUsuario AS idConductor, du.nombre, COUNT(*) AS totalServiciosPrestados FROM Servicio s
    INNER JOIN DatosUsuario du ON s.IDUSUARIO = du.IDUSUARIO
    INNER JOIN DetallesServicio ds ON s.IDSERVICIO = ds.IDSERVICIO
    INNER JOIN ConductorVehiculo cv ON s.idVehiculo = cv.idVehiculo
    WHERE cv.idUsuario = 'C00002'
    GROUP BY du.idUsuario, du.nombre
    ORDER BY totalServiciosPrestados DESC;



---- RFC3
SELECT SUM(ds.Costototal- ds.comision) AS ganancia, s.idVehiculo, ds.tiposervicio FROM Servicio s
    INNER JOIN DetallesServicio ds ON s.IDSERVICIO = ds.IDSERVICIO
    INNER JOIN ConductorVehiculo cv ON s.idVehiculo = cv.idVehiculo
    WHERE cv.idUsuario = 'C00002'
    GROUP BY ds.tiposervicio ,s.idVehiculo, ds.tiposervicio, cv.idUsuario
    ORDER BY s.idVehiculo DESC;

--- RFC4
SELECT p.idciudad, c.nombre, COUNT(*) AS total_servicios, ROUND( 100 * COUNT(*) / SUM(COUNT(*)) OVER (), 2 ) AS porcentaje  FROM DetallesServicio ds
    INNER JOIN PuntoUbicacion p ON ds.PUNTOUBICACION = p.idPunto
    INNER JOIN Ciudad c ON p.idCiudad = c.idCiudad
    WHERE ds.fecha BETWEEN TO_DATE('2024-01-01', 'YYYY-MM-DD') AND TO_DATE('2025-12-31', 'YYYY-MM-DD')
    GROUP BY p.idciudad, c.nombre
    ORDER BY total_servicios DESC;




