package elmifdali.examen.jee.services;

import elmifdali.examen.jee.dtos.LocationDTO;
import java.time.LocalDate;
import java.util.List;

public interface LocationService {
    LocationDTO creerLocation(LocationDTO dto);
    LocationDTO demarrerLocation(String id);
    LocationDTO terminerLocation(String id, LocalDate dateRetour);
    LocationDTO annulerLocation(String id);
    LocationDTO getLocationById(String id);
    List<LocationDTO> getAllLocations();
    List<LocationDTO> getLocationsByVehicule(Long vehiculeId);
    List<LocationDTO> getLocationsActives();
    List<LocationDTO> getLocationsByClient(String email);
    boolean isVehiculeDisponible(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin);
    double calculerPrixTotal(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin);
}
