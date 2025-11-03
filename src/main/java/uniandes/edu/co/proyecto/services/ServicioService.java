package uniandes.edu.co.proyecto.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import uniandes.edu.co.proyecto.entities.ConductorVehiculoEntity;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.TarifaEntity;
import uniandes.edu.co.proyecto.entities.TarjetaCreditoEntity;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.repositories.ConductorVehiculoRepository;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.ServicioDestinoRepository;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.TarifaRepository;
import uniandes.edu.co.proyecto.repositories.TarjetaCreditoRepository;
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
    private ServicioDestinoRepository servicioDestinoRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Transactional
    public void solicitarServicio(Long idUsuario, String tipoServicio, String nivelRequerido, Long idPuntoPartida, List<Long> destinos, String orden, String restaurante) {
        LocalDateTime fechaHoraSolicitud = LocalDateTime.now();

        //Inicializar el servicio
        servicioRepository.insertarServicio(idUsuario, fechaHoraSolicitud, tipoServicio, nivelRequerido, "Pendiente", orden, restaurante, idPuntoPartida);
        ServicioEntity servicio = servicioRepository.darUltimoServicio();
        for (Long idDestino : destinos){
            servicioDestinoRepository.insertarServicioDestino(servicio.getIdServicio(), idDestino);
        }

        //validar que tenga medio de pago
        List<TarjetaCreditoEntity> tarjetas = tarjetaCreditoRepository.encontrarTarjetas(idUsuario);
        if (tarjetas.isEmpty()) {
            throw new IllegalArgumentException("El usuario no tiene un medio de pago registrado disponible.");
        }

        ConductorAsignado asignado = buscarConductorDisponible(tipoServicio);
        LocalDateTime fechaHoraAsignacion = LocalDateTime.now();
        UsuarioEntity conductor = asignado.conductor();
        Long idVehiculo = asignado.idVehiculo();
        System.out.println("Conductor asignado: " + conductor.getNombre());

        servicio.setEstado("Asignado");
        servicioRepository.actualizarServicio(servicio.getIdServicio(), servicio.getIdCliente().getIdUsuario(), servicio.getFechaHora(), servicio.getTipoServicio(), servicio.getNivelRequerido(), "Asignado", servicio.getOrden(), servicio.getRestaurante(), servicio.getIdPuntoPartida().getIdPunto());

        LocalDate fechaSolicitud = fechaHoraSolicitud.toLocalDate();
        TarifaEntity tarifa = tarifaRepository.encontrarTarifa(tipoServicio, nivelRequerido, fechaSolicitud);

        viajeRepository.insertarViaje(
                fechaHoraAsignacion,
                null, 
                null,
                servicio.getIdServicio(),
                conductor.getIdUsuario(),
                idVehiculo,
                tarifa.getIdTarifa(),
                null
        );
    }   

    //no sabia que esto existia xd
    public record ConductorAsignado(UsuarioEntity conductor, Long idVehiculo) {}

    @Transactional
    public ConductorAsignado buscarConductorDisponible(String tipoServicio) {
        LocalDateTime ahora = LocalDateTime.now();
        String diaSemana = obtenerNombreDia(ahora.getDayOfWeek());
        int horaActual = ahora.getHour();

        System.out.println("tipoServicio = " + tipoServicio);
        System.out.println("diaSemana = " + diaSemana);
        System.out.println("horaActual = " + horaActual);

        List<DisponibilidadEntity> disponibles = disponibilidadRepository.findDisponibilidadesActivas(tipoServicio, diaSemana, horaActual);

        if (disponibles.isEmpty()) {
            throw new IllegalStateException("No hay conductores disponibles para el servicio solicitado.");
        }

        DisponibilidadEntity disp = disponibles.get(0);
        Long idVehiculo = disp.getPk().getVehiculo().getIdVehiculo();
        Long idFranja = disp.getPk().getFranja().getIdFranja();

        ConductorVehiculoEntity relacion = conductorVehiculoRepository.findByVehiculo(idVehiculo);
        if (relacion == null) {
            throw new IllegalStateException("El vehículo no tiene un conductor asignado.");
        }

        // Marcar como no disponible
        disp.setDisponible("N");
        disponibilidadRepository.actualizarDisponibilidadFranja(idVehiculo, idFranja, "N");
        disponibilidadRepository.save(disp); //esto toca cambiarlo

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
