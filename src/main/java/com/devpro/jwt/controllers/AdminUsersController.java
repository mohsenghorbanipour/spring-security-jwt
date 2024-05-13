package com.devpro.jwt.controllers;

import com.devpro.jwt.dto.ReqRes;
import com.devpro.jwt.entities.Product;
import com.devpro.jwt.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUsersController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts(@RequestBody ReqRes request) {
        return ResponseEntity.ok(productRepo.findAll());
    }

    @PostMapping("/admin/addproduct")
    public ResponseEntity<Object> addProduct(@RequestBody ReqRes request) {
        Product product = new Product();
        product.setName(request.getName());
        return ResponseEntity.ok(productRepo.save(product));
    }

    @GetMapping("/user/alone")
    public ResponseEntity<String> userAlone() {
        return ResponseEntity.ok("User Alone Access To This Api");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<String> bothAdminUser() {
        return ResponseEntity.ok("Both Admin User Access To This Api");
    }
}
