package com.example.capitalmarkets.tradesettlement.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);
    Set<Role> findAllByNameIn(Collection<String> names);
}
