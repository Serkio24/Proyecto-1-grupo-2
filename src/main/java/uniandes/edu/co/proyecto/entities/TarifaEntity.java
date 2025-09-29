package uniandes.edu.co.proyecto.entities;

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
    private String tipoServicio; //NN, CK(in "Transporte Pasajeros", "Domicilio Comida", "Transporte Paquete")
    private String nivel; //CK(in "Estandar", "Confort", "Large")
    private Double precioPorKm;//NN, CK(precioPorKm > 0)
    private String vigenciaDesde; //NN, CK(vigenciaDesde<vigenciaHasta)
    private String vigenciaHasta;//NN, CK(vigenciaHasta>vigenciaDesde)


    // Constructor con parámetros
    public TarifaEntity(String tipoServicio, String nivel, Double precioPorKm,
                        String vigenciaDesde, String vigenciaHasta) {
        this.tipoServicio = tipoServicio;
        this.nivel = nivel;
        this.precioPorKm = precioPorKm;
        this.vigenciaDesde = vigenciaDesde;
        this.vigenciaHasta = vigenciaHasta;
    }

    // Constructor vacío
    public TarifaEntity() {}

    // Getters
    public Long getIdTarifa() {
        return idTarifa;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public String getNivel() {
        return nivel;
    }

    public Double getPrecioPorKm() {
        return precioPorKm;
    }

    public String getVigenciaDesde() {
        return vigenciaDesde;
    }

    public String getVigenciaHasta() {
        return vigenciaHasta;
    }

    // Setters
    public void setIdTarifa(Long idTarifa) {
        this.idTarifa = idTarifa;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setPrecioPorKm(Double precioPorKm) {
        this.precioPorKm = precioPorKm;
    }

    public void setVigenciaDesde(String vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
    }

    public void setVigenciaHasta(String vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }
}
