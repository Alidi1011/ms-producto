package com.reto.pacifico.ms_producto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequestDto(
        @NotBlank String nombre,
        String descripcion,
        @NotNull @Positive Double precio,
        @PositiveOrZero Integer stock,
        Boolean activo
) {}
