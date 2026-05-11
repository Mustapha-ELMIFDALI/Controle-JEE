package elmifdali.examen.jee.entities;

import elmifdali.examen.jee.enums.BoiteVitesse;
import elmifdali.examen.jee.enums.TypeCarburant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "voiture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Voiture extends Vehicule {

    private int nombrePortes;

    @Enumerated(EnumType.STRING)
    private TypeCarburant typeCarburant;

    @Enumerated(EnumType.STRING)
    private BoiteVitesse boiteVitesse;
}
