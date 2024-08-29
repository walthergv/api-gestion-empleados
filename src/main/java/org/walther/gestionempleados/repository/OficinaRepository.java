package org.walther.gestionempleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.walther.gestionempleados.model.dto.OficinaDTO;
import org.walther.gestionempleados.model.entity.Oficina;

import java.util.Optional;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Integer> {
    Optional<Oficina> findByNombre (String nombre);
}
