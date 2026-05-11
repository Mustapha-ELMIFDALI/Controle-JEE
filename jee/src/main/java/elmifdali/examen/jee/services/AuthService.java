package elmifdali.examen.jee.services;

import elmifdali.examen.jee.dtos.AuthResponseDTO;
import elmifdali.examen.jee.dtos.LoginRequestDTO;
import elmifdali.examen.jee.dtos.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO register(RegisterRequestDTO request);
}
