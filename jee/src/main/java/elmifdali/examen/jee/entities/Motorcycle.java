package elmifdali.examen.jee.entities;

import elmifdali.examen.jee.enums.MotorcycleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motorcycle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Motorcycle extends Vehicule {

    private int cylindree;

    @Enumerated(EnumType.STRING)
    private MotorcycleType typeMoto;

    private boolean casqueInclus;
}
