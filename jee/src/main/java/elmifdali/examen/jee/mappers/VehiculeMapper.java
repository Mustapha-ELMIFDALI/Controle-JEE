package elmifdali.examen.jee.mappers;

import elmifdali.examen.jee.dtos.*;
import elmifdali.examen.jee.entities.*;
import org.springframework.stereotype.Component;

@Component
public class VehiculeMapper {

    public VehiculeDTO toDTO(Vehicule vehicule) {
        if (vehicule == null) return null;
        if (vehicule instanceof Voiture v) return toVoitureDTO(v);
        if (vehicule instanceof Motorcycle m) return toMotorcycleDTO(m);
        return toVehiculeDTO(vehicule);
    }

    public VoitureDTO toVoitureDTO(Voiture voiture) {
        if (voiture == null) return null;
        VoitureDTO dto = new VoitureDTO();
        fillCommonFields(dto, voiture);
        dto.setNombrePortes(voiture.getNombrePortes());
        dto.setTypeCarburant(voiture.getTypeCarburant());
        dto.setBoiteVitesse(voiture.getBoiteVitesse());
        dto.setType("VOITURE");
        return dto;
    }

    public MotorcycleDTO toMotorcycleDTO(Motorcycle motorcycle) {
        if (motorcycle == null) return null;
        MotorcycleDTO dto = new MotorcycleDTO();
        fillCommonFields(dto, motorcycle);
        dto.setCylindree(motorcycle.getCylindree());
        dto.setTypeMoto(motorcycle.getTypeMoto());
        dto.setCasqueInclus(motorcycle.isCasqueInclus());
        dto.setType("MOTORCYCLE");
        return dto;
    }

    public VehiculeDTO toVehiculeDTO(Vehicule vehicule) {
        if (vehicule == null) return null;
        VehiculeDTO dto = new VehiculeDTO();
        fillCommonFields(dto, vehicule);
        return dto;
    }

    public Voiture toVoitureEntity(VoitureDTO dto) {
        if (dto == null) return null;
        Voiture voiture = new Voiture();
        fillCommonEntityFields(voiture, dto);
        voiture.setNombrePortes(dto.getNombrePortes());
        voiture.setTypeCarburant(dto.getTypeCarburant());
        voiture.setBoiteVitesse(dto.getBoiteVitesse());
        return voiture;
    }

    public Motorcycle toMotorcycleEntity(MotorcycleDTO dto) {
        if (dto == null) return null;
        Motorcycle motorcycle = new Motorcycle();
        fillCommonEntityFields(motorcycle, dto);
        motorcycle.setCylindree(dto.getCylindree());
        motorcycle.setTypeMoto(dto.getTypeMoto());
        motorcycle.setCasqueInclus(dto.isCasqueInclus());
        return motorcycle;
    }

    public void updateVoitureEntity(Voiture target, VoitureDTO dto) {
        updateCommonFields(target, dto);
        if (dto.getNombrePortes() > 0) target.setNombrePortes(dto.getNombrePortes());
        if (dto.getTypeCarburant() != null) target.setTypeCarburant(dto.getTypeCarburant());
        if (dto.getBoiteVitesse() != null) target.setBoiteVitesse(dto.getBoiteVitesse());
    }

    public void updateMotorcycleEntity(Motorcycle target, MotorcycleDTO dto) {
        updateCommonFields(target, dto);
        if (dto.getCylindree() > 0) target.setCylindree(dto.getCylindree());
        if (dto.getTypeMoto() != null) target.setTypeMoto(dto.getTypeMoto());
        target.setCasqueInclus(dto.isCasqueInclus());
    }

    private void fillCommonFields(VehiculeDTO dto, Vehicule vehicule) {
        dto.setId(vehicule.getId());
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setMatricule(vehicule.getMatricule());
        dto.setPrixParJour(vehicule.getPrixParJour());
        dto.setDateMiseEnService(vehicule.getDateMiseEnService());
        dto.setStatus(vehicule.getStatus());
        if (vehicule.getAgence() != null) dto.setAgenceId(vehicule.getAgence().getId());
    }

    private void fillCommonEntityFields(Vehicule vehicule, VehiculeDTO dto) {
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setMatricule(dto.getMatricule());
        vehicule.setPrixParJour(dto.getPrixParJour());
        vehicule.setDateMiseEnService(dto.getDateMiseEnService());
        vehicule.setStatus(dto.getStatus() != null ? dto.getStatus() : elmifdali.examen.jee.enums.VehiculeStatus.DISPONIBLE);
    }

    private void updateCommonFields(Vehicule vehicule, VehiculeDTO dto) {
        if (dto.getMarque() != null) vehicule.setMarque(dto.getMarque());
        if (dto.getModele() != null) vehicule.setModele(dto.getModele());
        if (dto.getMatricule() != null) vehicule.setMatricule(dto.getMatricule());
        if (dto.getPrixParJour() != null && dto.getPrixParJour() > 0) vehicule.setPrixParJour(dto.getPrixParJour());
        if (dto.getDateMiseEnService() != null) vehicule.setDateMiseEnService(dto.getDateMiseEnService());
        if (dto.getStatus() != null) vehicule.setStatus(dto.getStatus());
    }
}
# correction mappers
