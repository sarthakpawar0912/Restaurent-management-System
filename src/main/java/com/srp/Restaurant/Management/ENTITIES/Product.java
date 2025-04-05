package com.srp.Restaurant.Management.ENTITIES;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.srp.Restaurant.Management.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String price;
    private String description;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
    @ManyToOne (fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;
    public ProductDTO getProductDTO(){
          ProductDTO productDTO=new ProductDTO();
          productDTO.setId(id);
          productDTO.setName(name);
          productDTO.setPrice(price);
          productDTO.setDescription(description);
          productDTO.setReturnedImg(img);
          productDTO.setCategoryId(category.getId());
          productDTO.setCategoryName(category.getName());

          return productDTO;
      }
}
