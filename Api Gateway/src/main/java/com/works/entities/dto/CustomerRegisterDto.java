package com.works.entities.dto;

import com.works.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.works.entities.Customer}
 */
@Value
public class CustomerRegisterDto implements Serializable {
    @NotNull
    @Size(min = 1, max = 100)
    @NotEmpty
    String username;
    @NotNull
    @Size(min = 5, max = 15)
    @NotEmpty
    String password;
    @NotNull
    Boolean enabled;
    @NotNull
    @Size(min = 1, max = 5)
    List<Role> roles;
}