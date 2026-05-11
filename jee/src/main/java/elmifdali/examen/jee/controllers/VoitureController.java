package elmifdali.examen.jee.controllers;

import elmifdali.examen.jee.dtos.VoitureDTO;
import elmifdali.examen.jee.services.VoitureService;
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
@RequestMapping("/api/voitures")
@RequiredArgsConstructor
@Tag(name = "Voitures", description = "Gestion des voitures")
@SecurityRequirement(name = "bearerAuth")
public class VoitureController {

    private final VoitureService voitureService;

    @GetMapping
    @Operation(summary = "Lister toutes les voitures")
    public ResponseEntity<List<VoitureDTO>> getAllVoitures() {
        return ResponseEntity.ok(voitureService.getAllVoitures());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une voiture par ID")
    public ResponseEntity<VoitureDTO> getVoitureById(@PathVariable Long id) {
        return ResponseEntity.ok(voitureService.getVoitureById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une voiture")
    public ResponseEntity<VoitureDTO> creerVoiture(@Valid @RequestBody VoitureDTO dto,
                                                    @RequestParam String agenceId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voitureService.creerVoiture(dto, agenceId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une voiture")
    public ResponseEntity<VoitureDTO> modifierVoiture(@PathVariable Long id,
                                                       @Valid @RequestBody VoitureDTO dto) {
        return ResponseEntity.ok(voitureService.modifierVoiture(id, dto));
    }
}
