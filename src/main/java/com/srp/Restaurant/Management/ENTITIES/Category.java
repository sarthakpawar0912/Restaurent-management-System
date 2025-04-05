package com.srp.Restaurant.Management.ENTITIES;

import com.srp.Restaurant.Management.DTO.CategoryDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    public CategoryDTO getCategoryDTO(){
        CategoryDTO categoryDTO= new CategoryDTO();

        categoryDTO.setId(id);
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        categoryDTO.setReturnedImg(img);

        return categoryDTO;
    }

}
