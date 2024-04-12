package de.presti.vkntf.backend.repository.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import java.sql.Timestamp;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class MissedExam implements Persistable<Long> {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String studentId;

    @NonNull
    String teacherId;

    @NonNull
    String classroomId;

    @Transient
    @org.springframework.data.annotation.Transient
    public boolean hasNotice;

    @NonNull
    Timestamp missedAt;

    @Override
    public @NonNull Long getId() {
        return id;
    }

    @Transient
    @JsonIgnore
    boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
