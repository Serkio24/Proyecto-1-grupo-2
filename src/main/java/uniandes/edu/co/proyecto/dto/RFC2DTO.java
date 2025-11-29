package uniandes.edu.co.proyecto.dto;

public class RFC2DTO {
    private Long idConductor;
    private String nombre;
    private Long totalViajes;

    public RFC2DTO() {
    }

    public RFC2DTO(Long idConductor, String nombre, Long totalViajes) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.totalViajes = totalViajes;
    }

    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTotalViajes() {
        return totalViajes;
    }

    public void setTotalViajes(Long totalViajes) {
        this.totalViajes = totalViajes;
    }
}
