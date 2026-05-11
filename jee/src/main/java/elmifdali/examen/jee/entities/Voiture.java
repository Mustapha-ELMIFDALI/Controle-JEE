package elmifdali.examen.jee.entities;


import elmifdali.examen.jee.enums.TypeCarburant;
import elmifdali.examen.jee.enums.BoiteVitesse;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entité représentant une voiture.
 *
 * Une voiture est un véhicule qui possède des caractéristiques supplémentaires :
 * - le nombre de portes,
 * - le type de carburant (Essence, Diesel, Hybride, Électrique),
 * - le type de boîte de vitesse (Manuelle ou Automatique).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Car extends Vehicule {

    /**
     * Nombre de portes de la voiture.
     */
    private int numberOfDoors;

    /**
     * Type de carburant utilisé par la voiture.
     */
    private FuelType fuelType;

    /**
     * Type de boîte de vitesse de la voiture.
     */
    private GearboxType gearboxType;
}
