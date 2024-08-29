package org.walther.gestionempleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.walther.gestionempleados.model.entity.Empleado;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByDni(String dni);
    Empleado deleteByDni(String dni);
    boolean existsByDni(String dni);
}
