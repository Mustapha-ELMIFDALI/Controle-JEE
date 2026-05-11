package elmifdali.examen.jee.entities;

import elmifdali.examen.jee.enums.RentalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "vehicule")
public class Location {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    @Column(nullable = false)
    private String nomClient;

    @Email
    @Column(nullable = false)
    private String emailClient;

    private String telephoneClient;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateFin;

    private LocalDate dateRetourReelle;

    @Column(nullable = false)
    private double prixTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus statut = RentalStatus.EN_ATTENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
}
