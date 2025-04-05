package com.srp.Restaurant.Management.SERVICES.CUSTOMER;

import com.srp.Restaurant.Management.DTO.CategoryDTO;
import com.srp.Restaurant.Management.DTO.ProductDTO;
import com.srp.Restaurant.Management.DTO.ReservationDTO;
import com.srp.Restaurant.Management.ENTITIES.Category;
import com.srp.Restaurant.Management.ENTITIES.Product;
import com.srp.Restaurant.Management.ENTITIES.Reservation;
import com.srp.Restaurant.Management.ENTITIES.User;
import com.srp.Restaurant.Management.ENUMS.ReservationStatus;
import com.srp.Restaurant.Management.REPOSITORY.CategoryRepository;
import com.srp.Restaurant.Management.REPOSITORY.ProductRepository;
import com.srp.Restaurant.Management.REPOSITORY.ReservationRepository;
import com.srp.Restaurant.Management.REPOSITORY.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;
    public CustomerServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getCategoryDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<CategoryDTO> getAllCategoriesByName(String title) {
        return categoryRepository.findAllByNameContaining(title)
                .stream()
                .map(Category::getCategoryDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductDTO> getProductByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndTitle(Long categoryId, String title) {
        return productRepository.findAllByCategoryIdAndNameContainingIgnoreCase(categoryId, title)
                .stream()
                .map(Product::getProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO postCategory(ReservationDTO reservationDTO) {
        Optional<User> optionalUser=userRepository.findById(reservationDTO.getCustomerId());
        if(optionalUser.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setTableType(reservationDTO.getTableType());
            reservation.setDateTime(reservationDTO.getDateTime());
            reservation.setDescription(reservationDTO.getDescription());
            reservation.setUser(optionalUser.get());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            Reservation postedReservation = reservationRepository.save(reservation);
            ReservationDTO postedReservationDTO = new ReservationDTO();
            postedReservationDTO.setId(postedReservation.getId());
            return postedReservationDTO;
        }
        return null;
    }

    @Override
    public List<ReservationDTO> getReservationsByUser(Long customerId) {
        return reservationRepository.findAllByUserId(customerId)
                .stream()
                .map(Reservation::getReservationDTO)
                .collect(Collectors.toList());
    }

}
