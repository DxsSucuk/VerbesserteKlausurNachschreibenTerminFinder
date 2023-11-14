package de.presti.vkntf.backend.repository.classroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class Classroom implements Persistable<String> {

    @Id
    @NonNull
    String name;

    int abteilung;

    @NonNull
    @JsonIgnore
    String classTeacher;

    @Override
    public String getId() {
        return name;
    }

    @JsonIgnore
    boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
