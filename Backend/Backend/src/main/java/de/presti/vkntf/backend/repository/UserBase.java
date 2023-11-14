package de.presti.vkntf.backend.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class UserBase implements Persistable<String> {

    @Id
    @NonNull
    @JsonIgnore
    String id;

    @NonNull
    String vorname;

    @NonNull
    String nachname;

    @NonNull
    String email;

    @JsonIgnore
    @NonNull
    String password;

    @Override
    public @NonNull String getId() {
        return id;
    }

    @JsonIgnore
    boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

}
