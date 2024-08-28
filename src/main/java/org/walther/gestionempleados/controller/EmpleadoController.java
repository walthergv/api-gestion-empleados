package org.walther.gestionempleados.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.walther.gestionempleados.model.dto.EmpleadoDTO;
import org.walther.gestionempleados.model.dto.EmpleadoSummaryDTO;
import org.walther.gestionempleados.service.EmpleadoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoSummaryDTO>> obtenerEmpleados(){
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleados());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmpleadoSummaryDTO> crearEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO){
        try {
            return ResponseEntity.ok(empleadoService.crearEmpleado(empleadoDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoSummaryDTO> obtenerEmpleadoPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<EmpleadoSummaryDTO> actualizarEmpleado(@RequestBody EmpleadoDTO empleadoDTO){
        try {
            return ResponseEntity.ok(empleadoService.actualizarEmpleado(empleadoDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable String dni){
        try {
            empleadoService.eliminarEmpleadoPorDni(dni);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
