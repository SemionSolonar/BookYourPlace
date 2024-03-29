package com.example.bookyourplace.model.Maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bookyourplace.R;
import com.example.bookyourplace.model.hotel_manager.Hotel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HotelOnMap extends Fragment implements OnMapReadyCallback {

    private FirebaseFirestore firestore;
    private static final int REQUEST_FINE_LOCATION = 40;

    private FloatingActionButton fab_parent_map, fab_satellite, fab_terrain, fab_normal;
    private TextView tv_satellite, tv_terrain, tv_normal;
    private Boolean isAllMapsFabsVisible;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;

    private SearchView searchView;

    private LinkedHashMap<Hotel,String> hotelResults = new LinkedHashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_hotelOnMap_to_traveler_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.traveler_fragment_hotel_on_map, container, false);

        initializeElements(root);

        clickListener(root);

        return root;
    }

    private void initializeElements(View root) {
      //databaseReference = FirebaseDatabase.getInstance().getReference().child("Hotel");
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference hotelsRef = firestore.collection("hotels");


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);

        floatingActionButtonsMapStyleInitialize(root);

        searchViewInitialize(root);

    }

    private void floatingActionButtonsMapStyleInitialize(View root) {

        fab_parent_map = root.findViewById(R.id.fab_parent_map);
        fab_satellite = root.findViewById(R.id.fab_satellite);
        fab_terrain = root.findViewById(R.id.fab_terrain);
        fab_normal = root.findViewById(R.id.fab_normal);

        tv_satellite = root.findViewById(R.id.tv_satellite);
        tv_terrain = root.findViewById(R.id.tv_terrain);
        tv_normal = root.findViewById(R.id.tv_normal);

        fab_satellite.setVisibility(View.GONE);
        fab_terrain.setVisibility(View.GONE);
        fab_normal.setVisibility(View.GONE);

        tv_satellite.setVisibility(View.GONE);
        tv_terrain.setVisibility(View.GONE);
        tv_normal.setVisibility(View.GONE);

        tv_satellite.bringToFront();
        tv_terrain.bringToFront();
        tv_normal.bringToFront();

        isAllMapsFabsVisible = false;
    }


    private void searchViewInitialize(View root) {
        searchView = root.findViewById(R.id.sv_location_OnMap);

        TextView textView = searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.WHITE);

        ImageView searchClose = searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null));
        searchClose.setColorFilter(Color.WHITE);

        ImageView searchMag = searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null));
        searchMag.setColorFilter(Color.WHITE);
    }

    private void clickListener(View root) {
        floatingActionButtonsMapStyleClickListener();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString().trim();
                List<Address> addresses = null;

                if(location != null || !location.isEmpty()){
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addresses =  geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!addresses.isEmpty()){
                        android.location.Address address = addresses.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));

                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void floatingActionButtonsMapStyleClickListener(){
        fab_parent_map.setOnClickListener(view -> {

            if (!isAllMapsFabsVisible) {
                fab_satellite.show();
                fab_terrain.show();
                fab_normal.show();

                tv_satellite.setVisibility(View.VISIBLE);
                tv_terrain.setVisibility(View.VISIBLE);
                tv_normal.setVisibility(View.VISIBLE);

                isAllMapsFabsVisible = true;

            } else {

                fab_satellite.hide();
                fab_terrain.hide();
                fab_normal.hide();

                tv_satellite.setVisibility(View.GONE);
                tv_terrain.setVisibility(View.GONE);
                tv_normal.setVisibility(View.GONE);

                isAllMapsFabsVisible = false;
            }
        });

        fab_terrain.setOnClickListener(view -> {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            fab_parent_map.performClick();
        });

        fab_satellite.setOnClickListener(view -> {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            fab_parent_map.performClick();
        });

        fab_normal.setOnClickListener(view -> {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            fab_parent_map.performClick();
        });
    }

    private void getListHotel() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference hotelsRef = firestore.collection("hotels");

        hotelsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Hotel hotel = document.toObject(Hotel.class);
                    if (hotel != null) {
                        hotelResults.put(hotel, document.getId());
                    }
                }
                creatMarkers(hotelResults);
            } else {
                Log.e("Error", task.getException().getMessage());
            }
        });
    }


    private void creatMarkers(LinkedHashMap<Hotel, String> hotels) {
        for (Map.Entry<Hotel, String> values : hotels.entrySet()) {
            Hotel hotel = values.getKey();
            String snippet = hotel.getCoverPhoto() + "«" + hotel.getAddress().getCity() + "«" + hotel.getPrice() + "«" + hotel.getStars() + "«" + values.getValue();
            MarkerOptions markerOptions = new MarkerOptions().position(hotel.getAddress().getCoordinates()).title(hotel.getName()).snippet(snippet);
            Marker marker = mMap.addMarker(markerOptions);
            marker.showInfoWindow();
            marker.hideInfoWindow();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_FINE_LOCATION);
            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        mMap = googleMap;
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
            if (location != null) {
                LatLng newlatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newlatLng, 18));
            }
        });


        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);


        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);


        mMap.setOnInfoWindowClickListener(marker -> {
            String[] info = marker.getSnippet().split("«");
            NavDirections action = HotelOnMapDirections.actionHotelOnMapToHotelViewer("",info[4],"clickDetails");
            Navigation.findNavController(getView()).navigate(action);
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.traveler_fragment_hotel_on_map_marker, null);

                ConstraintLayout cl_onMap = v.findViewById(R.id.cl_onMap);

                TextView name = v.findViewById(R.id.hotel_Name_onMap);
                TextView city = v.findViewById(R.id.hotel_City_onMap);
                TextView price = v.findViewById(R.id.hotel_Price_onMap);
                ImageView photo = v.findViewById(R.id.hotel_CoverPhoto_onMap);
                RatingBar ratingBar = v.findViewById(R.id.hotel_Rating_onMap);

                Log.e("Marker Info", marker.getSnippet());

                String[] info = marker.getSnippet().split("«");

                name.setText(marker.getTitle());

                Picasso.get().load(Uri.parse(info[0])).into(photo);

                city.setText(info[1]);
                price.setText(info[2]);
                ratingBar.setRating(Float.parseFloat(info[3]));

                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        getListHotel();
    }

}
