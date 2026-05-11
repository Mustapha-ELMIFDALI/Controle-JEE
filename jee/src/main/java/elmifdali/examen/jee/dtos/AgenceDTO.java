package elmifdali.examen.jee.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class AgenceDTO {
    private String id;
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    private String address;
    @NotBlank(message = "La ville est obligatoire")
    private String city;
    private String phone;
    private int vehiclesCount;
}
