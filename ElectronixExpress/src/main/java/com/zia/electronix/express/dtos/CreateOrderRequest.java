package com.zia.electronix.express.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotBlank(message = "Cart ID is required")
    private String cartId;

    @NotBlank(message = "User ID is required")
    private String userId;
    private String orderStatus = "PENDING";
    private String paymentStatus = "NOT_PAID";

    @NotBlank(message = "Name is required")
    private String billingName;

    @NotBlank(message = "Address is required")
    private String billingAddress;

    @NotBlank(message = "Phone is required")
    private String billingPhone;
}
