package com.reto.pacifico.ms_producto.dto;

public record ProductResponseDto(
        Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        Boolean activo
) {}
