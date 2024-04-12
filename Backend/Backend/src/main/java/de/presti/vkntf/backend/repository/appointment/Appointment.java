package de.presti.vkntf.backend.repository.appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class Appointment implements Persistable<Long> {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long appointmentId;

    boolean acceptedByTeacher;
    boolean acceptedByStudent;

    @NonNull
    String teacherId;

    @NonNull
    String studentId;

    @NonNull
    String classroomId;

    @NonNull
    LocalDateTime date;

    @Override
    public Long getId() {
        return appointmentId;
    }

    @Transient
    @JsonIgnore
    boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
