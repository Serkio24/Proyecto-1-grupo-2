package uniandes.edu.co.proyecto.entities;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tarifas")
public class TarifaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTarifa;
    private String tipoServicio;
    private String nivel;
    private Double precioPorKm;
    private LocalDate vigenciaDesde;
    private LocalDate vigenciaHasta;

    public TarifaEntity(String tipoServicio, String nivel, Double precioPorKm,
                        LocalDate vigenciaDesde, LocalDate vigenciaHasta) {
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

    public LocalDate getVigenciaDesde() { return vigenciaDesde; }
    public void setVigenciaDesde(LocalDate vigenciaDesde) { this.vigenciaDesde = vigenciaDesde; }

    public LocalDate getVigenciaHasta() { return vigenciaHasta; }
    public void setVigenciaHasta(LocalDate vigenciaHasta) { this.vigenciaHasta = vigenciaHasta; }
}
