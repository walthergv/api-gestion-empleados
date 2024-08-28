package org.walther.gestionempleados.service;

import org.walther.gestionempleados.model.dto.OficinaDTO;
import org.walther.gestionempleados.model.entity.Oficina;

import java.util.List;

public interface OficinaService {
    OficinaDTO crearOficina(OficinaDTO oficinaDTO);
    List<OficinaDTO> obtenerOficinas();
    OficinaDTO obtenerOficinaPorId(Integer id);
    OficinaDTO actualizarOficina(OficinaDTO oficinaDTO);
    void eliminarOficina(Integer id);

}
