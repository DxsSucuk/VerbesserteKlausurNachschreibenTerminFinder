package de.presti.vkntf.backend.repository.teacher;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class TeacherClassroomMap implements Persistable<Long> {

    @Id
    @GeneratedValue
    long id;

    @NonNull
    String teacherId;

    @NonNull
    String classroomId;

    @Override
    public Long getId() {
        return id;
    }

    boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
}