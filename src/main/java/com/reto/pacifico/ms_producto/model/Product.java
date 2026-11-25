package com.reto.pacifico.ms_producto.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("productos")
public record Product(
        @Id Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        Boolean activo
) {}
