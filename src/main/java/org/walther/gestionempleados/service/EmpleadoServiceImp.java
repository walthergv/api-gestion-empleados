package org.walther.gestionempleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import org.springframework.validation.Validator;
import org.walther.gestionempleados.errorHandling.ValidationException;
import org.walther.gestionempleados.model.dto.EmpleadoSummaryDTO;
import org.walther.gestionempleados.model.dto.EmpleadoDTO;
import org.walther.gestionempleados.model.entity.Empleado;
import org.walther.gestionempleados.model.entity.Oficina;
import org.walther.gestionempleados.repository.EmpleadoRepository;
import org.walther.gestionempleados.repository.OficinaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoServiceImp implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    private Validator validator;
    private void validarEmpleadoDTO(EmpleadoDTO empleadoDTO, List<String> erroresValidacion) {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(empleadoDTO, "empleadoDTO");
        validator.validate(empleadoDTO, result);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String mensajeError = fieldError.getField() + ": " + fieldError.getDefaultMessage();
                erroresValidacion.add(mensajeError);
            });
        }
    }

    @Override
    public EmpleadoSummaryDTO crearEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            List<String> erroresValidacion = new ArrayList<>();
            validarEmpleadoDTO(empleadoDTO, erroresValidacion);

            if (!erroresValidacion.isEmpty()) {
                throw new ValidationException(erroresValidacion);
            }

            Empleado empleado = new Empleado();
            empleado.setId(empleadoDTO.getId());
            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setApellido(empleadoDTO.getApellido());
            empleado.setDni(empleadoDTO.getDni());

            List<Oficina> oficinas = new ArrayList<>();
            for (Oficina oficinaDTO : empleadoDTO.getOficinas()) {
                Oficina oficina = oficinaRepository.findByNombre(oficinaDTO.getNombre())
                        .orElseThrow(() -> new RuntimeException("Oficina no encontrada: " + oficinaDTO.getNombre()));
                oficinas.add(oficina);
            }

            empleado.setOficinas(oficinas);
            empleado.setDireccion(empleadoDTO.getDireccion());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setFecha_nacimiento(empleadoDTO.getFecha_nacimiento());
            empleadoRepository.save(empleado);

            return new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getDni(), empleado.getOficinas());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el empleado", e);
        }
    }

    @Override
    public List<EmpleadoSummaryDTO> obtenerEmpleados() {
        try {
            List<Empleado> empleados = empleadoRepository.findAll();
            List<EmpleadoSummaryDTO> empleadosSummary = new ArrayList<>();
            for (Empleado empleado : empleados) {
                empleadosSummary.add(new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getDni(),empleado.getOficinas()));
            }
            return empleadosSummary;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los empleados", e);
        }
    }

    @Override
    public EmpleadoSummaryDTO obtenerEmpleadoPorId(Integer id) {
        try {
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id + " para obtener"));

            if (empleado == null) {
                throw new RuntimeException("Empleado no encontrado con id: " + id);
            }

            return new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getDni(), empleado.getApellido(), empleado.getOficinas());
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al obtener el empleado con id: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener el empleado con id: " + id, e);
        }
    }

    @Override
    public EmpleadoSummaryDTO actualizarEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            List<String> erroresValidacion = new ArrayList<>();
            validarEmpleadoDTO(empleadoDTO, erroresValidacion);

            if (!erroresValidacion.isEmpty()) {
                throw new ValidationException(erroresValidacion);
            }

            Empleado empleado = empleadoRepository.findById(empleadoDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + empleadoDTO.getId()));

            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setApellido(empleadoDTO.getApellido());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setDni(empleadoDTO.getDni());
            empleado.setDireccion(empleadoDTO.getDireccion());
            empleado.setFecha_nacimiento(empleadoDTO.getFecha_nacimiento());

            empleadoRepository.save(empleado);

            return new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getDni(), empleado.getApellido(), empleado.getOficinas());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el empleado", e);
        }
    }

    @Override
    public void eliminarEmpleado(Integer id) {
        try {
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id + " para eliminar"));

            if (empleado == null) {
                throw new RuntimeException("Empleado no encontrado con id: " + id);
            }

            empleado.getOficinas().clear();
            empleadoRepository.save(empleado);

            empleadoRepository.delete(empleado);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al eliminar el empleado con id: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al eliminar el empleado con id: " + id, e);
        }
    }

    @Override
    public EmpleadoSummaryDTO asignarOficinaAEmpleado(int empleadoId, String nombreOficina) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + empleadoId));
        Oficina oficina = oficinaRepository.findByNombre(nombreOficina)
                .orElseThrow(() -> new RuntimeException("Oficina no encontrada: " + nombreOficina));

        empleado.getOficinas().add(oficina);
        empleadoRepository.save(empleado);

        return new EmpleadoSummaryDTO(empleado);
    }
}