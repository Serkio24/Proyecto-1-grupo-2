package uniandes.edu.co.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;

@Controller
public class CiudadController {

    @Autowired
    private CiudadRepository ciudadRepository;

    //Get
    @GetMapping("/ciudades")
    public String bares(Model model){
        model.addAttribute("ciudades", ciudadRepository.darCiudades());
        return "ciudades";
    }

    //Post
    @GetMapping("/ciudades/new")
    public String ciudadForm(Model model){
        model.addAttribute("bar", new CiudadEntity());
        return "ciudadNueva";
    }

    @PostMapping("/ciudades/new/save")
    public String ciudadGuardar(@ModelAttribute CiudadEntity ciudad){
        ciudadRepository.insertarCiudad(ciudad.getNombre());
        return "redirect:/ciudades";
    }

    //Update
    @GetMapping("/ciudades/{id}/edit")
    public String ciudadEditarForm(@PathVariable("id") Long id, Model model){
        CiudadEntity ciudad = ciudadRepository.darCiudad(id);
        if (ciudad != null){
            model.addAttribute("ciudad", ciudad);
            return "ciudadEditar";
        }
        else{
            return "redirect:/ciudades";
        }
    }

    @PostMapping("/ciudades/{id}/edit/save")
    public String ciudadEditarGuardar(@PathVariable("id") Long id, @ModelAttribute CiudadEntity ciudad){
        ciudadRepository.actualizarCiudad(id, ciudad.getNombre());
        return "redirect:/ciudades";
    }

    //Delete
    @GetMapping("ciudades/{id}/delete")
    public String ciudadEliminar(@PathVariable("id") Long id){
        ciudadRepository.eliminarCiudad(id);
        return "redirect:/ciudades";
    }

}
