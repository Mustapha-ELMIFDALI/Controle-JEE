package elmifdali.examen.jee.controllers;

import elmifdali.examen.jee.dtos.MotorcycleDTO;
import elmifdali.examen.jee.services.MotorcycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/motorcycles")
@RequiredArgsConstructor
@Tag(name = "Motorcycles", description = "Gestion des motos")
@SecurityRequirement(name = "bearerAuth")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @GetMapping
    @Operation(summary = "Lister toutes les motos")
    public ResponseEntity<List<MotorcycleDTO>> getAllMotorcycles() {
        return ResponseEntity.ok(motorcycleService.getAllMotorcycles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une moto par ID")
    public ResponseEntity<MotorcycleDTO> getMotorcycleById(@PathVariable Long id) {
        return ResponseEntity.ok(motorcycleService.getMotorcycleById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une moto")
    public ResponseEntity<MotorcycleDTO> creerMotorcycle(@Valid @RequestBody MotorcycleDTO dto,
                                                          @RequestParam String agenceId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(motorcycleService.creerMotorcycle(dto, agenceId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une moto")
    public ResponseEntity<MotorcycleDTO> modifierMotorcycle(@PathVariable Long id,
                                                             @Valid @RequestBody MotorcycleDTO dto) {
        return ResponseEntity.ok(motorcycleService.modifierMotorcycle(id, dto));
    }
}
