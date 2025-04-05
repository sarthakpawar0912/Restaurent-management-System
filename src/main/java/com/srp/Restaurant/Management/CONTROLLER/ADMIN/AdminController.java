package com.srp.Restaurant.Management.CONTROLLER.ADMIN;

import com.srp.Restaurant.Management.DTO.CategoryDTO;
import com.srp.Restaurant.Management.DTO.ProductDTO;
import com.srp.Restaurant.Management.DTO.ReservationDTO;
import com.srp.Restaurant.Management.ENTITIES.Category;
import com.srp.Restaurant.Management.SERVICES.ADMIN.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private   final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> postCategory(@ModelAttribute CategoryDTO categoryDTO) throws IOException {
       CategoryDTO  createdCategoryDTO=adminService.postCategory(categoryDTO);
       if(createdCategoryDTO == null){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(createdCategoryDTO);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOList = adminService.getAllCategories();
        if (categoryDTOList.isEmpty()) {  // Changed from null check
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryDTOList);
    }
    @GetMapping("/categories/search")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesByTitle(@RequestParam String title) {
        List<CategoryDTO> categoryDTOList = adminService.getAllCategoriesByTitle(title);
        if (categoryDTOList.isEmpty()) {  // Changed from null check
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryDTOList);
    }
   //product operations
   // Fixed incorrect path variable name
   // Create a new product under a specific category
   // ✅ Ensure API uses correct authentication settings
   @PostMapping("/{categoryId}/product")
   public ResponseEntity<?> postProduct(@PathVariable("categoryId") Long categoryId,
                                        @ModelAttribute ProductDTO productDTO) {
       if (categoryId == null || categoryId <= 0) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category ID.");
       }
       try {
           ProductDTO createdProductDTO = adminService.postProduct(categoryId, productDTO);
           if (createdProductDTO == null) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID not found.");
           }
           return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
       } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the image.");
       }
   }


    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> productDTOList = adminService.getAllProductsByCategory(categoryId);
        if (productDTOList.isEmpty()) {  // Changed from null check
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDTOList);
    }


    @GetMapping("/{categoryId}/products/search")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategoriesAndTitle(@PathVariable Long categoryId,@RequestParam String title) {
        List<ProductDTO> productDTOList = adminService.getAllProductsByCategoriesAndTitle(categoryId,title);
        if (productDTOList.isEmpty()) {  // Changed from null check
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDTOList);
    }

    // ✅ Delete Product (Fixed Exception Handling)
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        boolean isDeleted = adminService.deleteProduct(productId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        ProductDTO productDTO = adminService.getProductById(productId);
        if (productDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                                           @ModelAttribute ProductDTO productDTO) throws IOException {
        ProductDTO updatedProductDTO = adminService.updateProduct(productId, productDTO);
        if (updatedProductDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong...!!");
        }
        return ResponseEntity.ok(updatedProductDTO);
    }

    //reservation operations


    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getReservations()  {
        List<ReservationDTO>  reservationDTOList=adminService.getReservations();
        if(reservationDTOList == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationDTOList);
    }


    @GetMapping("/reservation/{reservationId}/{status}")
    public ResponseEntity<ReservationDTO> ChangeReservationStatus(@PathVariable Long reservationId,@PathVariable String status )  {
        ReservationDTO  updatedReservationDTO=adminService.ChangeReservationStatus(reservationId,status);
        if(updatedReservationDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReservationDTO);
    }


}
