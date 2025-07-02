package com.works.entities.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductAddDto implements Serializable {
    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    String title;
    @NotNull
    @Min(2)
    @Max(1000000)
    Integer price;
}