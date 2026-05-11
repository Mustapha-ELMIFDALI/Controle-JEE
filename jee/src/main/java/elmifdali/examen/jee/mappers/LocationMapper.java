package elmifdali.examen.jee.mappers;

import elmifdali.examen.jee.dtos.LocationDTO;
import elmifdali.examen.jee.entities.Location;
import elmifdali.examen.jee.entities.Vehicule;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationDTO toDTO(Location location) {
        if (location == null) return null;
        LocationDTO dto = LocationDTO.builder()
                .id(location.getId())
                .nomClient(location.getNomClient())
                .emailClient(location.getEmailClient())
                .telephoneClient(location.getTelephoneClient())
                .dateDebut(location.getDateDebut())
                .dateFin(location.getDateFin())
                .dateRetourReelle(location.getDateRetourReelle())
                .prixTotal(location.getPrixTotal())
                .statut(location.getStatut())
                .build();
        if (location.getVehicule() != null) {
            dto.setVehiculeId(location.getVehicule().getId());
            dto.setMarqueVehicule(location.getVehicule().getMarque());
            dto.setModeleVehicule(location.getVehicule().getModele());
        }
        return dto;
    }

    public Location toEntity(LocationDTO dto, Vehicule vehicule) {
        if (dto == null) return null;
        Location location = new Location();
        location.setNomClient(dto.getNomClient());
        location.setEmailClient(dto.getEmailClient());
        location.setTelephoneClient(dto.getTelephoneClient());
        location.setDateDebut(dto.getDateDebut());
        location.setDateFin(dto.getDateFin());
        location.setPrixTotal(dto.getPrixTotal());
        location.setStatut(dto.getStatut() != null ? dto.getStatut() : elmifdali.examen.jee.enums.RentalStatus.EN_ATTENTE);
        location.setVehicule(vehicule);
        return location;
    }
}
