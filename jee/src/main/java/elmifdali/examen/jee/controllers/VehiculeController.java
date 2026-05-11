package elmifdali.examen.jee.controllers;

import elmifdali.examen.jee.dtos.VehiculeDTO;
import elmifdali.examen.jee.enums.VehiculeStatus;
import elmifdali.examen.jee.services.VehiculeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
@SecurityRequirement(name = "bearerAuth")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @GetMapping
    @Operation(summary = "Lister tous les véhicules")
    public ResponseEntity<List<VehiculeDTO>> getAllVehicules(
            @RequestParam(required = false) String agenceId,
            @RequestParam(required = false) String marque,
            @RequestParam(required = false) VehiculeStatus statut) {
        if (agenceId != null) return ResponseEntity.ok(vehiculeService.getVehiculesByAgence(agenceId));
        if (marque != null) return ResponseEntity.ok(vehiculeService.rechercherParMarque(marque));
        if (statut != null) return ResponseEntity.ok(vehiculeService.getVehiculesByStatut(statut));
        return ResponseEntity.ok(vehiculeService.getAllVehicules());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les véhicules disponibles")
    public ResponseEntity<List<VehiculeDTO>> getVehiculesDisponibles(
            @RequestParam(required = false) String agenceId) {
        if (agenceId != null) return ResponseEntity.ok(vehiculeService.getVehiculesDisponiblesParAgence(agenceId));
        return ResponseEntity.ok(vehiculeService.getVehiculesDisponibles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un véhicule par ID")
    public ResponseEntity<VehiculeDTO> getVehiculeById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.getVehiculeById(id));
    }

    @PatchMapping("/{id}/statut")
    @Operation(summary = "Changer le statut d'un véhicule")
    public ResponseEntity<VehiculeDTO> changerStatut(@PathVariable Long id,
                                                      @RequestParam VehiculeStatus statut) {
        return ResponseEntity.ok(vehiculeService.changerStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule")
    public ResponseEntity<Void> supprimerVehicule(@PathVariable Long id) {
        vehiculeService.supprimerVehicule(id);
        return ResponseEntity.noContent().build();
    }
}
