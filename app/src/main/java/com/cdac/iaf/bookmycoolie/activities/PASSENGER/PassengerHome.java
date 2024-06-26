package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.adpater.CarouselAdapter;
import com.cdac.iaf.bookmycoolie.databinding.ActivityPassengerHomeBinding;
import com.cdac.iaf.bookmycoolie.models.CoolieRequestModel;
import com.cdac.iaf.bookmycoolie.models.StationAreaModel;
import com.cdac.iaf.bookmycoolie.models.StationModel;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PassengerHome extends AppCompatActivity {

    MaterialCardView bookCoolieCard;
    RecyclerView recyclerView;
    String authToken;
    ActivityPassengerHomeBinding binding;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_home);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(PassengerHome.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authToken = securedSharedPreferenceUtils.getLoginData().getJwtToken();

        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_home);
        //authToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IlJPTEVfUEFTU0FOR0VSIiwic3ViIjoia3VsdmFudGtAY2RhYy5pbiIsImlhdCI6MTcxODI4MTk1MiwiZXhwIjoxNzE4Mjk5OTUyfQ._Rom18LY8VhR7ZpxsobZbR1s-I7wbLRdUFVy68irH9GhrBBkdutWOYU7kHqMRljpFGhRsrhQRMhGfBsbYbaGJA";

        //Toast.makeText(PassengerHome.this, "token " + sharedPreferences.getString("auth_token", null), Toast.LENGTH_LONG).show();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Handle profile item click
                startActivity(new Intent(PassengerHome.this, PassengerHome.class));
                Toast.makeText(PassengerHome.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.faq_item) {
                // Handle FAQ item click
                startActivity(new Intent(PassengerHome.this, PassengerFaqActivity.class));
                Toast.makeText(PassengerHome.this, "FAQ Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.contact_item) {
                // Handle contact item click

                startActivity(new Intent(PassengerHome.this, PassengerContactUsActivity.class));
                Toast.makeText(PassengerHome.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });


        bookCoolieCard = findViewById(R.id.book_a_coolie);
        bookCoolieCard.setCardElevation(8f);

        bookCoolieCard.setOnClickListener(v -> {
            BookCoolieService bookCoolieService = new BookCoolieService(PassengerHome.this, authToken, getSupportFragmentManager(),securedSharedPreferenceUtils);
            bookCoolieService.showCoolieBottomSheet();
        });

        MaterialCardView bookCartCard = findViewById(R.id.book_a_cart);
        bookCartCard.setCardElevation(8f);
        bookCartCard.setOnClickListener(v -> {
            BookCartService bookCartService = new BookCartService(PassengerHome.this, authToken, getSupportFragmentManager(),securedSharedPreferenceUtils);
            bookCartService.showCoolieBottomSheet();
        });

        MaterialCardView bookChairCard = findViewById(R.id.book_a_chair);
        bookChairCard.setCardElevation(8f);
        bookChairCard.setOnClickListener(v -> {
            BookWheelChairService bookWheelChairService = new BookWheelChairService(PassengerHome.this, authToken, getSupportFragmentManager(),securedSharedPreferenceUtils);
            bookWheelChairService.showCoolieBottomSheet();
        });

        MaterialCardView orderHistoryCard = findViewById(R.id.order_history_card);
        orderHistoryCard.setCardElevation(8f);
        orderHistoryCard.setOnClickListener(v -> {
            startActivity(new Intent(PassengerHome.this, PassengerOrderActivity.class));
        });

        logoutButton();
        //setCarouselImages();
    }


    /*public void setCarouselImages() {

        recyclerView = findViewById(R.id.carousel_recycler_view);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("https://images.unsplash.com/photo-1692528131755-d4e366b2adf0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzNXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692862582645-3b6fd47b7513?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692584927805-d4096552a5ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Nnx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692854236272-cc49076a2629?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1681207751526-a091f2c6a538?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODF8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692610365998-c628604f5d9f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODZ8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");

        CarouselAdapter adapter = new CarouselAdapter(PassengerHome.this, arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                startActivity(new Intent(PassengerHome.this, CarouselAdapter.class).putExtra("image", path), ActivityOptions.makeSceneTransitionAnimation(PassengerHome.this, imageView, "image").toBundle());
            }
        });

    }*/

    public void logoutButton(){


    }

}