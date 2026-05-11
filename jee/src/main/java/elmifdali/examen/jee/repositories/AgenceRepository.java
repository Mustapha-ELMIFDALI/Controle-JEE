package elmifdali.examen.jee.repositories;

import elmifdali.examen.jee.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgenceRepository extends JpaRepository<Agence, String> {
    List<Agence> findByCityIgnoreCase(String city);
    List<Agence> findByNameContainingIgnoreCase(String name);
}
