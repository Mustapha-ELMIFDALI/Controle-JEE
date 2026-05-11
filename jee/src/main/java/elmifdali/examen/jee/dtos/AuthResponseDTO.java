package elmifdali.examen.jee.dtos;

import lombok.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponseDTO {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String email;
    private String nom;
    private String prenom;
    private Set<String> roles;
}
