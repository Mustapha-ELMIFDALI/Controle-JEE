package elmifdali.examen.jee.controllers;

import elmifdali.examen.jee.dtos.AgenceDTO;
import elmifdali.examen.jee.dtos.VehiculeDTO;
import elmifdali.examen.jee.services.AgenceService;
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
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences de location")
@SecurityRequirement(name = "bearerAuth")
public class AgenceController {

    private final AgenceService agenceService;

    @GetMapping
    @Operation(summary = "Lister toutes les agences")
    public ResponseEntity<List<AgenceDTO>> getAllAgences(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String nom) {
        if (ville != null) return ResponseEntity.ok(agenceService.rechercherParVille(ville));
        if (nom != null) return ResponseEntity.ok(agenceService.rechercherParNom(nom));
        return ResponseEntity.ok(agenceService.getAllAgences());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une agence par ID")
    public ResponseEntity<AgenceDTO> getAgenceById(@PathVariable String id) {
        return ResponseEntity.ok(agenceService.getAgenceById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une agence")
    public ResponseEntity<AgenceDTO> creerAgence(@Valid @RequestBody AgenceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.creerAgence(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une agence")
    public ResponseEntity<AgenceDTO> modifierAgence(@PathVariable String id, @Valid @RequestBody AgenceDTO dto) {
        return ResponseEntity.ok(agenceService.modifierAgence(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une agence")
    public ResponseEntity<Void> supprimerAgence(@PathVariable String id) {
        agenceService.supprimerAgence(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/vehicules")
    @Operation(summary = "Lister les véhicules d'une agence")
    public ResponseEntity<List<VehiculeDTO>> getVehiculesAgence(@PathVariable String id) {
        return ResponseEntity.ok(agenceService.getVehiculesAgence(id));
    }
}
# ajout controllers rest
