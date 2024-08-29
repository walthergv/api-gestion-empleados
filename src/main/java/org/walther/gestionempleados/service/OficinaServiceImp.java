package org.walther.gestionempleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walther.gestionempleados.model.dto.OficinaDTO;
import org.walther.gestionempleados.model.entity.Empleado;
import org.walther.gestionempleados.model.entity.Oficina;
import org.walther.gestionempleados.repository.OficinaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OficinaServiceImp implements OficinaService{
    @Autowired
    private OficinaRepository oficinaRepository;
    @Override
    public OficinaDTO crearOficina(OficinaDTO oficinaDTO) {
        try {
            Oficina oficina = new Oficina();
            oficina.setNombre(oficinaDTO.getNombre());
            oficina.setEmpleados(new ArrayList<>());

            Oficina oficinaGuardada = oficinaRepository.save(oficina);
            return new OficinaDTO(oficinaGuardada.getId(), oficinaGuardada.getNombre(), oficinaGuardada.getEmpleados().stream().map(empleado -> new Empleado(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getTelefono(),
                    empleado.getDni(),
                    empleado.getDireccion(),
                    empleado.getFecha_nacimiento(),
                    null // Evitar referencia circular
            )).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la oficina", e);
        }
    }

    @Override
    public List<OficinaDTO> obtenerOficinas() {
        try {
            List<Oficina> oficinas = oficinaRepository.findAll();
            return oficinas.stream().map(oficina -> new OficinaDTO(
                    oficina.getId(),
                    oficina.getNombre(),
                    oficina.getEmpleados().stream().map(empleado -> new Empleado(
                            empleado.getId(),
                            empleado.getNombre(),
                            empleado.getApellido(),
                            empleado.getTelefono(),
                            empleado.getDni(),
                            empleado.getDireccion(),
                            empleado.getFecha_nacimiento(),
                            null // Avoid circular reference
                    )).collect(Collectors.toList())
            )).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las oficinas", e);
        }
    }

    @Override
    public OficinaDTO obtenerOficinaPorId(Integer id) {
        try {
            if (oficinaRepository.existsById(id)) {
                Oficina oficina = oficinaRepository.getById(id);
                return new OficinaDTO(oficina.getId(), oficina.getNombre(), oficina.getEmpleados().stream().map(empleado -> new Empleado(
                        empleado.getId(),
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getTelefono(),
                        empleado.getDni(),
                        empleado.getDireccion(),
                        empleado.getFecha_nacimiento(),
                        null // Avoid circular reference
                )).collect(Collectors.toList()));
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
                Oficina oficina = oficinaRepository.getById(oficinaDTO.getId());
                oficina.setNombre(oficinaDTO.getNombre());
                oficinaRepository.save(oficina);
                return new OficinaDTO(oficina.getId(), oficina.getNombre(), oficina.getEmpleados().stream().map(empleado -> new Empleado(
                        empleado.getId(),
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getTelefono(),
                        empleado.getDni(),
                        empleado.getDireccion(),
                        empleado.getFecha_nacimiento(),
                        null // Avoid circular reference
                )).collect(Collectors.toList()));
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
}
