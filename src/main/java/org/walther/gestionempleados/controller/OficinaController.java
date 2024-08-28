package org.walther.gestionempleados.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.walther.gestionempleados.model.dto.OficinaDTO;
import org.walther.gestionempleados.service.OficinaService;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<OficinaDTO> crearOficina(@RequestBody OficinaDTO oficinaDTO){
        return ResponseEntity.ok(oficinaService.crearOficina(oficinaDTO));
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
