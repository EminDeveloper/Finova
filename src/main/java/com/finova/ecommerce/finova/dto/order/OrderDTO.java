package com.finova.ecommerce.finova.dto.order;

import lombok.Data;

@Data
public class OrderDTO {
    private String typeRid;
    private String amount;
    private String currency;
    private String language;
    private String description;
    private String hppRedirectUrl;
}