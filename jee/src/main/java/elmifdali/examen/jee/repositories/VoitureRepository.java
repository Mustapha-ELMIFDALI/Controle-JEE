package elmifdali.examen.jee.repositories;

import elmifdali.examen.jee.entities.Voiture;
import elmifdali.examen.jee.enums.TypeCarburant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findByTypeCarburant(TypeCarburant typeCarburant);
}
