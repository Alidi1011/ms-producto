package com.reto.pacifico.ms_producto.mapper;

import com.reto.pacifico.ms_producto.dto.ProductRequestDto;
import com.reto.pacifico.ms_producto.dto.ProductResponseDto;
import com.reto.pacifico.ms_producto.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toModel(ProductRequestDto request) {
        return new Product(null, request.nombre(), request.descripcion(), request.precio(),
                request.stock(), request.activo());
    }

    public ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(product.id(), product.nombre(), product.descripcion(),
                product.precio(), product.stock(), product.activo());
    }
}
