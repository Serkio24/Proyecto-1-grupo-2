package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ViajeController {
    
    @Autowired
    private ViajeRepository viajeRepository;

    @GetMapping("/viajes")
    public String viajes(Model model) {
        model.addAttribute("viajes", viajeRepository.darViajes());
        return model.toString();
    }

    @GetMapping("/viajes/new")
    public String viajeForm(Model model){
        model.addAttribute("viaje", new ViajeEntity());
        return "viajeNuevo";
    }

    @PostMapping("/viajes/new/save")
    public String viajeGuardar(@ModelAttribute ViajeEntity viaje){
        viajeRepository.insertarViaje(
            viaje.getFechaHoraInicio(),
            viaje.getFechaHoraFin(),
            viaje.getLongitudTrayecto(),
            viaje.getIdServicio().getIdServicio(),
            viaje.getIdConductor().getIdUsuario(),
            viaje.getIdVehiculo().getIdVehiculo()
        );
        return "redirect:/viajes";
    }

    @GetMapping("/viajes/{id}")
    public String viajeEditarForm(@PathVariable("id") Long id, Model model){
        ViajeEntity viaje = viajeRepository.darViaje(id);
        if(viaje !=null){
            model.addAttribute("viaje", viaje);
            return "viajeEditar";
        } else{
            return "redirect:/viajes";
        }

    }

    @PostMapping("/viajes/{id}/edit/save")
    public String viajeEditarGuardar(@PathVariable("id") Long id, @ModelAttribute ViajeEntity viaje){
        viajeRepository.actualizarViaje(
            id,
            viaje.getFechaHoraInicio(),
            viaje.getFechaHoraFin(),
            viaje.getLongitudTrayecto(),
            viaje.getIdServicio().getIdServicio(),
            viaje.getIdConductor().getIdUsuario(),
            viaje.getIdVehiculo().getIdVehiculo()
        );
        return "redirect:/viajes";
    }

    @GetMapping("/viajes/{id}/delete")
    public String viajeEliminar(@PathVariable("id") Long id){
        viajeRepository.eliminarViaje(id);
        return "redirect:/viajes";
    }
    
    
}
