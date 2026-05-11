package elmifdali.examen.jee;

import elmifdali.examen.jee.entities.*;
import elmifdali.examen.jee.enums.*;
import elmifdali.examen.jee.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;

@SpringBootApplication
@Slf4j
public class JeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeeApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               AgenceRepository agenceRepository,
                               VoitureRepository voitureRepository,
                               MotorcycleRepository motorcycleRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            // Création des utilisateurs
            if (!userRepository.existsByEmail("admin@location.ma")) {
                AppUser admin = AppUser.builder()
                        .prenom("Mustapha").nom("ELMIFDALI")
                        .email("admin@location.ma")
                        .password(passwordEncoder.encode("admin123"))
                        .roles(Set.of(Role.ROLE_ADMIN))
                        .actif(true).build();
                userRepository.save(admin);

                AppUser employe = AppUser.builder()
                        .prenom("Ahmed").nom("BENALI")
                        .email("employe@location.ma")
                        .password(passwordEncoder.encode("employe123"))
                        .roles(Set.of(Role.ROLE_EMPLOYE))
                        .actif(true).build();
                userRepository.save(employe);

                AppUser client = AppUser.builder()
                        .prenom("Fatima").nom("ZAHRAE")
                        .email("client@location.ma")
                        .password(passwordEncoder.encode("client123"))
                        .roles(Set.of(Role.ROLE_CLIENT))
                        .actif(true).build();
                userRepository.save(client);
                log.info("Utilisateurs créés: admin, employe, client");
            }

            // Création des agences
            if (agenceRepository.count() == 0) {
                Agence agenceCasa = new Agence();
                agenceCasa.setName("AutoLoc Casablanca"); agenceCasa.setCity("Casablanca");
                agenceCasa.setAddress("Boulevard Mohammed V, 20000"); agenceCasa.setPhone("+212522000001");
                agenceRepository.save(agenceCasa);

                Agence agenceRabat = new Agence();
                agenceRabat.setName("AutoLoc Rabat"); agenceRabat.setCity("Rabat");
                agenceRabat.setAddress("Avenue Hassan II, 10000"); agenceRabat.setPhone("+212537000002");
                agenceRepository.save(agenceRabat);

                // Voitures
                Voiture v1 = new Voiture();
                v1.setMarque("Dacia"); v1.setModele("Sandero"); v1.setMatricule("CASA-001");
                v1.setPrixParJour(350.0); v1.setStatus(VehiculeStatus.DISPONIBLE);
                v1.setNombrePortes(5); v1.setTypeCarburant(TypeCarburant.ESSENCE);
                v1.setBoiteVitesse(BoiteVitesse.MANUELLE); v1.setAgence(agenceCasa);
                voitureRepository.save(v1);

                Voiture v2 = new Voiture();
                v2.setMarque("Renault"); v2.setModele("Clio"); v2.setMatricule("CASA-002");
                v2.setPrixParJour(420.0); v2.setStatus(VehiculeStatus.DISPONIBLE);
                v2.setNombrePortes(5); v2.setTypeCarburant(TypeCarburant.DIESEL);
                v2.setBoiteVitesse(BoiteVitesse.AUTOMATIQUE); v2.setAgence(agenceCasa);
                voitureRepository.save(v2);

                Voiture v3 = new Voiture();
                v3.setMarque("Toyota"); v3.setModele("Corolla"); v3.setMatricule("RABAT-001");
                v3.setPrixParJour(500.0); v3.setStatus(VehiculeStatus.DISPONIBLE);
                v3.setNombrePortes(4); v3.setTypeCarburant(TypeCarburant.HYBRIDE);
                v3.setBoiteVitesse(BoiteVitesse.AUTOMATIQUE); v3.setAgence(agenceRabat);
                voitureRepository.save(v3);

                // Motos
                Motorcycle m1 = new Motorcycle();
                m1.setMarque("Yamaha"); m1.setModele("MT-07"); m1.setMatricule("MOTO-001");
                m1.setPrixParJour(280.0); m1.setStatus(VehiculeStatus.DISPONIBLE);
                m1.setCylindree(689); m1.setTypeMoto(MotorcycleType.ROADSTER);
                m1.setCasqueInclus(true); m1.setAgence(agenceCasa);
                motorcycleRepository.save(m1);

                log.info("Données initiales créées avec succès !");
            }
        };
    }
}
# init data
