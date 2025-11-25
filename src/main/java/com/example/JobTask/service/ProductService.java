package com.example.JobTask.service;

import com.example.JobTask.dto.product.BuyProductRequestDTO;
import com.example.JobTask.dto.product.ProductRequestDTO;
import com.example.JobTask.dto.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO>getAllProducts();

    ProductResponseDTO updateProductById(Long id,ProductRequestDTO productRequestDTO);

    void deleteProductById(Long id);

    ProductResponseDTO buyProduct(BuyProductRequestDTO buyProductRequestDTO);
}
