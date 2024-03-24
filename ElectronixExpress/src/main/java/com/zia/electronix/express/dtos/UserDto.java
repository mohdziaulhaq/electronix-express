package com.zia.electronix.express.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;

    @Size(min=3, max = 15, message= "Invalid name")
    private String name;

//    @Email(message = "Invalid email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(min = 4,max = 6, message = "Invalid gender")
    private String gender;
}
