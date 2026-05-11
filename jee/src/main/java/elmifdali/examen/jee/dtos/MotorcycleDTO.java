package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.MotorcycleType;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString(callSuper = true)
public class MotorcycleDTO extends VehiculeDTO {
    private int cylindree;
    private MotorcycleType typeMoto;
    private boolean casqueInclus;
}
