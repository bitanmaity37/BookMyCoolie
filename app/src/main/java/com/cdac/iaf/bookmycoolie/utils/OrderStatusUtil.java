package com.cdac.iaf.bookmycoolie.utils;

import com.cdac.iaf.bookmycoolie.R;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusUtil {

    private static final Map<Integer, String> ORDER_STATUS = new HashMap<>();
    private static final Map<Integer, Integer> ORDER_STATUS_COLOR = new HashMap<>();

    static {
        ORDER_STATUS.put(1, "PENDING");
        ORDER_STATUS.put(2, "ASSIGNED");
        ORDER_STATUS.put(3, "COMPLETED");
        ORDER_STATUS.put(4, "CANCELLED");

        ORDER_STATUS_COLOR.put(1, R.color.pending);
        ORDER_STATUS_COLOR.put(2, R.color.assigned);
        ORDER_STATUS_COLOR.put(3, R.color.completed);
        ORDER_STATUS_COLOR.put(4, R.color.canceled);
    }

    public static String getOrderStatus(int status) {
        return ORDER_STATUS.get(status);
    }

    public static int getColor(int status) {
        System.out.println("ORDER_STATUS_COLOR: "+ORDER_STATUS_COLOR.get(status));
        return ORDER_STATUS_COLOR.getOrDefault(status, R.color.black);
    }

}
