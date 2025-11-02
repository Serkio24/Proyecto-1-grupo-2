package uniandes.edu.co.proyecto.dto;

import java.util.List;

public class RFC1IsolationResultDTO {
    private List<RFC1DTO> antes;
    private List<RFC1DTO> despues;

    public RFC1IsolationResultDTO() {}

    public RFC1IsolationResultDTO(List<RFC1DTO> antes, List<RFC1DTO> despues) {
        this.antes = antes;
        this.despues = despues;
    }

    public List<RFC1DTO> getAntes() {
        return antes;
    }

    public void setAntes(List<RFC1DTO> antes) {
        this.antes = antes;
    }

    public List<RFC1DTO> getDespues() {
        return despues;
    }

    public void setDespues(List<RFC1DTO> despues) {
        this.despues = despues;
    }
}
