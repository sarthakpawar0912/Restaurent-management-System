package com.srp.Restaurant.Management.SERVICES.ADMIN;

import com.srp.Restaurant.Management.DTO.CategoryDTO;
import com.srp.Restaurant.Management.DTO.ProductDTO;
import com.srp.Restaurant.Management.DTO.ReservationDTO;
import com.srp.Restaurant.Management.ENTITIES.Category;
import com.srp.Restaurant.Management.ENTITIES.Product;
import com.srp.Restaurant.Management.ENTITIES.Reservation;
import com.srp.Restaurant.Management.ENUMS.ReservationStatus;
import com.srp.Restaurant.Management.REPOSITORY.CategoryRepository;
import com.srp.Restaurant.Management.REPOSITORY.ProductRepository;
import com.srp.Restaurant.Management.REPOSITORY.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ReservationRepository  reservationRepository;
    public AdminServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ReservationRepository reservationRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public CategoryDTO postCategory(CategoryDTO categoryDTO) throws IOException {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        // Convert image to byte array
        MultipartFile imageFile = categoryDTO.getImg();
        if (imageFile != null && !imageFile.isEmpty()) {
            category.setImg(imageFile.getBytes());
        }


        // Save category to database
        Category createdCategory = categoryRepository.save(category);


        // Map saved entity to DTO
        CategoryDTO createdCategoryDTO = new CategoryDTO();
        createdCategoryDTO.setId(createdCategory.getId());
        createdCategoryDTO.setName(createdCategory.getName());
        createdCategoryDTO.setDescription(createdCategory.getDescription());
        createdCategoryDTO.setReturnedImg(createdCategory.getImg()); // Return image bytes if needed

        return createdCategoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllCategoriesByTitle(String title) {
        return categoryRepository.findAllByNameContaining(title)
                .stream()
                .map(Category::getCategoryDTO)
                .collect(Collectors.toList());
    }

    //product operations
    // Fixed product creation method
    @Override
    public ProductDTO postProduct(Long categoryId, ProductDTO productDTO) throws IOException {
        // ✅ Check if category exists
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Product product = new Product();
            BeanUtils.copyProperties(productDTO, product);

            // ✅ Handle image upload properly
            if (productDTO.getImg() != null && !productDTO.getImg().isEmpty()) {
                product.setImg(productDTO.getImg().getBytes());
            }

            product.setCategory(optionalCategory.get());
            Product createdProduct = productRepository.save(product);

            ProductDTO createdProductDTO = new ProductDTO();
            createdProductDTO.setId(createdProduct.getId());

            return createdProductDTO;
        }
        return null;  // ✅ Return null if category not found
    }

    @Override
    public List<ProductDTO> getAllProductsByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(Product::getProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByCategoriesAndTitle(Long categoryId, String title) {
        return productRepository.findAllByCategoryIdAndNameContaining(categoryId, title)
                .stream()
                .map(Product::getProductDTO)
                .collect(Collectors.toList());
    }

    // ✅ Fixed Delete Product Method
    @Override
    public boolean deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(productId);
            return true;
        }
        return false; // Product not found
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());

            if (productDTO.getImg() != null && !productDTO.getImg().isEmpty()) {
                product.setImg(productDTO.getImg().getBytes());
            }

            Product updatedProduct = productRepository.save(product);

            return updatedProduct.getProductDTO();
        }

        return null;
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    ProductDTO dto = product.getProductDTO();
                    dto.setReturnedImg(Base64.getEncoder().encodeToString(product.getImg()).getBytes()); // Encode image
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public List<ReservationDTO> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(Reservation::getReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO ChangeReservationStatus(Long reservationId, String status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();

            if (status.equalsIgnoreCase("APPROVE")) {  // ✅ Fixed Case Sensitivity
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            } else if (status.equalsIgnoreCase("DISAPPROVE")) {
                existingReservation.setReservationStatus(ReservationStatus.DISAPPROVED);
            } else {
                return null; // Invalid status
            }

            Reservation updatedReservation = reservationRepository.save(existingReservation);
            return updatedReservation.getReservationDTO();
        }
        return null;
    }

}
