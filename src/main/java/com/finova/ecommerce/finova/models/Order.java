package com.finova.ecommerce.finova.models;

import com.finova.ecommerce.finova.enums.TypeRid;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Currency;

public class Order {
    private TypeRid typeRid;
    private BigDecimal amount;
    private Currency currency;
    private String language;
    private String description;
    private URL hppRedirectUrl;
    private ArrayList hppCofCapturePurposes;

}
