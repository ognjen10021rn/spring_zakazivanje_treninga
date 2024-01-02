package rs.ognjen_uros.spring_zakazivanje_treninga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Termin;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {

    @Query("SELECT t FROM Termin t WHERE (t.start >= :start AND t.start < :end) OR (t.end > :start AND t.end <= :end)")
    List<Termin> findOccupiedTimes(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
