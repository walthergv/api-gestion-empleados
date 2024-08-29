package org.walther.gestionempleados.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.walther.gestionempleados.model.entity.Empleado;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OficinaDTO {
    private int id;
    @NotNull @NotEmpty
    private String nombre;
    private List<Empleado> empleados;
}
