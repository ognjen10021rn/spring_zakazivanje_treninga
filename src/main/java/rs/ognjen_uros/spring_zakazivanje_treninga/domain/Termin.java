package rs.ognjen_uros.spring_zakazivanje_treninga.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime start;

    private LocalDateTime end;
    private String dayOfTheWeek;
    @ManyToOne(optional = false)
    private TrainingType trainingType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
