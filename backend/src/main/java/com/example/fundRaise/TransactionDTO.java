package com.example.fundRaise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Phone number must be 10 to 13 digits")
    private String phone;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive value")
    private BigDecimal amount;
}
