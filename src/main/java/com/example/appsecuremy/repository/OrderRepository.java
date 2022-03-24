package com.example.appsecuremy.repository;

import com.example.appsecuremy.entity.Myorder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Myorder,Integer> {
}
