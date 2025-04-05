package com.srp.Restaurant.Management.CONTROLLER.CUSTOMER;


import com.srp.Restaurant.Management.DTO.CategoryDTO;
import com.srp.Restaurant.Management.DTO.ProductDTO;
import com.srp.Restaurant.Management.DTO.ReservationDTO;
import com.srp.Restaurant.Management.SERVICES.CUSTOMER.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOList = customerService.getAllCategories();
        if (categoryDTOList.isEmpty()) {  // Changed from null check
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryDTOList);
    }

    @GetMapping("/categories/{title}")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesByName(@PathVariable String title) {
        List<CategoryDTO> categoryDTOList = customerService.getAllCategoriesByName(title);
        if (categoryDTOList.isEmpty()) {  // Changed from null check
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryDTOList);
    }

    //product operations

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> productDTOList = customerService.getProductByCategory(categoryId);
        if (productDTOList.isEmpty()) {  // Changed from null check
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDTOList);
    }

    @GetMapping("/{categoryId}/products/{title}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryAndTitle(
            @PathVariable Long categoryId,
            @PathVariable String title
    ) {
        List<ProductDTO> productDTOList = customerService.getProductsByCategoryAndTitle(categoryId, title);
        if (productDTOList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> postReservation(@RequestBody ReservationDTO reservationDTO) throws IOException {
        ReservationDTO   postedReservationDTO=customerService.postCategory(reservationDTO);
        if(postedReservationDTO == null){
            return new ResponseEntity<>("Something Went Wrong...", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postedReservationDTO);
    }

    @GetMapping("reservations/{customerId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable Long customerId) {
        List<ReservationDTO> reservationDTOList = customerService.getReservationsByUser(customerId);
        if (reservationDTOList.isEmpty()) {
            return ResponseEntity.noContent().build();  // Changed from notFound() for better response
        }
        return ResponseEntity.ok(reservationDTOList);
    }

}
