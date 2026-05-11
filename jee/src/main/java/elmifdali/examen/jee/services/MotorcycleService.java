package elmifdali.examen.jee.services;

import elmifdali.examen.jee.dtos.MotorcycleDTO;
import java.util.List;

public interface MotorcycleService {
    MotorcycleDTO creerMotorcycle(MotorcycleDTO dto, String agenceId);
    MotorcycleDTO modifierMotorcycle(Long id, MotorcycleDTO dto);
    MotorcycleDTO getMotorcycleById(Long id);
    List<MotorcycleDTO> getAllMotorcycles();
}
