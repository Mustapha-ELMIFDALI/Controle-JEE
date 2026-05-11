package elmifdali.examen.jee.dtos;

import elmifdali.examen.jee.enums.RentalStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LocationDTO {

    private String id;

    private String nomClient;
    private String emailClient;
    private String telephoneClient;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateRetourReelle;

    private double prixTotal;

    private RentalStatus statut;

    /**
     * Référence simplifiée du véhicule
     * (évite d’exposer toute l’entité Vehicule)
     */
    private Long vehiculeId;
}