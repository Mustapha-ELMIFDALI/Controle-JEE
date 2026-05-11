package elmifdali.examen.jee.services;

import elmifdali.examen.jee.dtos.AgenceDTO;
import elmifdali.examen.jee.dtos.VehiculeDTO;
import java.util.List;

public interface AgenceService {
    AgenceDTO creerAgence(AgenceDTO dto);
    AgenceDTO modifierAgence(String id, AgenceDTO dto);
    void supprimerAgence(String id);
    AgenceDTO getAgenceById(String id);
    List<AgenceDTO> getAllAgences();
    List<AgenceDTO> rechercherParVille(String ville);
    List<AgenceDTO> rechercherParNom(String nom);
    List<VehiculeDTO> getVehiculesAgence(String agenceId);
}
