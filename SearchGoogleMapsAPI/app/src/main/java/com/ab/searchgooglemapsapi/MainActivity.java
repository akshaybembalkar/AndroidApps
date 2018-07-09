package com.ab.searchgooglemapsapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.searchgooglemapsapi.adapter.CustomAdapter;
import com.ab.searchgooglemapsapi.retrofit_models.AddressList;
import com.ab.searchgooglemapsapi.retrofit_models.AddressModel;
import com.ab.searchgooglemapsapi.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    ProgressDialog progressDialog;
    private Button searchButton, displayAllButton;
    private ListView locationList;
    private EditText searchField;
    private static List<AddressModel> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.searchButton);
        displayAllButton = findViewById(R.id.displayAllButton);
        locationList = findViewById(R.id.locationList);
        searchField = findViewById(R.id.searchField);

        addressList = new ArrayList<>();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationName = searchField.getText().toString();
                searchLocation(locationName);
            }
        });

        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(addressList);
                try {
                    double lat = addressList.get(i).getGeometry().getLocation().getLatitude();
                    double lng = addressList.get(i).getGeometry().getLocation().getLongitude();
                    String address = addressList.get(i).getFormattedAddress();
                    String locationId = addressList.get(i).getPlaceId();
                    Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                    Bundle b = new Bundle();
                    b.putDouble("Lat", lat);
                    b.putDouble("Lng", lng);
                    b.putString("address", address);
                    b.putString("locationId", locationId);
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Location co-ordinates missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        displayAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                intent.putParcelableArrayListExtra("addressList", new ArrayList<>(addressList));
                startActivity(intent);
            }
        });
    }

    public void searchLocation(String locationName) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<AddressList> call = service.getLocationByAddress(locationName);
        call.enqueue(new Callback<AddressList>() {

            @Override
            public void onResponse(Call<AddressList> call, Response<AddressList> response) {
//                System.out.println(response.body());
                displayAllLocations(response.body());
            }

            @Override
            public void onFailure(Call<AddressList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Display search result in List View*/
    public void displayAllLocations(AddressList list) {
        // System.out.println(list);
        addressList = list.getAddressList();
        adapter = new CustomAdapter(this, addressList);
        locationList.setAdapter(adapter);
    }
}
