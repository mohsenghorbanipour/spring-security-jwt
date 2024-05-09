package com.devpro.jwt.repositories;

import com.devpro.jwt.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
