package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.repositories.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServicioDestinoRepository servicioDestinoRepository;


    //RF9 - Registrar el final de un viaje.
    //Marca el servicio como finalizado, actualiza la longitud, costo y la disponibilidad del conductor.

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ViajeEntity finalizarViaje(Long idViaje) throws Exception {
        LocalDateTime fin = LocalDateTime.now();

        // 1. Obtener el viaje
        ViajeEntity viaje = viajeRepository.darViaje(idViaje);
        if (viaje == null) {
            throw new IllegalArgumentException("El viaje con ID " + idViaje + " no existe");
        }

        ServicioEntity servicio = viaje.getIdServicio();
        VehiculoEntity vehiculo = viaje.getIdVehiculo();

        if (!"Asignado".equals(servicio.getEstado())) {
            throw new IllegalStateException("El servicio no ha sido asignado, no puede finalizar");
        }

        // 2. Actualizar estado del servicio
        servicio.setEstado("Finalizado");
        servicioRepository.actualizarServicio(
                servicio.getIdServicio(),
                servicio.getIdCliente().getIdUsuario(),
                servicio.getFechaHora(),
                servicio.getTipoServicio(),
                servicio.getNivelRequerido(),
                "Finalizado",
                servicio.getOrden(),
                servicio.getRestaurante(),
                servicio.getIdPuntoPartida().getIdPunto()
        );

        // 3. Calcular longitud del trayecto
        PuntoGeograficoEntity origen = servicio.getIdPuntoPartida();
        List<ServicioDestinoEntity> destinos = servicioDestinoRepository.darDestinosServicio(servicio.getIdServicio());

        double longitud = 0;
        PuntoGeograficoEntity puntoAnterior = origen;
        for (ServicioDestinoEntity destino : destinos) {
            PuntoGeograficoEntity puntoActual = destino.getIdPuntoLlegada();
            longitud += calcularDistancia(puntoAnterior, puntoActual);
            puntoAnterior = puntoActual;
        }

        // 4. Calcular costo total
        Double costo = viaje.getIdTarifa().getPrecioPorKm() * longitud;

        // 5. Actualizar viaje
        viaje.setFechaHoraFin(fin);
        viaje.setLongitudTrayecto(longitud);
        viaje.setCosto(costo);

        viajeRepository.actualizarViaje(
                viaje.getIdViaje(),
                viaje.getFechaHoraInicio(),
                fin,
                longitud,
                viaje.getIdServicio().getIdServicio(),
                viaje.getIdConductor().getIdUsuario(),
                viaje.getIdVehiculo().getIdVehiculo(),
                viaje.getIdTarifa().getIdTarifa(),
                costo
        );

        // 6. Liberar disponibilidad del conductor
        for (DisponibilidadEntity disponibilidad : disponibilidadRepository.darDisponibilidadesVehiculo(vehiculo.getIdVehiculo())) {
            if (!disponibilidad.isDisponible()) {
                disponibilidad.setDisponible("Y");
                Long idVehiculo = disponibilidad.getPk().getVehiculo().getIdVehiculo();
                Long idFranja = disponibilidad.getPk().getFranja().getIdFranja();
                disponibilidadRepository.actualizarDisponibilidadFranja(idVehiculo, idFranja, "Y");
            }
        }

        return viaje;
    }

    // Calcula la distancia entre dos puntos geográficos usando la fórmula de Haversine.
    private double calcularDistancia(PuntoGeograficoEntity p1, PuntoGeograficoEntity p2) {
        final int R = 6371; // Radio de la Tierra en km
        double lat1 = Math.toRadians(p1.getLatitud());
        double lon1 = Math.toRadians(p1.getLongitud());
        double lat2 = Math.toRadians(p2.getLatitud());
        double lon2 = Math.toRadians(p2.getLongitud());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distancia en km
    }
}
