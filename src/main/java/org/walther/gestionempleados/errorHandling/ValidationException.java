package org.walther.gestionempleados.errorHandling;

import lombok.Getter;

import java.util.List;
@Getter
public class ValidationException extends RuntimeException {
    private final List<String> errors;
    public ValidationException(List<String> errors){
        super("Validación fallida");
        this.errors = errors;
    }
}
