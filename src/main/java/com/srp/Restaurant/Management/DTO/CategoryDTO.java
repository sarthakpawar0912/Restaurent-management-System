package com.srp.Restaurant.Management.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;
    private MultipartFile img;
    private byte[] returnedImg;

}
