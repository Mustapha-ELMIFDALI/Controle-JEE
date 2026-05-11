package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.VehiculeStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VehiculeDTO {

    private Long id;
    private String marque;
    private String modele;
    private String matricule;
    private Double prixParJour;
    private Date dateMiseEnService;
    private VehiculeStatus status;
}