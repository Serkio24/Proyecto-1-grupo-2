package uniandes.edu.co.proyecto.controllers;

import java.util.ArrayList;
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
import uniandes.edu.co.proyecto.repositories.RFCRepository;

@RestController
@RequestMapping("/rfc")
public class RFCController {

    @Autowired
    private RFCRepository rfcRepository;

    //RFC1
    @GetMapping("/viajes/cliente/{idUsuario}")
    public ResponseEntity<Collection<RFC1DTO>> consultarViajesCliente(@PathVariable Long idUsuario) {
        try {
            Collection<Object[]> resultados = rfcRepository.consultarViajesCliente(idUsuario);
            List<RFC1DTO> viajes = new ArrayList<>();
            
            for (Object[] row : resultados) {
                RFC1DTO dto = new RFC1DTO();
                if (row[0] != null) {
                    dto.setIdServicio(((Number) row[0]).longValue());
                } else {
                    dto.setIdServicio(null);
                }
                if (row[1] != null) {
                    dto.setFecha(row[1].toString());
                } else {
                    dto.setFecha(null);
                }
                if (row[2] != null) {
                    dto.setLongitudTrayecto(((Number) row[2]).doubleValue());
                } else {
                    dto.setLongitudTrayecto(null);
                }
                if (row[3] != null) {
                    dto.setCostoTotal(((Number) row[3]).doubleValue());
                } else {
                    dto.setCostoTotal(null);
                }
                if (row[4] != null) {
                    dto.setTipoServicio(row[4].toString());
                } else {
                    dto.setTipoServicio(null);
                }
                if (row[5] != null) {
                    dto.setIdVehiculo(((Number) row[5]).longValue());
                } else {
                    dto.setIdVehiculo(null);
                }
                viajes.add(dto);
            }
            
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //RFC2
    @GetMapping("/top-conductores")
    public ResponseEntity<Collection<RFC2DTO>> top20Conductores() {
        try {
            Collection<Object[]> resultados = rfcRepository.top20Conductores();
            List<RFC2DTO> conductores = new ArrayList<>();
            
            for (Object[] row : resultados) {
                RFC2DTO dto = new RFC2DTO();
                if (row[0] != null) {
                    dto.setIdConductor(((Number) row[0]).longValue());
                } else {
                    dto.setIdConductor(null);
                }
                if (row[1] != null) {
                    dto.setNombre(row[1].toString());
                } else {
                    dto.setNombre(null);
                }
                if (row[2] != null) {
                    dto.setTotalViajes(((Number) row[2]).longValue());
                } else {
                    dto.setTotalViajes(null);
                }
                conductores.add(dto);
            }
            
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
            Collection<Object[]> resultados = rfcRepository.gananciasVehiculoConductor(idConductor);
            List<RFC3DTO> ganancias = new ArrayList<>();
            
            for (Object[] row : resultados) {
                RFC3DTO dto = new RFC3DTO();
                if (row[0] != null) {
                    dto.setIdVehiculo(((Number) row[0]).longValue());
                } else {
                    dto.setIdVehiculo(null);
                }
                if (row[1] != null) {
                    dto.setTipoServicio(row[1].toString());
                } else {
                    dto.setTipoServicio(null);
                }
                if (row[2] != null) {
                    dto.setIdUsuario(((Number) row[2]).longValue());
                } else {
                    dto.setIdUsuario(null);
                }
                if (row[3] != null) {
                    dto.setCostoTotal(((Number) row[3]).doubleValue());
                } else {
                    dto.setCostoTotal(null);
                }
                if (row[4] != null) {
                    dto.setGanancia(((Number) row[4]).doubleValue());
                } else {
                    dto.setGanancia(null);
                }
                ganancias.add(dto);
            }
            
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
            Collection<Object[]> resultados = rfcRepository.distribucionServiciosCiudad(idCiudad, fechaInicio, fechaFin);
            List<RFC4DTO> distribucion = new ArrayList<>();
            
            for (Object[] row : resultados) {
                RFC4DTO dto = new RFC4DTO();
                if (row[0] != null) {
                    dto.setCiudad(row[0].toString());
                } else {
                    dto.setCiudad(null);
                }
                if (row[1] != null) {
                    dto.setTipoServicio(row[1].toString());
                } else {
                    dto.setTipoServicio(null);
                }
                if (row[2] != null) {
                    dto.setNivelRequerido(row[2].toString());
                } else {
                    dto.setNivelRequerido(null);
                }
                if (row[3] != null) {
                    dto.setTotalServicios(((Number) row[3]).longValue());
                } else {
                    dto.setTotalServicios(null);
                }
                if (row[4] != null) {
                    dto.setPorcentaje(((Number) row[4]).doubleValue());
                } else {
                    dto.setPorcentaje(null);
                }
                distribucion.add(dto);
            }
            
            return ResponseEntity.ok(distribucion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
