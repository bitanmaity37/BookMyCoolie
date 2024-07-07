package com.cdac.iaf.bookmycoolie.utils;

import com.cdac.iaf.bookmycoolie.R;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusUtil {

    private static final Map<Integer, String> ORDER_STATUS = new HashMap<>();
    private static final Map<Integer, Integer> ORDER_STATUS_COLOR = new HashMap<>();
    private static final Map<Integer, Integer> ORDER_STATUS_CARD_BG = new HashMap<>();

    static {
        ORDER_STATUS.put(1, "PENDING");
        ORDER_STATUS.put(2, "ASSIGNED");
        ORDER_STATUS.put(3, "COMPLETED");
        ORDER_STATUS.put(4, "CANCELLED");

        ORDER_STATUS_COLOR.put(1, R.color.pending);
        ORDER_STATUS_COLOR.put(2, R.color.assigned);
        ORDER_STATUS_COLOR.put(3, R.color.completed);
        ORDER_STATUS_COLOR.put(4, R.color.cancelled);

        ORDER_STATUS_CARD_BG.put(1, R.color.pending_card);
        ORDER_STATUS_CARD_BG.put(2, R.color.assigned_card);
        ORDER_STATUS_CARD_BG.put(3, R.color.completed_card);
        ORDER_STATUS_CARD_BG.put(4, R.color.cancelled_card);

    }

    public static String getOrderStatus(int status) {
        return ORDER_STATUS.get(status);
    }

    public static int getColor(int status) {
        return ORDER_STATUS_COLOR.getOrDefault(status, R.color.black);
    }

    public static int getBgColor(int status) {
        return ORDER_STATUS_CARD_BG.getOrDefault(status, R.color.white);
    }

}
