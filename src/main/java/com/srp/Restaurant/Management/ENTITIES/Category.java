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






    public Category(Long id, String name, String description, byte[] img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Category() {
    }
}
