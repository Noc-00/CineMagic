package com.cinemagic.repository;

import com.cinemagic.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNombre(String nombre);
}
