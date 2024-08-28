package org.walther.gestionempleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walther.gestionempleados.model.dto.OficinaDTO;
import org.walther.gestionempleados.model.entity.Oficina;
import org.walther.gestionempleados.repository.OficinaRepository;

import java.util.List;
@Service
public class OficinaServiceImp implements OficinaService{
    @Autowired
    private OficinaRepository oficinaRepository;
    @Override
    public OficinaDTO crearOficina(OficinaDTO oficinaDTO) {
        try {
            return getOficinaDTO(oficinaDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la oficina", e);
        }
    }

    @Override
    public List<OficinaDTO> obtenerOficinas() {
        try {
            List<Oficina> oficinas = oficinaRepository.findAll();
            return oficinas.stream().map(oficina -> new OficinaDTO(oficina.getId(), oficina.getNombre(), oficina.getEmpleados())).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las oficinas", e);
        }
    }

    @Override
    public OficinaDTO obtenerOficinaPorId(Integer id) {
        try {
            if (oficinaRepository.existsById(id)) {
                Oficina oficina = oficinaRepository.getById(id);
                return new OficinaDTO(oficina.getId(), oficina.getNombre(), oficina.getEmpleados());
            } else {
                throw new RuntimeException("La oficina no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la oficina", e);
        }
    }

    @Override
    public OficinaDTO actualizarOficina(OficinaDTO oficinaDTO) {
        try {
            if (oficinaRepository.existsById(oficinaDTO.getId())) {
                return getOficinaDTO(oficinaDTO);
            } else {
                throw new RuntimeException("La oficina no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la oficina", e);
        }
    }

    @Override
    public void eliminarOficina(Integer id) {
        try {
            if (oficinaRepository.existsById(id)) {
                oficinaRepository.deleteById(id);
            } else {
                throw new RuntimeException("La oficina no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la oficina", e);
        }
    }
    private OficinaDTO getOficinaDTO(OficinaDTO oficinaDTO) {
        Oficina oficina = new Oficina();
        oficina.setId(oficinaDTO.getId());
        oficina.setNombre(oficinaDTO.getNombre());

        Oficina oficinaGuardada = oficinaRepository.save(oficina);
        return new OficinaDTO(oficinaGuardada.getId(), oficinaGuardada.getNombre(), oficinaGuardada.getEmpleados());
    }
}
