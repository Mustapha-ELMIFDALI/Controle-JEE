package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.MotorcycleType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder @ToString(callSuper = true)
public class MotorcycleDTO extends VehiculeDTO {
    private int cylindree;
    private MotorcycleType typeMoto;
    private boolean casqueInclus;
}
