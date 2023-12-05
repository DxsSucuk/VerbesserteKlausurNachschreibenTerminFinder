package de.presti.vkntf.backend.repository.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
public class Notice implements Persistable<Long> {

    @Id
    long Id;
    long examId;

    @NonNull
    String studentId;

    String approvedByTeacherId;

    @CreationTimestamp
    Timestamp createdAt;

    @Override
    public Long getId() {
        return Id;
    }

    @Transient
    @JsonIgnore
    boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

}
