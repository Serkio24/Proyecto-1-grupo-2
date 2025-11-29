package uniandes.edu.co.proyecto.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.dto.RFC1DTO;
import uniandes.edu.co.proyecto.dto.RFC1IsolationResultDTO;
import uniandes.edu.co.proyecto.dto.RFC2DTO;
import uniandes.edu.co.proyecto.dto.RFC3DTO;
import uniandes.edu.co.proyecto.dto.RFC4DTO;
import uniandes.edu.co.proyecto.repositories.RFCRepository;

@Service
public class RFCService {

    private final RFCRepository rfcRepository;

    public RFCService(RFCRepository rfcRepository) {
        this.rfcRepository = rfcRepository;
    }

    private List<RFC1DTO> mapRFC1(Collection<Object[]> resultados) {
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
        return viajes;
    }
    //RFC1  READ COMMITTED
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public RFC1IsolationResultDTO rfc1ReadCommitted(Long idUsuario) throws InterruptedException {
        List<RFC1DTO> antes = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        Thread.sleep(30_000);
        List<RFC1DTO> despues = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        return new RFC1IsolationResultDTO(antes, despues);
    }
    //RFC1 SERIALIZABLE
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public RFC1IsolationResultDTO rfc1Serializable(Long idUsuario) throws InterruptedException {
        List<RFC1DTO> antes = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        Thread.sleep(30_000); 
        List<RFC1DTO> despues = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        return new RFC1IsolationResultDTO(antes, despues);
    }
    
    // RFC1 
    @Transactional(readOnly = true)
    public List<RFC1DTO> consultarViajesCliente(Long idUsuario) {
        return mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
    }

    //RFC2
    @Transactional(readOnly = true)
    public List<RFC2DTO> top20Conductores() {
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
        
        return conductores;
    }

    //RFC3
    @Transactional(readOnly = true)
    public List<RFC3DTO> gananciasVehiculoConductor(Long idConductor) {
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
        
        return ganancias;
    }

    //RFC4
    @Transactional(readOnly = true)
    public List<RFC4DTO> distribucionServiciosCiudad(Long idCiudad, String fechaInicio, String fechaFin) {
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
        
        return distribucion;
    }
}
