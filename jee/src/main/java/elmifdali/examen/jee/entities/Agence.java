package elmifdali.examen.jee.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entité représentant une agence de location de véhicules.
 *
 * Une agence est caractérisée par :
 * - un identifiant unique généré automatiquement sous forme UUID,
 * - un nom,
 * - une adresse,
 * - une ville,
 * - un numéro de téléphone.
 *
 * Une agence peut posséder plusieurs véhicules.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Agence {

    /**
     * Identifiant unique de l'agence.
     * Généré automatiquement sous forme de chaîne UUID.
     */
    @Id
    private String id = UUID.randomUUID().toString();

    /**
     * Nom de l'agence.
     */
    private String name;

    /**
     * Adresse complète de l'agence.
     */
    private String address;

    /**
     * Ville où se situe l'agence.
     */
    private String city;

    /**
     * Numéro de téléphone de l'agence.
     */
    private String phone;

    /**
     * Liste des véhicules appartenant à cette agence.
     *
     * Relation OneToMany :
     * - Une agence peut posséder plusieurs véhicules.
     * - Le champ "agency" dans l'entité Vehicle représente le côté propriétaire.
     * - CascadeType.ALL permet de propager toutes les opérations
     *   (persist, merge, remove, etc.) aux véhicules associés.
     * - orphanRemoval = false signifie qu'un véhicule retiré de la liste
     *   n'est pas automatiquement supprimé de la base de données.
     */
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Vehicule> vehicles = new ArrayList<>();
}
