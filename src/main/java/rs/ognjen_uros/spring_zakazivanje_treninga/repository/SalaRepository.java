package rs.ognjen_uros.spring_zakazivanje_treninga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Sala;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;

import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    Optional<Sala> findByName(String name);
}
