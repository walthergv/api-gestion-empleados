package org.walther.gestionempleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import org.springframework.validation.Validator;
import org.walther.gestionempleados.errorHandling.ValidationException;
import org.walther.gestionempleados.model.dto.EmpleadoSummaryDTO;
import org.walther.gestionempleados.model.dto.EmpleadoDTO;
import org.walther.gestionempleados.model.dto.OficinaDTO;
import org.walther.gestionempleados.model.entity.Empleado;
import org.walther.gestionempleados.model.entity.Oficina;
import org.walther.gestionempleados.repository.EmpleadoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoServiceImp implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;
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
            empleado.setOficinas(empleadoDTO.getOficinas());
            empleado.setDireccion(empleadoDTO.getDireccion());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setFecha_nacimiento(empleadoDTO.getFecha_nacimiento());
            empleadoRepository.save(empleado);

            return new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido());
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
                empleadosSummary.add(new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido()));
            }
            return empleadosSummary;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los empleados", e);
        }
    }

    @Override
    public EmpleadoSummaryDTO obtenerEmpleadoPorId(Integer id) {
        try {
            Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id + " para obtener"));
            return new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el empleado con id: " + id, e);
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

            Empleado empleado = new Empleado();
            empleado.setId(empleadoDTO.getId());
            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setApellido(empleadoDTO.getApellido());
            empleado.setDni(empleadoDTO.getDni());
            empleado.setOficinas(empleadoDTO.getOficinas());
            empleado.setDireccion(empleadoDTO.getDireccion());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setFecha_nacimiento(empleadoDTO.getFecha_nacimiento());
            empleadoRepository.save(empleado);

            return new EmpleadoSummaryDTO(empleado.getId(), empleado.getNombre(), empleado.getApellido());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el empleado", e);
        }
    }

    @Override
    public void eliminarEmpleadoPorDni(String dni) {
        try {
            if (empleadoRepository.existsByDni(dni)) {
                Empleado empleado = empleadoRepository.findByDni(dni);
                empleadoRepository.delete(empleado);
            } else {
                throw new RuntimeException("Empleado no encontrado con dni: " + dni + " para eliminar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el empleado con dni: " + dni, e);
        }
    }
}