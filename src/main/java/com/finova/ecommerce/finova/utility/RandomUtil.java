package com.finova.ecommerce.finova.utility;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    public static String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 12);
    }

    public static String approvalCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
