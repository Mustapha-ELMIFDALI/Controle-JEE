package elmifdali.examen.jee.services;

import elmifdali.examen.jee.dtos.VehiculeDTO;
import elmifdali.examen.jee.enums.VehiculeStatus;
import java.util.List;

public interface VehiculeService {
    VehiculeDTO getVehiculeById(Long id);
    List<VehiculeDTO> getAllVehicules();
    List<VehiculeDTO> getVehiculesByStatut(VehiculeStatus statut);
    List<VehiculeDTO> getVehiculesByAgence(String agenceId);
    List<VehiculeDTO> getVehiculesDisponibles();
    List<VehiculeDTO> getVehiculesDisponiblesParAgence(String agenceId);
    List<VehiculeDTO> rechercherParMarque(String marque);
    VehiculeDTO changerStatut(Long vehiculeId, VehiculeStatus statut);
    void supprimerVehicule(Long id);
}
