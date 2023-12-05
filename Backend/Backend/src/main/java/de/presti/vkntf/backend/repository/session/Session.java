package de.presti.vkntf.backend.repository.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.presti.vkntf.backend.repository.student.Student;
import de.presti.vkntf.backend.repository.teacher.Teacher;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class Session implements Persistable<String> {

    public Session(@NonNull String sessionToken, Teacher teacher) {
        this.sessionToken = sessionToken;
        this.teacher = teacher.getId();
    }

    public Session(@NonNull String sessionToken, Student student) {
        this.sessionToken = sessionToken;
        this.student = student.getId();
    }

    @Id
    @NonNull
    String sessionToken;

    @JsonIgnore
    private String teacher;

    @JsonIgnore
    private String student;

    @Override
    @JsonIgnore
    public String getId() {
        return sessionToken;
    }

    @Transient
    @JsonIgnore
    boolean isNew = true;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return isNew;
    }

    @CreationTimestamp
    Timestamp created;

    @UpdateTimestamp
    Timestamp updated;
}