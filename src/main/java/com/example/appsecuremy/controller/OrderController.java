package com.example.appsecuremy.controller;

import com.example.appsecuremy.OrderDto;
import com.example.appsecuremy.entity.Myorder;
import com.example.appsecuremy.repository.OrderRepository;
import com.example.appsecuremy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    final OrderRepository orderRepository;
    final ProductRepository productRepository;
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','OPERATOR ')")
    @GetMapping
    public HttpEntity<?>all(){
        return ResponseEntity.ok(orderRepository.findAll());
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','OPERATOR ')")
    @GetMapping("/{id}")
    public HttpEntity<?>getById(@PathVariable Integer id){
        Optional<Myorder> optionalMyorder = orderRepository.findById(id);
        return ResponseEntity.status(optionalMyorder.isPresent()?200:404).body(optionalMyorder.orElse(null));
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','OPERATOR ')")
    @PostMapping
    public HttpEntity<?>addOrder(@RequestBody OrderDto dto){
        Myorder myorder=new Myorder(productRepository.findById(dto.getProductId()).get(),dto.getAmount());
        return ResponseEntity.ok(orderRepository.save(myorder));
    }
}
