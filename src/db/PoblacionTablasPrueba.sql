SELECT * FROM Servicio;

SELECT du.idUsuario AS idConductor, du.nombre, du.numeroCedula, du.correoElectronico, COUNT(s.idServicio) AS totalServicios FROM Servicio s
    JOIN DatosUsuario du ON s.idConductor = du.idUsuario
    GROUP BY du.idUsuario, du.nombre, du.numeroCedula, du.correoElectronico
    ORDER BY totalServicios DESC
    FETCH FIRST 20 ROWS ONLY;