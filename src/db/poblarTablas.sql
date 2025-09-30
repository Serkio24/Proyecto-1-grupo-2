
-- Borrar datos de todas las tablas (en orden correcto para respetar FKs)
DELETE FROM reviews;
DELETE FROM viajes;
DELETE FROM servicios_destinos;
DELETE FROM servicios;
DELETE FROM disponibilidades;
DELETE FROM franjas_horarias;
DELETE FROM tarifas;
DELETE FROM conductor_vehiculos;
DELETE FROM vehiculos;
DELETE FROM tarjetas_credito;
DELETE FROM puntos_geograficos;
DELETE FROM ciudades;
DELETE FROM usuarios;
COMMIT;

