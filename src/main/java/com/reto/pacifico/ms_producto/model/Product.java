package com.reto.pacifico.ms_producto.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Table("productos")
public record Product(
        @Id Long id,
        @NotBlank
        String nombre,
        String descripcion,
        @NotNull
        @Positive
        Double precio,
        @PositiveOrZero
        Integer stock,
        Boolean activo
) {}
