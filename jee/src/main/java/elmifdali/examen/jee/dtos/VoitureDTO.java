package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.BoiteVitesse;
import elmifdali.examen.jee.enums.TypeCarburant;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder @ToString(callSuper = true)
public class VoitureDTO extends VehiculeDTO {
    private int nombrePortes;
    private TypeCarburant typeCarburant;
    private BoiteVitesse boiteVitesse;
}
