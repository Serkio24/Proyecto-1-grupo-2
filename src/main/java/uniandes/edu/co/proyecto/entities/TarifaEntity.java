package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tarifas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TarifaEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarifas_seq_gen")
    @SequenceGenerator(name = "tarifas_seq_gen", sequenceName = "tarifas_SEQ", allocationSize = 1)
    private Long idTarifa;
    private String tipoServicio;
    private String nivel;
    private Double precioPorKm;
    @Column(name = "VIGENCIADESDE", columnDefinition = "TIMESTAMP")
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA", columnDefinition = "TIMESTAMP")
    private LocalDateTime vigenciaHasta;


    public TarifaEntity(String tipoServicio, String nivel, Double precioPorKm,
                        LocalDateTime vigenciaDesde, LocalDateTime vigenciaHasta) {
        this.tipoServicio = tipoServicio;
        this.nivel = nivel;
        this.precioPorKm = precioPorKm;
        this.vigenciaDesde = vigenciaDesde;
        this.vigenciaHasta = vigenciaHasta;
    }

    public TarifaEntity() {}

    // Getters y Setters
    public Long getIdTarifa() { return idTarifa; }
    public void setIdTarifa(Long idTarifa) { this.idTarifa = idTarifa; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Double getPrecioPorKm() { return precioPorKm; }
    public void setPrecioPorKm(Double precioPorKm) { this.precioPorKm = precioPorKm; }

    public LocalDateTime getVigenciaDesde() { return vigenciaDesde; }
    public void setVigenciaDesde(LocalDateTime vigenciaDesde) { this.vigenciaDesde = vigenciaDesde; }

    public LocalDateTime getVigenciaHasta() { return vigenciaHasta; }
    public void setVigenciaHasta(LocalDateTime vigenciaHasta) { this.vigenciaHasta = vigenciaHasta; }
}
