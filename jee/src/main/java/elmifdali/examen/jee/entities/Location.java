package elmifdali.examen.jee.entities;

import java.time.LocalDate;
import java.util.UUID;

import elmifdali.examen.jee.enums.RentalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    @Id
    private String id = UUID.randomUUID().toString();

    private String nomClient;
    private String emailClient;
    private String telephoneClient;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateRetourReelle;

    private double prixTotal;

    @Enumerated(EnumType.STRING)
    private RentalStatus statut;

    @ManyToOne
    private Vehicule vehicule;
}