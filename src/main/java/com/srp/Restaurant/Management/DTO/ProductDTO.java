package com.srp.Restaurant.Management.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDTO {

    private Long id;

    private String name;

    private String price;

    private String description;

    private MultipartFile img;

    private byte[] returnedImg;

    private Long categoryId;

    private String categoryName;

}
