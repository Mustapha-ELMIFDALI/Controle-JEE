# 📋 Rapport d'Examen - Contrôle Architecture Distribuée et Middleware

<div align="center">

![ENSET Logo](docs/images/1.png)

**ENSET Mohammedia – Université Hassan II de Casablanca**  
**Master SDIA – Architecture Distribuée et Middleware**  
**Professeur : Pr. Mohamed YOUSSFI**

---

| Informations | |
|---|---|
| **Étudiant** | Mustapha ELMIFDALI |
| **Filière** | Master SDIA |
| **Module** | Architecture Distribuée et Middleware |
| **Date** | 11 Mai 2022 |
| **Sujet** | Application de Gestion de Location de Véhicules |

</div>

---

## 📑 Table des Matières

1. [Introduction](#introduction)
2. [Analyse des Besoins](#analyse-des-besoins)
3. [Architecture Technique Backend](#architecture-technique-backend)
4. [Diagramme de Classes Backend](#diagramme-de-classes-backend)
5. [Entités JPA](#entités-jpa)
6. [Couche DAO – Repositories](#couche-dao--repositories)
7. [Couche Service](#couche-service)
8. [REST API Controllers](#rest-api-controllers)
9. [Sécurité JWT avec Spring Security](#sécurité-jwt-avec-spring-security)
10. [Documentation Swagger / OpenAPI](#documentation-swagger--openapi)
11. [Base de Données MySQL](#base-de-données-mysql)
12. [Tests réalisés](#tests-réalisés)
13. [Difficultés rencontrées et solutions](#difficultés-rencontrées-et-solutions)
14. [Améliorations apportées](#améliorations-apportées)
15. [Conclusion](#conclusion)

---

## Introduction

Ce rapport présente le développement backend d'une application de **gestion de location de véhicules en agences**, réalisée dans le cadre du contrôle du module *Architecture Distribuée et Middleware* du Master SDIA à l'ENSET Mohammedia.

L'application est développée avec **Spring Boot 3.3**, **Spring Security + JWT**, **Spring Data JPA** et **MySQL**, selon les principes d'architecture **REST** et de **microservices**.

---

## Analyse des Besoins

### Règles métier

- Une **agence** possède plusieurs véhicules
- Chaque **véhicule** appartient à une seule agence
- Un véhicule peut être une **voiture** ou une **moto** (héritage)
- Chaque véhicule peut avoir plusieurs **locations**
- Chaque **location** concerne un seul véhicule
- Le prix total est calculé automatiquement selon le nombre de jours

### Acteurs du système

| Rôle | Permissions |
|------|-------------|
| **ROLE_ADMIN** | Accès total (CRUD agences, véhicules, locations, utilisateurs) |
| **ROLE_EMPLOYE** | Gestion agences, véhicules, locations |
| **ROLE_CLIENT** | Création de location, consultation de ses locations |

---

## Architecture Technique Backend

![Architecture Backend](docs/images/2.png)

```
┌──────────────────────────────────────────────────────┐
│                   CLIENT (Postman/Angular)            │
└──────────────────────────┬───────────────────────────┘
                           │ HTTP/HTTPS
┌──────────────────────────▼───────────────────────────┐
│              Spring Boot Application                  │
│  ┌─────────────────────────────────────────────────┐ │
│  │           Spring Security + JWT Filter          │ │
│  └────────────────────┬────────────────────────────┘ │
│  ┌─────────────────────▼──────────────────────────┐  │
│  │           REST Controllers (API Layer)          │  │
│  │  AgenceController │ VehiculeController          │  │
│  │  VoitureController│ MotorcycleController        │  │
│  │  LocationController│ AuthController             │  │
│  └────────────────────┬───────────────────────────┘  │
│  ┌─────────────────────▼──────────────────────────┐  │
│  │           Service Layer (Business Logic)        │  │
│  │  AgenceService │ VehiculeService                │  │
│  │  LocationService│ VoitureService                │  │
│  │  MotorcycleService│ AuthService                 │  │
│  └────────────────────┬───────────────────────────┘  │
│  ┌─────────────────────▼──────────────────────────┐  │
│  │         DAO Layer (Spring Data JPA)             │  │
│  │  AgenceRepository │ VehiculeRepository          │  │
│  │  LocationRepository│ UserRepository             │  │
│  └────────────────────┬───────────────────────────┘  │
└───────────────────────┼──────────────────────────────┘
                        │ JPA / Hibernate
┌───────────────────────▼──────────────────────────────┐
│                   MySQL Database                      │
│         location_agence (base de données)             │
└──────────────────────────────────────────────────────┘
```

### Technologies utilisées

| Technologie | Version | Rôle |
|---|---|---|
| Spring Boot | 3.3.0 | Framework principal |
| Spring Security | 6.x | Sécurité |
| JJWT | 0.12.5 | Tokens JWT |
| Spring Data JPA | 3.3.0 | Accès données |
| Hibernate | 6.x | ORM |
| MySQL | 8.x | Base de données |
| Lombok | Latest | Réduction boilerplate |
| SpringDoc OpenAPI | 2.5.0 | Documentation Swagger |
| Java | 21 | Langage |

---

## Diagramme de Classes Backend

![Diagramme de Classes](docs/images/3.png)

```
                    ┌─────────────┐
                    │   Agence    │
                    │─────────────│
                    │ id: String  │
                    │ name: String│
                    │ address     │
                    │ city: String│
                    │ phone       │
                    └──────┬──────┘
                           │ 1
                           │ @OneToMany
                           │ *
               ┌───────────┴───────────┐
               │       Vehicule        │
               │───────────────────────│
               │ id: Long              │
               │ marque: String        │
               │ modele: String        │
               │ matricule: String     │
               │ prixParJour: Double   │
               │ dateMiseEnService     │
               │ status: VehiculeStatus│
               └──────┬────────────────┘
                      │ @Inheritance(JOINED)
          ┌───────────┴───────────┐
          │                       │
   ┌──────▼──────┐        ┌───────▼──────┐
   │   Voiture   │        │  Motorcycle  │
   │─────────────│        │──────────────│
   │ nombrePortes│        │ cylindree    │
   │ typeCarburant│       │ typeMoto     │
   │ boiteVitesse│        │ casqueInclus │
   └─────────────┘        └──────────────┘

               Vehicule *──────────────── 1..* Location
                              @ManyToOne
               ┌──────────────────────────────┐
               │           Location           │
               │──────────────────────────────│
               │ id: String (UUID)            │
               │ nomClient: String            │
               │ emailClient: String          │
               │ telephoneClient: String      │
               │ dateDebut: LocalDate         │
               │ dateFin: LocalDate           │
               │ dateRetourReelle: LocalDate  │
               │ prixTotal: double            │
               │ statut: RentalStatus         │
               └──────────────────────────────┘

               ┌──────────────────────────────┐
               │           AppUser            │
               │──────────────────────────────│
               │ id: String (UUID)            │
               │ prenom: String               │
               │ nom: String                  │
               │ email: String                │
               │ password: String (BCrypt)    │
               │ actif: boolean               │
               │ roles: Set<Role>             │
               └──────────────────────────────┘
```

---

## Entités JPA

### Héritage `Vehicule`

L'entité `Vehicule` utilise la stratégie d'héritage **JOINED** : chaque sous-classe possède sa propre table liée par clé étrangère.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque, modele, matricule;
    private Double prixParJour;
    @Enumerated(EnumType.STRING)
    private VehiculeStatus status;
    @ManyToOne @JoinColumn(name = "agence_id")
    private Agence agence;
    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    private List<Location> locations;
}
```

### Entités Voiture / Motorcycle

```java
@Entity
public class Voiture extends Vehicule {
    private int nombrePortes;
    @Enumerated(EnumType.STRING) private TypeCarburant typeCarburant;
    @Enumerated(EnumType.STRING) private BoiteVitesse boiteVitesse;
}

@Entity
public class Motorcycle extends Vehicule {
    private int cylindree;
    @Enumerated(EnumType.STRING) private MotorcycleType typeMoto;
    private boolean casqueInclus;
}
```

### Énumérations

| Enum | Valeurs |
|------|---------|
| `VehiculeStatus` | `DISPONIBLE`, `LOUE`, `ENMAINTENANCE` |
| `RentalStatus` | `EN_ATTENTE`, `CONFIRMEE`, `EN_COURS`, `TERMINEE`, `ANNULEE` |
| `TypeCarburant` | `ESSENCE`, `DIESEL`, `HYBRIDE`, `ELECTRIQUE` |
| `BoiteVitesse` | `MANUELLE`, `AUTOMATIQUE` |
| `MotorcycleType` | `SPORT`, `SCOOTER`, `ROADSTER`, `TOURING` |
| `Role` | `ROLE_ADMIN`, `ROLE_EMPLOYE`, `ROLE_CLIENT` |

---

## Couche DAO – Repositories

Tous les repositories étendent `JpaRepository` pour bénéficier des opérations CRUD automatiques.

### AgenceRepository

```java
public interface AgenceRepository extends JpaRepository<Agence, String> {
    List<Agence> findByCityIgnoreCase(String city);
    List<Agence> findByNameContainingIgnoreCase(String name);
}
```

### VehiculeRepository

```java
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Optional<Vehicule> findByMatricule(String matricule);
    List<Vehicule> findByStatus(VehiculeStatus status);
    List<Vehicule> findByAgenceId(String agenceId);
    List<Vehicule> findByAgenceIdAndStatus(String agenceId, VehiculeStatus status);
    boolean existsByMatricule(String matricule);
}
```

### LocationRepository

```java
public interface LocationRepository extends JpaRepository<Location, String> {
    List<Location> findByVehiculeId(Long vehiculeId);
    List<Location> findByStatut(RentalStatus statut);
    List<Location> findByEmailClient(String emailClient);

    @Query("SELECT l FROM Location l WHERE l.vehicule.id = :vehiculeId " +
           "AND l.statut IN ('CONFIRMEE', 'EN_COURS') " +
           "AND l.dateDebut <= :dateFin AND l.dateFin >= :dateDebut")
    List<Location> findOverlappingLocations(...);
}
```

---

## Couche Service

### Pattern utilisé

Chaque service utilise une **interface** et une **implémentation** annotée `@Service @Transactional`.

### LocationService – Logique métier

```java
@Override
public LocationDTO creerLocation(LocationDTO dto) {
    // 1. Vérifier existence du véhicule
    // 2. Vérifier disponibilité du véhicule
    // 3. Vérifier cohérence des dates
    // 4. Calculer le prix total automatiquement
    // 5. Créer et sauvegarder la location
}

@Override
public double calculerPrixTotal(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
    long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
    return vehicule.getPrixParJour() * (jours <= 0 ? 1 : jours);
}
```

### DTOs et Mappers

Les **DTOs** exposent uniquement les champs nécessaires à l'API. Les **Mappers** assurent la conversion entité ↔ DTO manuellement sans dépendance externe.

---

## REST API Controllers

### Endpoints disponibles

#### Authentification (`/api/auth`)

| Méthode | URL | Description | Accès |
|---------|-----|-------------|-------|
| POST | `/api/auth/login` | Connexion et obtention token JWT | Public |
| POST | `/api/auth/register` | Inscription client | Public |

#### Agences (`/api/agences`)

| Méthode | URL | Description | Accès |
|---------|-----|-------------|-------|
| GET | `/api/agences` | Lister toutes les agences | Public |
| GET | `/api/agences/{id}` | Obtenir une agence | Public |
| GET | `/api/agences?ville=Casablanca` | Rechercher par ville | Public |
| POST | `/api/agences` | Créer une agence | ADMIN, EMPLOYE |
| PUT | `/api/agences/{id}` | Modifier une agence | ADMIN, EMPLOYE |
| DELETE | `/api/agences/{id}` | Supprimer une agence | ADMIN, EMPLOYE |
| GET | `/api/agences/{id}/vehicules` | Véhicules d'une agence | Public |

#### Véhicules (`/api/vehicules`)

| Méthode | URL | Description | Accès |
|---------|-----|-------------|-------|
| GET | `/api/vehicules` | Lister tous les véhicules | Public |
| GET | `/api/vehicules/disponibles` | Véhicules disponibles | Public |
| GET | `/api/vehicules/{id}` | Obtenir un véhicule | Public |
| PATCH | `/api/vehicules/{id}/statut` | Changer le statut | ADMIN, EMPLOYE |
| DELETE | `/api/vehicules/{id}` | Supprimer un véhicule | ADMIN, EMPLOYE |

#### Voitures (`/api/voitures`)

| Méthode | URL | Description | Accès |
|---------|-----|-------------|-------|
| GET | `/api/voitures` | Lister toutes les voitures | ADMIN, EMPLOYE |
| GET | `/api/voitures/{id}` | Obtenir une voiture | ADMIN, EMPLOYE |
| POST | `/api/voitures?agenceId=...` | Créer une voiture | ADMIN, EMPLOYE |
| PUT | `/api/voitures/{id}` | Modifier une voiture | ADMIN, EMPLOYE |

#### Locations (`/api/locations`)

| Méthode | URL | Description | Accès |
|---------|-----|-------------|-------|
| GET | `/api/locations` | Lister toutes les locations | ADMIN, EMPLOYE |
| GET | `/api/locations/actives` | Locations en cours | ADMIN, EMPLOYE |
| POST | `/api/locations` | Créer une location | Authentifié |
| PATCH | `/api/locations/{id}/demarrer` | Démarrer la location | ADMIN, EMPLOYE |
| PATCH | `/api/locations/{id}/terminer` | Terminer la location | ADMIN, EMPLOYE |
| PATCH | `/api/locations/{id}/annuler` | Annuler la location | Authentifié |
| GET | `/api/locations/disponibilite` | Vérifier disponibilité + prix | Public |

---

## Sécurité JWT avec Spring Security

![Sécurité JWT](docs/images/4.png)

### Flux d'authentification

```
Client                    Backend
  │                          │
  │── POST /api/auth/login ──▶│
  │   {email, password}      │
  │                          │── Vérification credentials
  │                          │── Génération token JWT
  │◀── 200 OK ───────────────│
  │    {token: "eyJ..."}     │
  │                          │
  │── GET /api/agences ──────▶│
  │   Authorization: Bearer  │── JwtAuthenticationFilter
  │                eyJ...    │── Valider token
  │                          │── Charger UserDetails
  │◀── 200 OK ───────────────│
  │    [...agences]          │
```

### Configuration Spring Security

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**", "/swagger-ui/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/agences/**", "/api/vehicules/**").permitAll()
            .requestMatchers("/api/agences/**").hasAnyRole("ADMIN", "EMPLOYE")
            .requestMatchers(HttpMethod.POST, "/api/locations/**").hasAnyRole("ADMIN","EMPLOYE","CLIENT")
            .anyRequest().authenticated()
        )
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
```

### Génération et validation du Token JWT

Le token JWT contient :
- **Subject** : email de l'utilisateur
- **IssuedAt** : date d'émission
- **Expiration** : 24h (configurable dans application.properties)
- **Signature** : HMAC-SHA256 avec clé secrète Base64

---

## Documentation Swagger / OpenAPI

La documentation Swagger est accessible à : **http://localhost:8088/swagger-ui.html**

Toutes les APIs sont documentées avec :
- Description des endpoints
- Modèles de requête et réponse
- Authentification JWT intégrée (bouton **Authorize**)
- Possibilité de tester directement depuis l'interface

### Configuration Swagger avec JWT

```java
@OpenAPIDefinition(
    info = @Info(title = "API Location de Véhicules", version = "1.0")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {}
```

---

## Base de Données MySQL

### Configuration application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/location_agence?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Africa/Casablanca&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### Tables générées automatiquement

| Table | Description |
|-------|-------------|
| `agence` | Agences de location |
| `vehicule` | Table de base des véhicules |
| `voiture` | Extension Voiture (JOINED) |
| `motorcycle` | Extension Motorcycle (JOINED) |
| `location` | Contrats de location |
| `app_user` | Utilisateurs du système |
| `user_roles` | Rôles des utilisateurs |

### Initialisation des données

L'application initialise automatiquement au démarrage :
- 3 utilisateurs : `admin`, `employe`, `client`
- 2 agences : Casablanca, Rabat
- 4 véhicules : Dacia Sandero, Renault Clio, Toyota Corolla, Yamaha MT-07

---

## Tests réalisés

### Tests avec Postman

**1. Authentification**
```
POST http://localhost:8088/api/auth/login
Body: {"email": "admin@location.ma", "password": "admin123"}
→ Retourne: {"token": "eyJhbGciOiJIUzI1NiJ9..."}
```

**2. Créer une agence (avec token)**
```
POST http://localhost:8088/api/agences
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Body: {"name": "AutoLoc Fès", "city": "Fès", "address": "..."}
→ 201 Created
```

**3. Réserver un véhicule**
```
POST http://localhost:8088/api/locations
Body: {
  "nomClient": "Mustapha ELMIFDALI",
  "emailClient": "m.elmifdali@test.ma",
  "dateDebut": "2022-05-15",
  "dateFin": "2022-05-20",
  "vehiculeId": 1
}
→ 201 Created avec prixTotal: 1750.0 (350 × 5 jours)
```

**4. Vérifier disponibilité + prix**
```
GET http://localhost:8088/api/locations/disponibilite?vehiculeId=1&dateDebut=2022-06-01&dateFin=2022-06-05
→ {"disponible": true, "prixTotal": 1400.0}
```

---

## Difficultés rencontrées et solutions

### 1. Héritage JPA avec Lombok `@Data`

**Problème** : `@Data` de Lombok sur une entité parent génère des conflits avec les sous-classes lors de l'héritage JPA.

**Solution** : Remplacer `@Data` par `@Getter @Setter @ToString(exclude=...)` séparément sur chaque classe, en évitant les références circulaires dans `toString()`.

### 2. Références croisées dans les entités

**Problème** : `Agence ↔ Vehicule` et `Vehicule ↔ Location` créaient des boucles infinies lors de la sérialisation JSON.

**Solution** : Utilisation de **DTOs** pour exposer uniquement les champs nécessaires, avec `@JsonIgnore` sur les relations bidirectionnelles, et `FetchType.LAZY` par défaut.

### 3. Wrong package dans UserRepository et VehiculeRepository

**Problème** : Le repository `UserRepository` importait une classe `AppUser` d'un autre package (`toubani.badreddine.carloacation`), et `VehiculeRepository` était dans un mauvais package.

**Solution** : Correction des imports et du package de chaque fichier pour correspondre à la structure du projet.

### 4. Types incompatibles (String vs Long) dans les IDs

**Problème** : L'entité `Vehicule` utilisait `Long` comme ID mais les repositories attendaient `String`.

**Solution** : Uniformisation — `Agence` et `AppUser` utilisent `String (UUID)`, `Vehicule`, `Voiture`, `Motorcycle` utilisent `Long (AUTO_INCREMENT)`, et `Location` utilise `String (UUID)`.

### 5. Configuration Spring Boot 4.x avec dépendances incorrectes

**Problème** : Le `pom.xml` original utilisait Spring Boot 4.0.6 (inexistant) avec des artefacts incorrects (`spring-boot-h2console`, `spring-boot-starter-webmvc`).

**Solution** : Migration vers Spring Boot **3.3.0** stable, correction de toutes les dépendances, ajout de JWT JJWT 0.12.5.

---

## Améliorations apportées

1. **Architecture en couches stricte** : Controller → Service (Interface + Impl) → Repository
2. **Gestion des erreurs centralisée** : `GlobalExceptionHandler` avec messages d'erreur propres en français
3. **Validation des données** : Annotations `@Valid`, `@NotBlank`, `@Email`, `@Positive` sur les DTOs
4. **Calcul automatique du prix total** : basé sur `prixParJour × nombre de jours`
5. **Vérification de disponibilité** : Requête JPQL pour détecter les chevauchements de réservations
6. **Initialisation automatique des données** : `CommandLineRunner` avec données réalistes
7. **Sécurité par rôles** : Chaque endpoint sécurisé selon le rôle de l'utilisateur
8. **Documentation complète** : Swagger UI avec authentification JWT intégrée
9. **Journalisation** : Utilisation de `@Slf4j` pour les logs applicatifs
10. **Transactions** : `@Transactional(readOnly = true)` pour les opérations de lecture

---

## Conclusion

Ce projet implémente une application backend complète et professionnelle de gestion de location de véhicules, en respectant les bonnes pratiques de développement Spring Boot :

- **Architecture REST** claire et cohérente
- **Sécurité robuste** avec JWT et Spring Security
- **Modélisation JPA** correcte avec héritage, relations et contraintes
- **Code maintenable** grâce aux DTOs, mappers et séparation des couches
- **Documentation** complète via Swagger/OpenAPI

L'application est prête à être étendue avec un frontend Angular ou une intégration avec d'autres microservices.

---

*Rapport rédigé par **Mustapha ELMIFDALI** – Master SDIA – ENSET Mohammedia – Mai 2022*
