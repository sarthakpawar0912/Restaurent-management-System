package com.srp.Restaurant.Management.DTO;

import com.srp.Restaurant.Management.ENUMS.ReservationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {

    private Long id;

    private String tableType;

    private String description;

    private Date dateTime;

    private ReservationStatus reservationStatus;

    private Long customerId;

    private String customerName;  // ✅ This should hold the username

}
