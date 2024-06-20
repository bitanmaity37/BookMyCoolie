package com.cdac.iaf.bookmycoolie.utils;

import java.util.HashMap;
import java.util.Map;

public class ServiceTypeUtil {

    private static final Map<Integer, String> SERVICE_TYPE_MAP = new HashMap<>();

    static {
        SERVICE_TYPE_MAP.put(1, "Coolie");
        SERVICE_TYPE_MAP.put(2, "Cart");
        SERVICE_TYPE_MAP.put(3, "Wheel Chair");
    }

    public static String getServiceTypeName(int serviceTypeId) {
        return SERVICE_TYPE_MAP.get(serviceTypeId);
    }
}
