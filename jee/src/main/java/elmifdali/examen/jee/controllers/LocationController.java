package elmifdali.examen.jee.controllers;

import elmifdali.examen.jee.dtos.LocationDTO;
import elmifdali.examen.jee.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "Gestion des locations de véhicules")
@SecurityRequirement(name = "bearerAuth")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    @Operation(summary = "Lister toutes les locations")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/actives")
    @Operation(summary = "Lister les locations actives")
    public ResponseEntity<List<LocationDTO>> getLocationsActives() {
        return ResponseEntity.ok(locationService.getLocationsActives());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une location par ID")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable String id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @GetMapping("/vehicule/{vehiculeId}")
    @Operation(summary = "Historique des locations d'un véhicule")
    public ResponseEntity<List<LocationDTO>> getLocationsByVehicule(@PathVariable Long vehiculeId) {
        return ResponseEntity.ok(locationService.getLocationsByVehicule(vehiculeId));
    }

    @GetMapping("/client")
    @Operation(summary = "Locations d'un client par email")
    public ResponseEntity<List<LocationDTO>> getLocationsByClient(@RequestParam String email) {
        return ResponseEntity.ok(locationService.getLocationsByClient(email));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle location")
    public ResponseEntity<LocationDTO> creerLocation(@Valid @RequestBody LocationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.creerLocation(dto));
    }

    @PatchMapping("/{id}/demarrer")
    @Operation(summary = "Démarrer une location")
    public ResponseEntity<LocationDTO> demarrerLocation(@PathVariable String id) {
        return ResponseEntity.ok(locationService.demarrerLocation(id));
    }

    @PatchMapping("/{id}/terminer")
    @Operation(summary = "Terminer une location")
    public ResponseEntity<LocationDTO> terminerLocation(@PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRetour) {
        return ResponseEntity.ok(locationService.terminerLocation(id, dateRetour));
    }

    @PatchMapping("/{id}/annuler")
    @Operation(summary = "Annuler une location")
    public ResponseEntity<LocationDTO> annulerLocation(@PathVariable String id) {
        return ResponseEntity.ok(locationService.annulerLocation(id));
    }

    @GetMapping("/disponibilite")
    @Operation(summary = "Vérifier la disponibilité d'un véhicule")
    public ResponseEntity<Map<String, Object>> verifierDisponibilite(
            @RequestParam Long vehiculeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        boolean disponible = locationService.isVehiculeDisponible(vehiculeId, dateDebut, dateFin);
        double prix = locationService.calculerPrixTotal(vehiculeId, dateDebut, dateFin);
        return ResponseEntity.ok(Map.of("disponible", disponible, "prixTotal", prix));
    }
}
