package elmifdali.examen.jee.services.impl;

import elmifdali.examen.jee.dtos.LocationDTO;
import elmifdali.examen.jee.entities.Location;
import elmifdali.examen.jee.entities.Vehicule;
import elmifdali.examen.jee.enums.RentalStatus;
import elmifdali.examen.jee.enums.VehiculeStatus;
import elmifdali.examen.jee.exeptions.BusinessException;
import elmifdali.examen.jee.exeptions.ResourceNotFoundException;
import elmifdali.examen.jee.mappers.LocationMapper;
import elmifdali.examen.jee.repositories.LocationRepository;
import elmifdali.examen.jee.repositories.VehiculeRepository;
import elmifdali.examen.jee.services.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final VehiculeRepository vehiculeRepository;
    private final LocationMapper locationMapper;

    @Override
    public LocationDTO creerLocation(LocationDTO dto) {
        Vehicule vehicule = vehiculeRepository.findById(dto.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", dto.getVehiculeId().toString()));

        if (vehicule.getStatus() != VehiculeStatus.DISPONIBLE)
            throw new BusinessException("Le véhicule n'est pas disponible");

        if (!isVehiculeDisponible(dto.getVehiculeId(), dto.getDateDebut(), dto.getDateFin()))
            throw new BusinessException("Le véhicule est déjà réservé pour ces dates");

        if (dto.getDateFin().isBefore(dto.getDateDebut()))
            throw new BusinessException("La date de fin doit être après la date de début");

        double prix = calculerPrixTotal(dto.getVehiculeId(), dto.getDateDebut(), dto.getDateFin());
        dto.setPrixTotal(prix);

        Location location = locationMapper.toEntity(dto, vehicule);
        location.setStatut(RentalStatus.CONFIRMEE);
        return locationMapper.toDTO(locationRepository.save(location));
    }

    @Override
    public LocationDTO demarrerLocation(String id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", id));
        if (location.getStatut() != RentalStatus.CONFIRMEE)
            throw new BusinessException("La location doit être confirmée pour être démarrée");
        location.setStatut(RentalStatus.EN_COURS);
        location.getVehicule().setStatus(VehiculeStatus.LOUE);
        return locationMapper.toDTO(locationRepository.save(location));
    }

    @Override
    public LocationDTO terminerLocation(String id, LocalDate dateRetour) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", id));
        if (location.getStatut() != RentalStatus.EN_COURS)
            throw new BusinessException("La location doit être en cours pour être terminée");
        location.setStatut(RentalStatus.TERMINEE);
        location.setDateRetourReelle(dateRetour != null ? dateRetour : LocalDate.now());
        location.getVehicule().setStatus(VehiculeStatus.DISPONIBLE);
        return locationMapper.toDTO(locationRepository.save(location));
    }

    @Override
    public LocationDTO annulerLocation(String id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", id));
        if (location.getStatut() == RentalStatus.TERMINEE || location.getStatut() == RentalStatus.EN_COURS)
            throw new BusinessException("Impossible d'annuler une location terminée ou en cours");
        location.setStatut(RentalStatus.ANNULEE);
        return locationMapper.toDTO(locationRepository.save(location));
    }

    @Override
    @Transactional(readOnly = true)
    public LocationDTO getLocationById(String id) {
        return locationMapper.toDTO(locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationDTO> getLocationsByVehicule(Long vehiculeId) {
        return locationRepository.findByVehiculeId(vehiculeId).stream()
                .map(locationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationDTO> getLocationsActives() {
        return locationRepository.findByStatut(RentalStatus.EN_COURS).stream()
                .map(locationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationDTO> getLocationsByClient(String email) {
        return locationRepository.findByEmailClient(email).stream()
                .map(locationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isVehiculeDisponible(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
        return locationRepository.findOverlappingLocations(vehiculeId, dateDebut, dateFin).isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public double calculerPrixTotal(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", vehiculeId.toString()));
        long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
        if (jours <= 0) jours = 1;
        return vehicule.getPrixParJour() * jours;
    }
}
# ajout services
