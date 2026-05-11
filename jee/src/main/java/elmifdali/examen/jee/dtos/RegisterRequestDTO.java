package elmifdali.examen.jee.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank private String prenom;
    @NotBlank private String nom;
    @Email @NotBlank private String email;
    @NotBlank @Size(min = 6) private String password;
}
