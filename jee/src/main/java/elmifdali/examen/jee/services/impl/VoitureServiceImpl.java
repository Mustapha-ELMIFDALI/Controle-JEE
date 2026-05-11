package elmifdali.examen.jee.services.impl;

import elmifdali.examen.jee.dtos.VoitureDTO;
import elmifdali.examen.jee.entities.Agence;
import elmifdali.examen.jee.entities.Voiture;
import elmifdali.examen.jee.exeptions.BusinessException;
import elmifdali.examen.jee.exeptions.ResourceNotFoundException;
import elmifdali.examen.jee.mappers.VehiculeMapper;
import elmifdali.examen.jee.repositories.AgenceRepository;
import elmifdali.examen.jee.repositories.VoitureRepository;
import elmifdali.examen.jee.repositories.VehiculeRepository;
import elmifdali.examen.jee.services.VoitureService;
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
public class VoitureServiceImpl implements VoitureService {

    private final VoitureRepository voitureRepository;
    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VoitureDTO creerVoiture(VoitureDTO dto, String agenceId) {
        if (vehiculeRepository.existsByMatricule(dto.getMatricule()))
            throw new BusinessException("Matricule déjà existant: " + dto.getMatricule());
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        Voiture voiture = vehiculeMapper.toVoitureEntity(dto);
        voiture.setAgence(agence);
        return vehiculeMapper.toVoitureDTO(voitureRepository.save(voiture));
    }

    @Override
    public VoitureDTO modifierVoiture(Long id, VoitureDTO dto) {
        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voiture", id.toString()));
        vehiculeMapper.updateVoitureEntity(voiture, dto);
        return vehiculeMapper.toVoitureDTO(voitureRepository.save(voiture));
    }

    @Override
    @Transactional(readOnly = true)
    public VoitureDTO getVoitureById(Long id) {
        return vehiculeMapper.toVoitureDTO(voitureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voiture", id.toString())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoitureDTO> getAllVoitures() {
        return voitureRepository.findAll().stream()
                .map(vehiculeMapper::toVoitureDTO).collect(Collectors.toList());
    }
}
