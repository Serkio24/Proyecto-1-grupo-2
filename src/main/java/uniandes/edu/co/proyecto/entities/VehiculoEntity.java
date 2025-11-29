package uniandes.edu.co.proyecto.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "vehiculos")
public class VehiculoEntity {

    @Id
    private String id;

    private String idConductor;
    private String nivelVehiculo;
    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private String placa;
    private String ciudadExpedicion;
    private Integer capacidadPasajeros;

    private Disponibilidad disponibilidad;
    private List<FranjaHoraria> franjasHorarias = new ArrayList<>();

    public VehiculoEntity() {}

    public VehiculoEntity(String idConductor, String nivelVehiculo, String tipo, String marca,
                   String modelo, String color, String placa, String ciudadExpedicion,
                   Integer capacidadPasajeros) {
        this.idConductor = idConductor;
        this.nivelVehiculo = nivelVehiculo;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.ciudadExpedicion = ciudadExpedicion;
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public static class Disponibilidad {
        private String disponible;

        public Disponibilidad() {
            this.disponible = "Y";
        }

        public Disponibilidad(String disponible) {
            this.disponible = disponible;
        }

        public String getDisponible() { return disponible; }
        public void setDisponible(String disponible) { this.disponible = disponible; }

        public boolean isDisponible() {
            return "Y".equals(disponible);
        }
    }

    public static class FranjaHoraria {
        private Integer diaSemana;
        private String horaInicio;
        private String horaFin;
        private String tipoServicio;

        public FranjaHoraria() {}

        public FranjaHoraria(Integer diaSemana, String horaInicio, String horaFin, String tipoServicio) {
            this.diaSemana = diaSemana;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.tipoServicio = tipoServicio;
        }

        public Integer getDiaSemana() { return diaSemana; }
        public void setDiaSemana(Integer diaSemana) { this.diaSemana = diaSemana; }

        public String getHoraInicio() { return horaInicio; }
        public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

        public String getHoraFin() { return horaFin; }
        public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

        public String getTipoServicio() { return tipoServicio; }
        public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdConductor() { return idConductor; }
    public void setIdConductor(String idConductor) { this.idConductor = idConductor; }

    public String getNivelVehiculo() { return nivelVehiculo; }
    public void setNivelVehiculo(String nivelVehiculo) { this.nivelVehiculo = nivelVehiculo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getCiudadExpedicion() { return ciudadExpedicion; }
    public void setCiudadExpedicion(String ciudadExpedicion) { this.ciudadExpedicion = ciudadExpedicion; }

    public Integer getCapacidadPasajeros() { return capacidadPasajeros; }
    public void setCapacidadPasajeros(Integer capacidadPasajeros) { this.capacidadPasajeros = capacidadPasajeros; }

    public Disponibilidad getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(Disponibilidad disponibilidad) { this.disponibilidad = disponibilidad; }

    public List<FranjaHoraria> getFranjasHorarias() { return franjasHorarias; }
    public void setFranjasHorarias(List<FranjaHoraria> franjasHorarias) { this.franjasHorarias = franjasHorarias; }
}
