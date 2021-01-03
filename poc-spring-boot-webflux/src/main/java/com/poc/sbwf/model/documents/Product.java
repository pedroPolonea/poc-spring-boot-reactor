package com.poc.sbwf.model.documents;

import javax.validation.Valid;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document
public class Product {

    @Id
    private String id;

    @NotNull @NotEmpty
    private String name;

    @NotNull
    private Double price;

    private LocalDateTime createAt;

    public Product() {
    }

    public Product(String id, @NotNull @NotEmpty String name, @NotNull @NotEmpty Double price, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}
