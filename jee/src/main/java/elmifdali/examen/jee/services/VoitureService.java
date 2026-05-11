package elmifdali.examen.jee.services;

import elmifdali.examen.jee.dtos.VoitureDTO;
import java.util.List;

public interface VoitureService {
    VoitureDTO creerVoiture(VoitureDTO dto, String agenceId);
    VoitureDTO modifierVoiture(Long id, VoitureDTO dto);
    VoitureDTO getVoitureById(Long id);
    List<VoitureDTO> getAllVoitures();
}
