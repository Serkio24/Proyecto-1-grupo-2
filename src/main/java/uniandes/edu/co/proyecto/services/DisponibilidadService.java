package uniandes.edu.co.proyecto.services;

import java.time.LocalTime;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.FranjaHorariaEntity;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.FranjaHorariaRepository;

@Service
public class DisponibilidadService {
    
    private DisponibilidadRepository disponibilidadRepository;

    private FranjaHorariaRepository franjaHorariaRepository;

    public DisponibilidadService(DisponibilidadRepository disponibilidadRepository, FranjaHorariaRepository franjaHorariaRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.franjaHorariaRepository = franjaHorariaRepository;
    }

    @Transactional
    public void registrarDisponibilidad(Long idConductor, String diaSemana, String horaInicio, String horaFin, String tipoServicio) {
    /** 
        RF 5:
        REGISTRAR LA DISPONIBILIDAD DE UN USUARIO CONDUCTOR Y SU VEHÍCULO PARA UN SERVICIO.

        Un conductor puede seleccionar días y rangos horarios en los cuales prestará diferentes tipos de servicios.
        (transporte de pasajeros, entrega de comida, transporte de mercancías. En caso de prestar el servicio de
        transporte de pasajeros, el modelo y capacidad del vehículo determinarán el nivel de este (Estándar,
        Confort y Large). No es posible tener disponibilidades que se superpongan para un mismo conductor.
        */
        //disponibilidadRepository.registrarDisponibilidad(idConductor, diaSemana, horaInicio, horaFin);
    try {
        if(disponibilidadRepository.validarTraslape(idConductor, diaSemana, horaInicio, horaFin)==0) {
            int existe = franjaHorariaRepository.existeFranja(diaSemana, horaInicio, horaFin);
            FranjaHorariaEntity franja;
            if(existe==0) {
                franja = franjaHorariaRepository.buscarFranjaExistente(diaSemana, horaInicio, horaFin);
            }else {
                // Crear nueva franja si no existe
                franjaHorariaRepository.insertarFranjaHoraria(diaSemana, horaInicio, horaFin, tipoServicio);
                franja = franjaHorariaRepository.buscarFranjaExistente(diaSemana, horaInicio, horaFin);
            }
            disponibilidadRepository.insertarDisponibilidad(idConductor, franja.getIdFranja());
        } else {
            System.out.println("No se puede registrar la disponibilidad porque se traslapa con una existente.");
        }
    } catch (Exception e) {
        System.out.println("Error al registrar la disponibilidad: " + e.getMessage());
    }
    }

    @Transactional
    public void modificarDisponibilidad(
        Long idConductor,
        Long idVehiculoAnt,
        Long idFranjaAnt,
        String diaSemana,
        String horaInicio,
        String horaFin,
        String tipoServicio,
        Long idVehiculoNuevo
    ) {
        // Validar traslape
        boolean hayTraslape = disponibilidadRepository.validarTraslapeExcluyendoActual(
            idConductor, diaSemana, horaInicio, horaFin, idVehiculoAnt, idFranjaAnt
        ) > 0;


        if (hayTraslape) {
            throw new IllegalArgumentException("No se puede modificar: la nueva franja se traslapa con otra disponibilidad existente.");
        }

        // Buscar o crear la nueva franja
        FranjaHorariaEntity franja = franjaHorariaRepository.buscarFranjaExistente(diaSemana, horaInicio, horaFin);
        Long idFranjaNueva;

        if (franja != null) {
            idFranjaNueva = franja.getIdFranja();
        } else {
            franjaHorariaRepository.insertarFranjaHoraria(diaSemana, horaInicio, horaFin, tipoServicio);
            FranjaHorariaEntity nueva = franjaHorariaRepository.buscarFranjaExistente(diaSemana, horaInicio, horaFin);
            idFranjaNueva = nueva.getIdFranja();
        }

        // Actualizar la disponibilidad
        disponibilidadRepository.actualizarDisponibilidad(idVehiculoAnt, idFranjaAnt, idVehiculoNuevo, idFranjaNueva);
    }
}
