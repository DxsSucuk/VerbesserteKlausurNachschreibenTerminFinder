package de.presti.vkntf.backend.repository.teacher;

import de.presti.vkntf.backend.repository.UserBase;
import jakarta.persistence.Inheritance;
import lombok.*;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table
@Getter
@Setter
@Inheritance
@AllArgsConstructor
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class Teacher extends UserBase {
    int abteilung;
}
