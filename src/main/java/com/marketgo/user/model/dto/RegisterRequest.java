package com.marketgo.user.model.dto;

import jakarta.validation.constraints.*;


public record RegisterRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email address")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Enter your state of reference")
        String state,

        @NotBlank(message = "Enter your city of residence")
        String city,

        @NotBlank(message = "Enter your street address")
        String street
) {
}