package org.walther.gestionempleados.model.dto;

import lombok.*;
import org.walther.gestionempleados.model.entity.Empleado;
import org.walther.gestionempleados.model.entity.Oficina;

import java.util.List;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoSummaryDTO {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private List<Oficina> oficinas;

    public EmpleadoSummaryDTO(Empleado empleado) {
        this.id = empleado.getId();
        this.nombre = empleado.getNombre();
        this.apellido = empleado.getApellido();
        this.dni = empleado.getDni();
        this.oficinas = empleado.getOficinas();
    }
}
