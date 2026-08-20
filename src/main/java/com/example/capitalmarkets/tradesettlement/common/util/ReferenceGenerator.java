package com.example.capitalmarkets.tradesettlement.common.util;

import java.util.UUID;
public final class ReferenceGenerator {
    public ReferenceGenerator() {
    }

    public static String settlementReference(){
        return "SET-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0,8)
                        .toUpperCase();
    }
}
