package elmifdali.examen.jee.mappers;

import elmifdali.examen.jee.dtos.AgenceDTO;
import elmifdali.examen.jee.entities.Agence;
import org.springframework.stereotype.Component;

@Component
public class AgenceMapper {

    public AgenceDTO toDTO(Agence agence) {
        if (agence == null) return null;
        return AgenceDTO.builder()
                .id(agence.getId())
                .name(agence.getName())
                .address(agence.getAddress())
                .city(agence.getCity())
                .phone(agence.getPhone())
                .vehiclesCount(agence.getVehicles() != null ? agence.getVehicles().size() : 0)
                .build();
    }

    public Agence toEntity(AgenceDTO dto) {
        if (dto == null) return null;
        Agence agence = new Agence();
        if (dto.getId() != null && !dto.getId().isBlank()) {
            agence.setId(dto.getId());
        }
        agence.setName(dto.getName());
        agence.setAddress(dto.getAddress());
        agence.setCity(dto.getCity());
        agence.setPhone(dto.getPhone());
        return agence;
    }

    public void updateEntity(Agence target, AgenceDTO dto) {
        if (dto.getName() != null) target.setName(dto.getName());
        if (dto.getAddress() != null) target.setAddress(dto.getAddress());
        if (dto.getCity() != null) target.setCity(dto.getCity());
        if (dto.getPhone() != null) target.setPhone(dto.getPhone());
    }
}
