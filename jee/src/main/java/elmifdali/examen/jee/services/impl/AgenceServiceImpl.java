package elmifdali.examen.jee.services.impl;

import elmifdali.examen.jee.dtos.AgenceDTO;
import elmifdali.examen.jee.dtos.VehiculeDTO;
import elmifdali.examen.jee.entities.Agence;
import elmifdali.examen.jee.exeptions.ResourceNotFoundException;
import elmifdali.examen.jee.mappers.AgenceMapper;
import elmifdali.examen.jee.mappers.VehiculeMapper;
import elmifdali.examen.jee.repositories.AgenceRepository;
import elmifdali.examen.jee.services.AgenceService;
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
public class AgenceServiceImpl implements AgenceService {

    private final AgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public AgenceDTO creerAgence(AgenceDTO dto) {
        log.info("Création agence: {}", dto.getName());
        Agence agence = agenceMapper.toEntity(dto);
        return agenceMapper.toDTO(agenceRepository.save(agence));
    }

    @Override
    public AgenceDTO modifierAgence(String id, AgenceDTO dto) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        agenceMapper.updateEntity(agence, dto);
        return agenceMapper.toDTO(agenceRepository.save(agence));
    }

    @Override
    public void supprimerAgence(String id) {
        if (!agenceRepository.existsById(id))
            throw new ResourceNotFoundException("Agence", id);
        agenceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO getAgenceById(String id) {
        return agenceMapper.toDTO(agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> getAllAgences() {
        return agenceRepository.findAll().stream()
                .map(agenceMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> rechercherParVille(String ville) {
        return agenceRepository.findByCityIgnoreCase(ville).stream()
                .map(agenceMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> rechercherParNom(String nom) {
        return agenceRepository.findByNameContainingIgnoreCase(nom).stream()
                .map(agenceMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> getVehiculesAgence(String agenceId) {
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        return agence.getVehicles().stream()
                .map(vehiculeMapper::toDTO).collect(Collectors.toList());
    }
}
