package com.zia.electronix.express.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
        private String categoryId;
        @NotBlank
        @Size(min = 4, max = 50)
        private String title;

        @NotBlank(message = "description is required")
        private String description;

        private String coverImage;
    }
