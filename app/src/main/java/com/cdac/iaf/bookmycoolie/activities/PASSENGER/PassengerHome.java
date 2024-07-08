package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.OnGoingRequestAdapter;
import com.cdac.iaf.bookmycoolie.models.OrderDetailsModel;
import com.cdac.iaf.bookmycoolie.models.OrderStatusModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.OrderStatusUtil;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.cdac.iaf.bookmycoolie.utils.NetworkUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerHome extends AppCompatActivity {

    MaterialCardView bookCoolieCard;
    String authToken;
    RecyclerView recyclerView;
    int userId;
    MaterialToolbar navbarMenu;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    boolean isNetworkAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_home);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(PassengerHome.this);
            authToken = securedSharedPreferenceUtils.getLoginData().getJwtToken();
            userId = securedSharedPreferenceUtils.getLoginData().getUserId();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        navbarMenu = findViewById(R.id.topAppBar);

        navbarMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logout) {
                try {
                    InvalidateUser.invalidate(PassengerHome.this);
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
            return false;
        });

        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Handle profile item click
                startActivity(new Intent(PassengerHome.this, PassengerHome.class));
                return true;
            } else if (item.getItemId() == R.id.faq_item) {
                // Handle FAQ item click
                startActivity(new Intent(PassengerHome.this, PassengerFaqActivity.class));
                return true;
            } else if (item.getItemId() == R.id.contact_item) {
                // Handle contact item click
                startActivity(new Intent(PassengerHome.this, PassengerContactUsActivity.class));
                return true;
            }
            return false;
        });


        bookCoolieCard = findViewById(R.id.book_a_coolie);
        bookCoolieCard.setCardElevation(8f);

        bookCoolieCard.setOnClickListener(v -> {
            BookCoolieService bookCoolieService = new BookCoolieService(PassengerHome.this, authToken, getSupportFragmentManager(),userId);
            bookCoolieService.showCoolieBottomSheet();
        });

        MaterialCardView bookCartCard = findViewById(R.id.book_a_cart);
        bookCartCard.setCardElevation(8f);
        bookCartCard.setOnClickListener(v -> {
            BookCartService bookCartService = new BookCartService(PassengerHome.this, authToken, getSupportFragmentManager(),userId);
            bookCartService.showCoolieBottomSheet();
        });

        MaterialCardView bookChairCard = findViewById(R.id.book_a_chair);
        bookChairCard.setCardElevation(8f);
        bookChairCard.setOnClickListener(v -> {
            BookWheelChairService bookWheelChairService = new BookWheelChairService(PassengerHome.this, authToken, getSupportFragmentManager(),userId);
            bookWheelChairService.showCoolieBottomSheet();
        });

        MaterialCardView orderHistoryCard = findViewById(R.id.order_history_card);
        orderHistoryCard.setCardElevation(8f);
        orderHistoryCard.setOnClickListener(v -> {
            startActivity(new Intent(PassengerHome.this, PassengerOrderActivity.class));
        });

        fetchOrderDetailsByReqId();
        /*OnGoingRequest onGoingRequest = new OnGoingRequest(PassengerHome.this, authToken, userId, this);
        onGoingRequest.fetchOrderDetailsByReqId();*/
        //setCarouselImages();
    }

    public void fetchOrderDetailsByReqId() {
        ArrayList<OrderStatusModel> orderStatusModels = new ArrayList<>();
        ArrayList<OrderDetailsModel> allOrderDetailsByReqId = new ArrayList<>();
        Map<Integer, ArrayList<OrderDetailsModel>> map = new HashMap<>();
        int pendingRequests = 0;
        //System.out.println(userId);
        Call<ArrayList<OrderStatusModel>> orderStatusModelCall = RestClient.getRetrofitClient()
                .create(RestInterface.class)
                .getOrderHistoryByPassengerId(authToken, userId);
        orderStatusModelCall.enqueue(new Callback<ArrayList<OrderStatusModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<OrderStatusModel>> call, @NonNull Response<ArrayList<OrderStatusModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderStatusModels.addAll(response.body());
                    AtomicInteger pendingRequests = new AtomicInteger(orderStatusModels.size());
                    for (OrderStatusModel orderStatusModel : orderStatusModels) {
                       int requestStatus = orderStatusModel.getRequestStatus();
                        //Toast.makeText(PassengerHome.this, "requestStatus: "+requestStatus, Toast.LENGTH_SHORT).show();
                        if (requestStatus == 1 || requestStatus == 2) {
                            if (OrderStatusUtil.getOrderStatus(orderStatusModel.getRequestStatus()).equalsIgnoreCase("assigned")) {
                                getCoolieForOngoingRequest(orderStatusModel.getPassengerRequestId(), orderDetails -> {
                                    allOrderDetailsByReqId.addAll(orderDetails);
                                    map.put(orderStatusModel.getPassengerRequestId(), orderDetails);
                                    pendingRequests.getAndDecrement();
                                    if (pendingRequests.get() == 0) {
                                        setAdapters(orderStatusModels, map);
                                    }
                                });
                            } else {
                                pendingRequests.getAndDecrement();
                            }
                        } else {
                            pendingRequests.getAndDecrement();
                        }
                    }
                    if (pendingRequests.get() == 0) {
                        setAdapters(orderStatusModels, map);
                    }
                    //Toast.makeText(PassengerHome.this, orderStatusModels.get(0).toString(), Toast.LENGTH_SHORT).show();
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(PassengerHome.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<OrderStatusModel>> call, @NonNull Throwable t) {
                try {
                    InvalidateUser.invalidate(PassengerHome.this);
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(PassengerHome.this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapters(ArrayList<OrderStatusModel> orderStatusModels, Map<Integer, ArrayList<OrderDetailsModel>> map){
        recyclerView = findViewById(R.id.carousel_recycler_view);
        OnGoingRequestAdapter carouselAdapter = new OnGoingRequestAdapter(orderStatusModels, PassengerHome.this, authToken, map);
        recyclerView.setAdapter(carouselAdapter);
        carouselAdapter.setOnItemClickListener((textView, reqId, status) -> {
            Intent intent = new Intent(PassengerHome.this, PassengerOrderDetailsActivity.class);
            intent.putExtra("reqId", reqId);
            intent.putExtra("status", status);
            startActivity(intent);
        });
    }

    public ArrayList<OrderDetailsModel> getCoolieForOngoingRequest(int reqId, CoolieDetailsCallBack coolieDetailsCallBack){
        ArrayList<OrderDetailsModel> orderDetailsByReqId = new ArrayList<>();
        Call<ArrayList<OrderDetailsModel>> orderStatusModelCall = RestClient.getRetrofitClient()
                .create(RestInterface.class)
                .getOrderDetailsByRequestId(authToken, reqId);
        orderStatusModelCall.enqueue(new Callback<ArrayList<OrderDetailsModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<OrderDetailsModel>> call, @NonNull Response<ArrayList<OrderDetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("response.body() in ongoingreq: "+response.body());
                    orderDetailsByReqId.addAll(response.body());
                    System.out.println("orderDetailsByReqId: "+orderDetailsByReqId.size());
                    coolieDetailsCallBack.onOrderDetailsFetched(orderDetailsByReqId);
                }
                else{
                    coolieDetailsCallBack.onOrderDetailsFetched(new ArrayList<>());
                    Toast.makeText(PassengerHome.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<OrderDetailsModel>> call, @NonNull Throwable t) {
                Toast.makeText(PassengerHome.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
        return orderDetailsByReqId;
    }


    /*public void setCarouselImages() {

        recyclerView = findViewById(R.id.carousel_recycler_view);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Raipur");
        arrayList.add("Bhilai");
        arrayList.add("Tilda");
        arrayList.add("Bhatapara");
        arrayList.add("Durg");

        CarouselAdapter adapter = new CarouselAdapter(PassengerHome.this, arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                startActivity(new Intent(PassengerHome.this, CarouselAdapter.class).putExtra("image", path), ActivityOptions.makeSceneTransitionAnimation(PassengerHome.this, imageView, "image").toBundle());
            }
        });

    }
*/

}