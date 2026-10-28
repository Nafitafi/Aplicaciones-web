package com.example.PokemonAPI.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "El nombre no puede ser vacio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre dos y cincuenta caracteres")
    private String nombre;

    @NotBlank(message = "El tipo no puede ser vacio")
    private String tipo;

    @NotNull(message = "El nivel es requerido")
    @Min(value = 1, message = "El valor mínimo es 1")
    @Max(value = 100, message = "El nivel máximo es 100")
    private Integer nivel;

    @NotNull(message = "El nivel es requerido")
    @Min(value = 1, message = "El ataque mínimo es 1")
    private Integer ataque;

    @Min(value = 0, message = "La defensa no puede ser negativa")
    private Integer defensa;

    @Size(max = 255, message = "La longitud de la descripción no puede exceder los 255 caracteres")
    private String descripcion;

    @URL(message = "La imagen debe ser una URL")
    private String imagenUrl;

    @Email(message = "El correo debe tener un formato valido")
    private String correoEntrenador;

    @PastOrPresent(message = "La fecha de nacimiento no puede ser mayor a la actual")
    private LocalDate fechaNacimiento;

    @Positive(message = "El peso del pokemon debe ser mayor a 0")
    private Double peso;

    @Pattern(regexp = "\\\\d{10}", message = "El formato del telefono no es valido")
    private String telefonoEntrenador;

    @AssertTrue(message = "El pokemon debe esrtar sanito")
    private Boolean estaSano;
}
