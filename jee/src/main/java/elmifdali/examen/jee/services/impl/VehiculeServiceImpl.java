package elmifdali.examen.jee.services.impl;

import elmifdali.examen.jee.dtos.VehiculeDTO;
import elmifdali.examen.jee.entities.Vehicule;
import elmifdali.examen.jee.enums.VehiculeStatus;
import elmifdali.examen.jee.exeptions.ResourceNotFoundException;
import elmifdali.examen.jee.mappers.VehiculeMapper;
import elmifdali.examen.jee.repositories.VehiculeRepository;
import elmifdali.examen.jee.services.VehiculeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO getVehiculeById(Long id) {
        return vehiculeMapper.toDTO(vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", id.toString())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getAllVehicules() {
        return vehiculeRepository.findAll().stream()
                .map(vehiculeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getVehiculesByStatut(VehiculeStatus statut) {
        return vehiculeRepository.findByStatus(statut).stream()
                .map(vehiculeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getVehiculesByAgence(String agenceId) {
        return vehiculeRepository.findByAgenceId(agenceId).stream()
                .map(vehiculeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getVehiculesDisponibles() {
        return vehiculeRepository.findByStatus(VehiculeStatus.DISPONIBLE).stream()
                .map(vehiculeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getVehiculesDisponiblesParAgence(String agenceId) {
        return vehiculeRepository.findByAgenceIdAndStatus(agenceId, VehiculeStatus.DISPONIBLE).stream()
                .map(vehiculeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> rechercherParMarque(String marque) {
        return vehiculeRepository.findByMarqueIgnoreCase(marque).stream()
                .map(vehiculeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public VehiculeDTO changerStatut(Long vehiculeId, VehiculeStatus statut) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", vehiculeId.toString()));
        vehicule.setStatus(statut);
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    @Override
    public void supprimerVehicule(Long id) {
        if (!vehiculeRepository.existsById(id))
            throw new ResourceNotFoundException("Véhicule", id.toString());
        vehiculeRepository.deleteById(id);
    }
}
