package uniandes.edu.co.proyecto.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniandes.edu.co.proyecto.entities.ConductorVehiculoEntity;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.ServicioDestinoEntity;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.TarjetaCreditoEntity;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.repositories.ConductorVehiculoRepository;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.PuntoGeograficoRepository;
import uniandes.edu.co.proyecto.repositories.ServicioDestinoRepository;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.TarjetaCreditoRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;

@Service
public class ServicioService {

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    @Autowired
    private ConductorVehiculoRepository conductorVehiculoRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private PuntoGeograficoRepository puntoGeograficoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioDestinoRepository servicioDestinoRepository;


    public void solicitarServicio(Long idUsuario, String tipoServicio, String nivelRequerido, Long idPuntoPartida, Long idPuntoDestino) {
        UsuarioEntity usuario= usuarioRepository.findById(idUsuario).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        TarjetaCreditoEntity infoMedioPago = tarjetaCreditoRepository.findByClienteId(idUsuario);
        if(infoMedioPago == null){
            throw new IllegalArgumentException("El usuario no tiene un medio de pago registrado disponible.");
        }

        UsuarioEntity conductor = buscarConductorDisponible(tipoServicio);
        System.out.println("Conductor asignado: " + conductor.getNombre());

        ServicioEntity nuevoServicio = new ServicioEntity(
            usuario,
            java.time.LocalDateTime.now(),
            tipoServicio,
            nivelRequerido,
            "Asignado",
            "",
            "",
            puntoGeograficoRepository.findById(idPuntoPartida).orElseThrow(() -> new IllegalArgumentException("Punto de partida no encontrado."))
        );

        servicioRepository.save(nuevoServicio);
        servicioDestinoRepository.insertarServicioDestino(nuevoServicio.getIdServicio(), idPuntoDestino);
        
    }

    public UsuarioEntity buscarConductorDisponible(String tipoServicio) {
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        String diaSemana = obtenerNombreDia(ahora.getDayOfWeek());
        int horaActual = ahora.getHour();
        System.out.println("Buscando conductor para el día: " + diaSemana + " a la hora: " + horaActual);
        List<DisponibilidadEntity> disponibles = disponibilidadRepository.findDisponibilidadesActivas(
            tipoServicio, diaSemana, horaActual
        );

        if (disponibles.isEmpty()) {
            throw new IllegalStateException("No hay conductores disponibles para el servicio solicitado.");
        }

        DisponibilidadEntity disp = disponibles.get(0);
        Long idVehiculo = disp.getPk().getVehiculo().getIdVehiculo();

        ConductorVehiculoEntity relacion = conductorVehiculoRepository.findByVehiculo(idVehiculo);
        if (relacion == null) {
            throw new IllegalStateException("El vehículo no tiene un conductor asignado.");
        }
        //Actualizar el estado de un conductor para que no pueda requerido por otros servicios
        disp.setDisponible(false);
        disponibilidadRepository.save(disp);
        return relacion.getPk().getIdConductor();
    }

    private String obtenerNombreDia(java.time.DayOfWeek dia) {
        return switch (dia) {
            case MONDAY -> "Lunes";
            case TUESDAY -> "Martes";
            case WEDNESDAY -> "Miércoles";
            case THURSDAY -> "Jueves";
            case FRIDAY -> "Viernes";
            case SATURDAY -> "Sábado";
            case SUNDAY -> "Domingo";
        };
    }
}
