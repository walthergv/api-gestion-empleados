package org.walther.gestionempleados.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.walther.gestionempleados.model.dto.OficinaDTO;
import org.walther.gestionempleados.service.OficinaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/oficinas")
public class OficinaController {
    @Autowired
    private OficinaService oficinaService;

    @GetMapping
    public ResponseEntity<List<OficinaDTO>> obtenerOficinas(){
        try {
            return ResponseEntity.ok(oficinaService.obtenerOficinas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OficinaDTO> obtenerOficinaPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(oficinaService.obtenerOficinaPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<OficinaDTO> crearOficina(@RequestBody OficinaDTO oficinaDTO){
        return ResponseEntity.ok(oficinaService.crearOficina(oficinaDTO));
    }

    @PostMapping("/{id}")
    public ResponseEntity<OficinaDTO> actualizarOficina(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        try {
            String nuevoNombre = request.get("nombre");
            OficinaDTO oficinaDTO = oficinaService.obtenerOficinaPorId(id);
            oficinaDTO.setNombre(nuevoNombre);
            return ResponseEntity.ok(oficinaService.actualizarOficina(oficinaDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOficina(@PathVariable Integer id){
        try {
            oficinaService.eliminarOficina(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
