package com.srp.Restaurant.Management.REPOSITORY;

import com.srp.Restaurant.Management.ENTITIES.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String title);

    List<Product> findAllByCategoryIdAndNameContainingIgnoreCase(Long categoryId, String title);

}
