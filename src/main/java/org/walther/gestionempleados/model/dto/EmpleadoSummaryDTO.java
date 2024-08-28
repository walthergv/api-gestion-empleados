package org.walther.gestionempleados.model.dto;

import lombok.*;
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
   // private List<Oficina> oficinas;

}
