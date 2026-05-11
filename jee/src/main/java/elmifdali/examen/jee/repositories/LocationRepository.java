package elmifdali.examen.jee.repositories;

import elmifdali.examen.jee.entities.Location;
import elmifdali.examen.jee.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, String> {
    List<Location> findByVehiculeId(Long vehiculeId);
    List<Location> findByStatut(RentalStatus statut);
    List<Location> findByEmailClient(String emailClient);

    @Query("SELECT l FROM Location l WHERE l.vehicule.id = :vehiculeId " +
           "AND l.statut IN ('CONFIRMEE', 'EN_COURS') " +
           "AND l.dateDebut <= :dateFin AND l.dateFin >= :dateDebut")
    List<Location> findOverlappingLocations(@Param("vehiculeId") Long vehiculeId,
                                             @Param("dateDebut") LocalDate dateDebut,
                                             @Param("dateFin") LocalDate dateFin);
}
