package org.walther.gestionempleados.service;

import org.walther.gestionempleados.model.dto.EmpleadoSummaryDTO;
import org.walther.gestionempleados.model.entity.Empleado;
import org.walther.gestionempleados.model.dto.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {
    EmpleadoSummaryDTO crearEmpleado(EmpleadoDTO empleadoDTO);
    List<EmpleadoSummaryDTO> obtenerEmpleados();
    EmpleadoSummaryDTO obtenerEmpleadoPorId(Integer id);
    EmpleadoSummaryDTO actualizarEmpleado(EmpleadoDTO empleadoDTO);
    void eliminarEmpleadoPorDni(String dni);
}
