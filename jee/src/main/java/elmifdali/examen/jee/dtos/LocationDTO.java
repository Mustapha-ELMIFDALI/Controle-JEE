package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.RentalStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class LocationDTO {
    private String id;
    @NotBlank(message = "Le nom du client est obligatoire")
    private String nomClient;
    @Email @NotBlank
    private String emailClient;
    private String telephoneClient;
    @NotNull private LocalDate dateDebut;
    @NotNull private LocalDate dateFin;
    private LocalDate dateRetourReelle;
    private double prixTotal;
    private RentalStatus statut;
    @NotNull private Long vehiculeId;
    private String marqueVehicule;
    private String modeleVehicule;
}
