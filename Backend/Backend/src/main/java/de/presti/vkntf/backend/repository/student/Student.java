package de.presti.vkntf.backend.repository.student;

import de.presti.vkntf.backend.repository.UserBase;
import jakarta.persistence.Inheritance;
import lombok.*;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@Setter
@Inheritance
@NoArgsConstructor
@AllArgsConstructor
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class Student extends UserBase {
    String classroomId;
}
