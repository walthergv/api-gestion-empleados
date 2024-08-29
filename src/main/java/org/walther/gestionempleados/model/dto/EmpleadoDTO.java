package org.walther.gestionempleados.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.walther.gestionempleados.model.entity.Oficina;
import org.walther.gestionempleados.validators.Dni;
import org.walther.gestionempleados.validators.Telefono;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
    private int id;
    private String nombre;
    @NotNull @NotEmpty
    private String apellido;
    @NotNull @NotEmpty @Telefono
    private String telefono;
    @NotNull @NotEmpty @Dni
    private String dni;
    @NotNull @NotEmpty
    private String direccion;
    @NotNull @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_nacimiento;
    private List<Oficina> oficinas;
}
