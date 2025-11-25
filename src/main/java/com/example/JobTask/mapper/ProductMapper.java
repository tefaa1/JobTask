package com.example.JobTask.mapper;

import com.example.JobTask.dto.product.ProductRequestDTO;
import com.example.JobTask.dto.product.ProductResponseDTO;
import com.example.JobTask.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id",ignore = true)
    Product toEntity(ProductRequestDTO productRequestDTO);

    ProductResponseDTO toResponse(Product product);

    void updateEntityFromDto(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
}
