package com.example.appsecuremy.controller;

import com.example.appsecuremy.entity.Product;
import com.example.appsecuremy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    final ProductRepository productRepository;
    @GetMapping
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    public HttpEntity<?>all(){
        return ResponseEntity.ok(productRepository.findAll());
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody Product product){
     return ResponseEntity.ok(   productRepository.save(product));
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Product myproduct){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(myproduct.getName());
            Product save = productRepository.save(product);
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return ResponseEntity.status(optionalProduct.isPresent()?200:404).body(optionalProduct.orElse(null));
    }
}
