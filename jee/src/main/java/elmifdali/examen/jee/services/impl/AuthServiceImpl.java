package elmifdali.examen.jee.services.impl;

import elmifdali.examen.jee.dtos.AuthResponseDTO;
import elmifdali.examen.jee.dtos.LoginRequestDTO;
import elmifdali.examen.jee.dtos.RegisterRequestDTO;
import elmifdali.examen.jee.entities.AppUser;
import elmifdali.examen.jee.enums.Role;
import elmifdali.examen.jee.exeptions.BusinessException;
import elmifdali.examen.jee.repositories.UserRepository;
import elmifdali.examen.jee.security.service.JwtService;
import elmifdali.examen.jee.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        AppUser user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return AuthResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new BusinessException("Email déjà utilisé: " + request.getEmail());
        AppUser user = AppUser.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.ROLE_CLIENT))
                .actif(true)
                .build();
        userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        return AuthResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .roles(Set.of("ROLE_CLIENT"))
                .build();
    }
}
