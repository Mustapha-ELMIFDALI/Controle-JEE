package elmifdali.examen.jee.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginRequestDTO {
    @Email @NotBlank
    private String email;
    @NotBlank
    private String password;
}
