package elmifdali.examen.jee.repositories;

import elmifdali.examen.jee.entities.Vehicule;
import elmifdali.examen.jee.enums.VehiculeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Optional<Vehicule> findByMatricule(String matricule);
    List<Vehicule> findByStatus(VehiculeStatus status);
    List<Vehicule> findByAgenceId(String agenceId);
    List<Vehicule> findByAgenceIdAndStatus(String agenceId, VehiculeStatus status);
    List<Vehicule> findByMarqueIgnoreCase(String marque);
    boolean existsByMatricule(String matricule);
}
