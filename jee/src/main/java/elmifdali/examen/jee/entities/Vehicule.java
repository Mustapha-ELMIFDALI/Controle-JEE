package elmifdali.examen.jee.entities;
import elmifdali.examen.jee.enums.VehiculeStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String modele;
    private String matricule;
    private Double prixParJour;
    private Date  dateMiseEnService ;

    @Enumerated(EnumType.STRING)
    private VehiculeStatus status;
}
