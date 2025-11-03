package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.RFC1DTO;
import uniandes.edu.co.proyecto.dto.RFC2DTO;
import uniandes.edu.co.proyecto.dto.RFC3DTO;
import uniandes.edu.co.proyecto.dto.RFC4DTO;
import uniandes.edu.co.proyecto.dto.RFC1IsolationResultDTO;
import uniandes.edu.co.proyecto.services.RFCService;

@RestController
@RequestMapping("/rfc")
public class RFCController {

    @Autowired
    private RFCService rfcService;

    //RFC1
    @GetMapping("/viajes/cliente/{idUsuario}")
    public ResponseEntity<Collection<RFC1DTO>> consultarViajesCliente(@PathVariable Long idUsuario) {
        try {
            List<RFC1DTO> viajes = rfcService.consultarViajesCliente(idUsuario);
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //RFC1 READ COMMITTED
    @GetMapping("/viajes/cliente/read-committed/{idUsuario}")
    public ResponseEntity<RFC1IsolationResultDTO> rfc1ReadCommitted(@PathVariable Long idUsuario) {
        try {
            RFC1IsolationResultDTO result = rfcService.rfc1ReadCommitted(idUsuario);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //RFC1 SERIALIZABLE
    @GetMapping("/viajes/cliente/serializable/{idUsuario}")
    public ResponseEntity<RFC1IsolationResultDTO> rfc1Serializable(@PathVariable Long idUsuario) {
        try {
            RFC1IsolationResultDTO result = rfcService.rfc1Serializable(idUsuario);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //RFC2
    @GetMapping("/top-conductores")
    public ResponseEntity<Collection<RFC2DTO>> top20Conductores() {
        try {
            List<RFC2DTO> conductores = rfcService.top20Conductores();
            return ResponseEntity.ok(conductores);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //RFC3
    @GetMapping("/ganancias/conductor/{idConductor}")
    public ResponseEntity<Collection<RFC3DTO>> gananciasVehiculoConductor(@PathVariable Long idConductor) {
        try {
            List<RFC3DTO> ganancias = rfcService.gananciasVehiculoConductor(idConductor);
            return ResponseEntity.ok(ganancias);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //RFC4
    @GetMapping("/distribucion/servicios")
    public ResponseEntity<Collection<RFC4DTO>> distribucionServiciosCiudad(
            @RequestParam Long idCiudad,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            List<RFC4DTO> distribucion = rfcService.distribucionServiciosCiudad(idCiudad, fechaInicio, fechaFin);
            return ResponseEntity.ok(distribucion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
