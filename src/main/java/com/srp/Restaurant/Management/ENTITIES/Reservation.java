package com.srp.Restaurant.Management.ENTITIES;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.srp.Restaurant.Management.DTO.ReservationDTO;
import com.srp.Restaurant.Management.ENUMS.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tableType;
    private String  description;
    private Date dateTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public ReservationDTO getReservationDTO() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(id);
        reservationDTO.setTableType(tableType);
        reservationDTO.setReservationStatus(reservationStatus);
        reservationDTO.setDateTime(dateTime);
        reservationDTO.setDescription(description);

        // ✅ Ensuring user is not null before accessing fields
        if (user != null) {
            reservationDTO.setCustomerId(user.getId());
            reservationDTO.setCustomerName(user.getName());  // ✅ Get username
        } else {
            reservationDTO.setCustomerName("Unknown");  // Fallback if user is null
        }

        return reservationDTO;
    }
}
