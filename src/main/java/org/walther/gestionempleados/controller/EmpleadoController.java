package org.walther.gestionempleados.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.walther.gestionempleados.model.dto.EmpleadoDTO;
import org.walther.gestionempleados.model.dto.EmpleadoSummaryDTO;
import org.walther.gestionempleados.service.EmpleadoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

//    @ApiOperation(value = "Obtener todos los empleados")
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

//    @ApiOperation(value = "Obtener un empleado por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoSummaryDTO> obtenerEmpleadoPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @ApiOperation(value = "Actualizar un empleado")
    @PostMapping("/{id}")
    public ResponseEntity<EmpleadoSummaryDTO> actualizarEmpleado(@PathVariable Integer id, @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            empleadoDTO.setId(id);
            return ResponseEntity.ok(empleadoService.actualizarEmpleado(empleadoDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


//    @ApiOperation(value = "Eliminar un empleado por su DNI")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id){
        try {
            empleadoService.eliminarEmpleado(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @ApiOperation(value = "Asignar oficina adicional a un empleado")
    @PostMapping("/{id}/asignar-oficina")
    public ResponseEntity<EmpleadoSummaryDTO> asignarOficinaAdicional(@PathVariable int id, @RequestBody Map<String, String> request) {
        try {
            String nombreOficina = request.get("nombreOficina");
            EmpleadoSummaryDTO empleado = empleadoService.asignarOficinaAEmpleado(id, nombreOficina);
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
