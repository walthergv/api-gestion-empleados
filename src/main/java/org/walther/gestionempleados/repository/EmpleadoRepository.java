package org.walther.gestionempleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.walther.gestionempleados.model.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Empleado findByDni(String dni);
    Empleado deleteByDni(String dni);
    boolean existsByDni(String dni);
}
