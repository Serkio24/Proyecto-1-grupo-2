package uniandes.edu.co.proyecto.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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
import uniandes.edu.co.proyecto.repositories.ViajeRepository;

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

    @Autowired
    private ViajeRepository viajeRepository;

    @Transactional
    public void solicitarServicio(Long idUsuario, String tipoServicio, String nivelRequerido, Long idPuntoPartida, Long idPuntoDestino) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        TarjetaCreditoEntity infoMedioPago = tarjetaCreditoRepository.findByClienteId(idUsuario);
        if (infoMedioPago == null) {
            throw new IllegalArgumentException("El usuario no tiene un medio de pago registrado disponible.");
        }

        ConductorAsignado asignado = buscarConductorDisponible(tipoServicio);
        UsuarioEntity conductor = asignado.conductor();
        Long idVehiculo = asignado.idVehiculo();
        System.out.println("Conductor asignado: " + conductor.getNombre());
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        ServicioEntity nuevoServicio = new ServicioEntity(
                usuario,
                fechaHoraActual,
                tipoServicio,
                nivelRequerido,
                "Asignado",
                "",
                "",
                puntoGeograficoRepository.findById(idPuntoPartida)
                        .orElseThrow(() -> new IllegalArgumentException("Punto de partida no encontrado."))
        );

        nuevoServicio = servicioRepository.saveAndFlush(nuevoServicio);

        if (!puntoGeograficoRepository.existsById(idPuntoDestino)) {
            throw new IllegalArgumentException("Punto destino no encontrado.");
        }

        servicioDestinoRepository.insertarServicioDestino(nuevoServicio.getIdServicio(), idPuntoDestino);
        Double longi=0.0;
        viajeRepository.insertarViaje(
                fechaHoraActual,
                fechaHoraActual.plusHours(1),
                longi,
                nuevoServicio.getIdServicio(),
                conductor.getIdUsuario(),
                idVehiculo
        );
    }   

    public record ConductorAsignado(UsuarioEntity conductor, Long idVehiculo) {}

    @Transactional
    public ConductorAsignado buscarConductorDisponible(String tipoServicio) {
        LocalDateTime ahora = LocalDateTime.now();
        String diaSemana = obtenerNombreDia(ahora.getDayOfWeek());
        int horaActual = ahora.getHour();

        System.out.println("tipoServicio = " + tipoServicio);
        System.out.println("diaSemana = " + diaSemana);
        System.out.println("horaActual = " + horaActual);

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

        // Marcar como no disponible
        disp.setDisponible("N");
        disponibilidadRepository.save(disp);

        return new ConductorAsignado(relacion.getPk().getIdConductor(), idVehiculo);
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
