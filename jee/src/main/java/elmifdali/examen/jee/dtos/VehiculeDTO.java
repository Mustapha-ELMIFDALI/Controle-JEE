package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.VehiculeStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder @ToString
public class VehiculeDTO {
    private Long id;
    @NotBlank(message = "La marque est obligatoire")
    private String marque;
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    @NotBlank(message = "Le matricule est obligatoire")
    private String matricule;
    @NotNull @Positive(message = "Le prix par jour doit être positif")
    private Double prixParJour;
    private Date dateMiseEnService;
    private VehiculeStatus status;
    private String agenceId;
    private String type;
}
