package elmifdali.examen.jee.repositories;

import elmifdali.examen.jee.entities.Motorcycle;
import elmifdali.examen.jee.enums.MotorcycleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    List<Motorcycle> findByTypeMoto(MotorcycleType typeMoto);
}
