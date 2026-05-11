package elmifdali.examen.jee.services.impl;

import elmifdali.examen.jee.dtos.MotorcycleDTO;
import elmifdali.examen.jee.entities.Agence;
import elmifdali.examen.jee.entities.Motorcycle;
import elmifdali.examen.jee.exeptions.BusinessException;
import elmifdali.examen.jee.exeptions.ResourceNotFoundException;
import elmifdali.examen.jee.mappers.VehiculeMapper;
import elmifdali.examen.jee.repositories.AgenceRepository;
import elmifdali.examen.jee.repositories.MotorcycleRepository;
import elmifdali.examen.jee.repositories.VehiculeRepository;
import elmifdali.examen.jee.services.MotorcycleService;
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
public class MotorcycleServiceImpl implements MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public MotorcycleDTO creerMotorcycle(MotorcycleDTO dto, String agenceId) {
        if (vehiculeRepository.existsByMatricule(dto.getMatricule()))
            throw new BusinessException("Matricule déjà existant: " + dto.getMatricule());
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        Motorcycle motorcycle = vehiculeMapper.toMotorcycleEntity(dto);
        motorcycle.setAgence(agence);
        return vehiculeMapper.toMotorcycleDTO(motorcycleRepository.save(motorcycle));
    }

    @Override
    public MotorcycleDTO modifierMotorcycle(Long id, MotorcycleDTO dto) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorcycle", id.toString()));
        vehiculeMapper.updateMotorcycleEntity(motorcycle, dto);
        return vehiculeMapper.toMotorcycleDTO(motorcycleRepository.save(motorcycle));
    }

    @Override
    @Transactional(readOnly = true)
    public MotorcycleDTO getMotorcycleById(Long id) {
        return vehiculeMapper.toMotorcycleDTO(motorcycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorcycle", id.toString())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotorcycleDTO> getAllMotorcycles() {
        return motorcycleRepository.findAll().stream()
                .map(vehiculeMapper::toMotorcycleDTO).collect(Collectors.toList());
    }
}
