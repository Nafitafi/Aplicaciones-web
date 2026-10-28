package com.example.PokemonAPI.exception;

import com.example.PokemonAPI.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidaciones(MethodArgumentNotValidException ex){
        List<String> detalles = new ArrayList<>();

        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            detalles.add(error.getField() + ": "+ error.getDefaultMessage());
        }

        ErrorDTO errorDTO = new ErrorDTO(
                "Error en la validacion de datos de entrada",
                detalles,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarRecursoNoEncontrado(ResourceNotFoundException ex){
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                "El recurso solicitado no fue encontrado",
                detalles,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorDTO> manejarReglaDeNegocio(BusinessRuleException ex){
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                "Violación de regla de negocio",
                detalles,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> errorDelProgramador(Exception ex){
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                "Ocurrio un error interno en el servidor",
                detalles,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
