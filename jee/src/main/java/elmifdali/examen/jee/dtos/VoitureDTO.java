package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.BoiteVitesse;
import elmifdali.examen.jee.enums.TypeCarburant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class VoitureDTO extends VehiculeDTO {

    private int numberOfDoors;

    private TypeCarburant TypeCarburant;

    private BoiteVitesse BoiteVitesse;
}