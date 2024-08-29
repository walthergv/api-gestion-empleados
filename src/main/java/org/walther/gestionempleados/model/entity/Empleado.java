package org.walther.gestionempleados.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.walther.gestionempleados.validators.Dni;
import org.walther.gestionempleados.validators.Telefono;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    @Column(unique = true)
    private String dni;
    private String direccion;
    @Column(name = "fecha_nacimiento")
    private Date fecha_nacimiento;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "empleado_oficina",
            joinColumns = @JoinColumn(name = "empleado_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "oficina_id", referencedColumnName = "id")
    )
   // @JsonManagedReference
    private List<Oficina> oficinas;

}
