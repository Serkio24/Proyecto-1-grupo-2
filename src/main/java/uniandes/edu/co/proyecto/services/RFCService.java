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

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public RFC1IsolationResultDTO rfc1ReadCommitted(Long idUsuario) throws InterruptedException {
        List<RFC1DTO> antes = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        Thread.sleep(30_000);
        List<RFC1DTO> despues = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        return new RFC1IsolationResultDTO(antes, despues);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public RFC1IsolationResultDTO rfc1Serializable(Long idUsuario) throws InterruptedException {
        List<RFC1DTO> antes = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        Thread.sleep(30_000); 
        List<RFC1DTO> despues = mapRFC1(rfcRepository.consultarViajesCliente(idUsuario));
        return new RFC1IsolationResultDTO(antes, despues);
    }
}
