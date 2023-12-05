package de.presti.vkntf.backend.repository.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    Long id;

    @NonNull
    String studentId;

    @NonNull
    String teacherId;

    @NonNull
    String classromId;

    @Transient
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
