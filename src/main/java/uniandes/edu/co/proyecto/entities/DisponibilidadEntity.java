package uniandes.edu.co.proyecto.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "DISPONIBILIDADES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DisponibilidadEntity {

    @Id
    private Long id;           // Mapea al _id de Mongo
    private Long idConductor;  // id del conductor
    private Long idVehiculo;   // id del vehículo

    // Lista de franjas horarias embebidas en el documento
    private List<FranjaHorariaEntity> franjasHorarias;

    public DisponibilidadEntity() {
    }

    public DisponibilidadEntity(Long idConductor, Long idVehiculo, List<FranjaHorariaEntity> franjasHorarias) {
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
        this.franjasHorarias = franjasHorarias;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public List<FranjaHorariaEntity> getFranjasHorarias() {
        return franjasHorarias;
    }

    public void setFranjasHorarias(List<FranjaHorariaEntity> franjasHorarias) {
        this.franjasHorarias = franjasHorarias;
    }
}
