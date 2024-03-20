package de.presti.vkntf.backend.repository.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
