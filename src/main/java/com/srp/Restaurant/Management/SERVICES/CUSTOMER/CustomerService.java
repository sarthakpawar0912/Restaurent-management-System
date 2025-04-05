package com.srp.Restaurant.Management.SERVICES.CUSTOMER;

import com.srp.Restaurant.Management.DTO.CategoryDTO;
import com.srp.Restaurant.Management.DTO.ProductDTO;
import com.srp.Restaurant.Management.DTO.ReservationDTO;
import com.srp.Restaurant.Management.ENTITIES.Reservation;

import java.util.List;

public interface CustomerService {
    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getAllCategoriesByName(String title);

    List<ProductDTO> getProductByCategory(Long categoryId);

    List<ProductDTO> getProductsByCategoryAndTitle(Long categoryId, String title);

    ReservationDTO postCategory(ReservationDTO reservationDTO);

    List<ReservationDTO> getReservationsByUser(Long categoryId);
}
