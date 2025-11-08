package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.repositories.ConductorVehiculoRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;


@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConductorVehiculoRepository conductorVehiculoRepository;


    //RF4 - Registrar un vehículo para un usuario conductor
    public VehiculoEntity registrarVehiculoParaConductor(VehiculoEntity vehiculo, Long conductorId) {
        // Validar que el conductor exista y tenga tipo "Conductor"
        UsuarioEntity conductor = usuarioRepository.darUsuario(conductorId);
        if (conductor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El conductor con ID " + conductorId + " no existe.");
        }
        if (!"Conductor".equalsIgnoreCase(conductor.getTipo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no tiene rol de conductor.");
        }

        // Insertar el vehículo
        vehiculoRepository.insertarVehiculo(
                vehiculo.getTipo(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getColor(),
                vehiculo.getPlaca(),
                vehiculo.getCiudadExpedicion(),
                vehiculo.getCapacidadPasajeros()
        );

        // Obtener el último vehículo insertado
        VehiculoEntity vehiculoCreado = vehiculoRepository.darUltimoVehiculo();
        if (vehiculoCreado == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo obtener el vehículo recién creado.");
        }

        // Registrar relación conductor–vehículo
        conductorVehiculoRepository.insertarConductorVehiculo(conductor.getIdUsuario(), vehiculoCreado.getIdVehiculo());

        return vehiculoCreado;
    }
}