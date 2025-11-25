package com.example.JobTask.contorller;

import com.example.JobTask.dto.product.BuyProductRequestDTO;
import com.example.JobTask.dto.product.ProductRequestDTO;
import com.example.JobTask.dto.product.ProductResponseDTO;
import com.example.JobTask.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?>createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){

        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){

        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){

        List<ProductResponseDTO>productResponseDTOList = productService.getAllProducts();
        return ResponseEntity.ok(productResponseDTOList);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id,@Valid @RequestBody ProductRequestDTO productRequestDTO){

        ProductResponseDTO productResponseDTO = productService.updateProductById(id,productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){

        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<?> buyProduct(@Valid @RequestBody BuyProductRequestDTO buyProductRequestDTO){

        ProductResponseDTO productResponseDTO = productService.buyProduct(buyProductRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }
}
