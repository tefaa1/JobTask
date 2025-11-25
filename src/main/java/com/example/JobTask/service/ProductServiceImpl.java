package com.example.JobTask.service;

import com.example.JobTask.Repository.ProductRepository;
import com.example.JobTask.dto.product.BuyProductRequestDTO;
import com.example.JobTask.dto.product.ProductRequestDTO;
import com.example.JobTask.dto.product.ProductResponseDTO;
import com.example.JobTask.entity.Product;
import com.example.JobTask.exception.NoEnoughMoney;
import com.example.JobTask.exception.NoEnoughStockQuantity;
import com.example.JobTask.exception.ResourceNotFoundException;
import com.example.JobTask.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        Product product = productMapper.toEntity(productRequestDTO);
        product = productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProductById(Long id, ProductRequestDTO productRequestDTO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

        productMapper.updateEntityFromDto(productRequestDTO, product);

        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO buyProductById(BuyProductRequestDTO buyProductRequestDTO) {

        Long id = buyProductRequestDTO.getId();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

        if (buyProductRequestDTO.getQuantity() > product.getStockQuantity()) {
            throw new NoEnoughStockQuantity("No enough stock quantity for product with id " + id);
        }

        Long money = product.getPrice() * buyProductRequestDTO.getQuantity();

        if (money > buyProductRequestDTO.getMoneyYouHave()) {
            throw new NoEnoughMoney("No enough money to this quantity from product with id " + id);
        }

        product.reduceStockQuantity(buyProductRequestDTO.getQuantity());
        product = productRepository.save(product);

        return productMapper.toResponse(product);
    }
}
