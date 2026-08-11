package com.example.capitalmarkets.tradesettlement.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "roles")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Override
    public boolean equals(Object o) {
        // 1. Check identity
        if (this == o) return true;

        // 2. Check null and class compatibility (handles Hibernate proxies safely)
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        // 3. Compare by ID if it exists; otherwise compare by business key (name)
        if (id != null && role.id != null) {
            return Objects.equals(id, role.id);
        }
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        // If ID is null, fallback to name to maintain consistency before persistence
        if (id != null) {
            return Objects.hashCode(id);
        }
        return Objects.hashCode(name);
    }
}
