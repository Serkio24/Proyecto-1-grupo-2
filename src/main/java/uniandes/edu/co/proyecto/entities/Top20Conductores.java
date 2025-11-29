package uniandes.edu.co.proyecto.entities;

public class Top20Conductores {
    private Long idConductor;
    private int numeroViajes;

    public Top20Conductores() {
    }

    public Top20Conductores(Long idConductor, int numeroViajes) {
        this.idConductor = idConductor;
        this.numeroViajes = numeroViajes;
    }
    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }
    public int getNumeroViajes() {
        return numeroViajes;
    }
    public void setNumeroViajes(int numeroViajes) {
        this.numeroViajes = numeroViajes;
    }

    
}
