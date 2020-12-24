package com.poc.sbwf.webflux.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Product {

    @Id
    private String id;

    @NotNull @NotEmpty
    private String name;

    @NotNull @NotEmpty
    private Double price;

    private LocalDateTime createAt;
}
