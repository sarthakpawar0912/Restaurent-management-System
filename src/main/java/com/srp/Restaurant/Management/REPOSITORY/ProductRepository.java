package com.srp.Restaurant.Management.REPOSITORY;

import com.srp.Restaurant.Management.DTO.ProductDTO;
import com.srp.Restaurant.Management.ENTITIES.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long categoryId);
    List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String title);

    List<Product> findAllByCategoryIdAndNameContainingIgnoreCase(Long categoryId, String title);
}
