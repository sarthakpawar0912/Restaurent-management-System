package com.srp.Restaurant.Management.SERVICES.ADMIN;

import com.srp.Restaurant.Management.DTO.CategoryDTO;
import com.srp.Restaurant.Management.DTO.ProductDTO;
import com.srp.Restaurant.Management.DTO.ReservationDTO;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    CategoryDTO postCategory(CategoryDTO categoryDTO) throws IOException;

    List<CategoryDTO> getAllCategories();


    List<CategoryDTO> getAllCategoriesByTitle(String title);


    ProductDTO postProduct(Long categoryId, ProductDTO productDTO)  throws IOException;

    List<ProductDTO> getAllProductsByCategory(Long categoryId);

    List<ProductDTO> getAllProductsByCategoriesAndTitle(Long categoryId, String title);

    boolean deleteProduct(Long productId);


    ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws IOException;

    ProductDTO getProductById(Long productId);

    List<ReservationDTO> getReservations();

    ReservationDTO ChangeReservationStatus(Long reservationId, String status);
}
