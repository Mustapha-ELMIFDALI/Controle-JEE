package elmifdali.examen.jee.entities;


import elmifdali.examen.jee.enums.MotorcycleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@ToString(callSuper = true)
public class Motorcycle extends Vehicule {

    private int cylindree;

    @Enumerated(EnumType.STRING)
    private MotorcycleType typeMoto;

    private boolean casqueInclus;
}
