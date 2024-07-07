package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import com.cdac.iaf.bookmycoolie.models.OrderDetailsModel;

import java.util.ArrayList;

public interface CoolieDetailsCallBack {

    void onOrderDetailsFetched(ArrayList<OrderDetailsModel> orderDetails);

}
