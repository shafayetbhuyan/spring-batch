package com.shafayet.spring_batch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record StudentCsvDto(
                            @NotBlank(message = "First name is required")
                            @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
                            String firstName,
                            @NotBlank(message = "Last name is required")
                            @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
                            String lastName,
                            @NotNull(message = "Age is required")
                            @Min(value = 18, message = "Age must be at least 18")
                            @Max(value = 100, message = "Age must be less than 100")
                            Integer age,
                            @JsonFormat(pattern = "yyyy-MM-dd")
                            LocalDate dateOfBirth,
                            @NotBlank(message = "Email is required")
                            @Email(message = "Email should be valid")
                            String email,
                            @Pattern(regexp = "^[0-9]{11}$", message = "Phone number must be 11 digits")
                            String phoneNumber) {
}
