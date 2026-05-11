package elmifdali.examen.jee.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AgenceDTO {

    private String id;
    private String name;
    private String address;
    private String city;
    private String phone;

    /**
     * Liste des véhicules de l'agence (DTO simplifié).
     * Tu peux remplacer VehiculeDTO selon ton projet.
     */
    private List<VehiculeDTO> vehicles;
}