package org.walther.gestionempleados.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = TelefonoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Telefono{
    String message() default "El teléfono no es válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
