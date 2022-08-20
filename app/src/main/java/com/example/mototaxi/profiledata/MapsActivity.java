// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.mototaxi.profiledata;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.example.mototaxi.R;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback , VolleyTasksListener {

    private GoogleMap mMap;
    // New variables for Current Place Picker
    private static final String TAG = "MapsActivity";

    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    EditText editText,addressnew;
    Button dialogButton,dialogButton1;
    String address,city,state;
    String auuid,label,addressnewdata,lattt,longnew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //
        // PASTE THE LINES BELOW THIS COMMENT
        //
        editText = (EditText) findViewById(R.id.title);
        addressnew = (EditText) findViewById(R.id.address);
        dialogButton = (Button) findViewById(R.id.ok);
        dialogButton1 = (Button) findViewById(R.id.discard);
        // Set up the action toolbar
        Intent intent=new Intent(getIntent());
        if(intent!=null){
            if(intent.getExtras()==null){

            }else {
                String type= intent.getExtras().get("type").toString();
                if (type.equals("add")) {
                   // auuid = intent.getExtras().get("auid").toString();
                }else {
                    auuid = intent.getExtras().get("auid").toString();
                    lattt=intent.getExtras().get("lat").toString();
                    longnew=intent.getExtras().get("long").toString();
                    label=intent.getExtras().get("label").toString();
                    addressnewdata=intent.getExtras().get("address").toString();
                    editText.setText(intent.getExtras().get("label").toString());
                    addressnew.setText(intent.getExtras().get("address").toString());
                }



            }

        }

         dialogButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MapsActivity.this);
                 String mytoken=sharedPreferences.getString("token","");
                 System.out.println("token= "+mytoken);
                 String type= intent.getExtras().get("type").toString();

                 logi1(mytoken,type);


             }
         });
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MapsActivity.this, Profileedit.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                MapsActivity.this.finish();
            }
        });

        CircleImageView circleImageView=(CircleImageView)findViewById(R.id.edit);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickCurrentPlace();
            }
        });

        // Set up the views
        // Initialize the Places client
        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(), apiKey);
        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        String type= intent.getExtras().get("type").toString();
        if (type.equals("add")) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    pickCurrentPlace();
                    // this code will be executed after 2 seconds
                }
            }, 3000);
            // auuid = intent.getExtras().get("auid").toString();
        }else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    pickCurrentPlacenew(Double.parseDouble(lattt),Double.parseDouble(longnew));
                    // this code will be executed after 2 seconds
                }
            }, 3000);
        }


    }

    /**
     * Populates the app bar with the menu.
     */

    /**
     * Handles user clicks on menu items.
     */



    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //
        // PASTE THE LINES BELOW THIS COMMENT
        //

        // Enable the zoom controls for the map
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                pickCurrentPlaceclick(latLng.latitude,latLng.longitude);
            }
        });



        // Prompt the user for permission.
        getLocationPermission();

    }


    private void pickCurrentPlaceclick(Double lat,Double longg) {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocationclick(lat,longg);
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            getDeviceLocationclick(lat,longg);
            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.app_name))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.app_name)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }
    private void getDeviceLocationclick(Double lat,Double lon) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); //
                    editText.setText(city);
                    addressnew.setText(address);

                }catch (Exception e){

                }



                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MapsActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("city", city);
                editor.commit();
                lattt=String.valueOf(mLastKnownLocation.getLatitude());
                longnew=String.valueOf(mLastKnownLocation.getLongitude());
                final LatLng mDefaultLocation1 = new LatLng(lat, lon);
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .title(city)
                        .position(mDefaultLocation1)
                        .snippet(state));
            }
        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Calls the findCurrentPlace method in Google Maps Platform Places API.
     * Response contains a list of placeLikelihood objects.
     * Takes the most likely places and extracts the place details for access in other methods.
     */
    private void getCurrentPlaceLikelihoods() {
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG);

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        @SuppressWarnings("MissingPermission") final FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.builder(placeFields).build();
        Task<FindCurrentPlaceResponse> placeResponse = mPlacesClient.findCurrentPlace(request);
        placeResponse.addOnCompleteListener(this,
                new OnCompleteListener<FindCurrentPlaceResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                        if (task.isSuccessful()) {
                            FindCurrentPlaceResponse response = task.getResult();
                            // Set the count, handling cases where less than 5 entries are returned.
                            int count;
                            if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                                count = response.getPlaceLikelihoods().size();
                            } else {
                                count = M_MAX_ENTRIES;
                            }

                            int i = 0;
                            mLikelyPlaceNames = new String[count];
                            mLikelyPlaceAddresses = new String[count];
                            mLikelyPlaceAttributions = new String[count];
                            mLikelyPlaceLatLngs = new LatLng[count];

                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                Place currPlace = placeLikelihood.getPlace();
                                mLikelyPlaceNames[i] = currPlace.getName();
                                mLikelyPlaceAddresses[i] = currPlace.getAddress();
                                mLikelyPlaceAttributions[i] = (currPlace.getAttributions() == null) ?
                                        null : TextUtils.join(" ", currPlace.getAttributions());
                                mLikelyPlaceLatLngs[i] = currPlace.getLatLng();

                                String currLatLng = (mLikelyPlaceLatLngs[i] == null) ?
                                        "" : mLikelyPlaceLatLngs[i].toString();

                                Log.i(TAG, String.format("Place " + currPlace.getName()
                                        + " has likelihood: " + placeLikelihood.getLikelihood()
                                        + " at " + currLatLng));

                                i++;
                                if (i > (count - 1)) {
                                    break;
                                }
                            }


                            // COMMENTED OUT UNTIL WE DEFINE THE METHOD
                            // Populate the ListView
                           // fillPlacesList();
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                            }
                        }
                    }
                });
    }

    /**
     * Get the current location of the device, and position the map's camera
     */
    private void getDeviceLocationnew(Double latttttt,Double longgg) {

        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = location;

                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1);
                                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                city = addresses.get(0).getLocality();
                                state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName(); //

                                editText.setText(city);
                                addressnew.setText(address);// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            final LatLng mDefaultLocation1 = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            LatLng latLng= new LatLng(latttttt,longgg);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(latttttt,
                                            longgg), DEFAULT_ZOOM));

                            mMap.addMarker(new MarkerOptions()
                                    .title(city)
                                    .position(latLng)
                                    .snippet(state));
                            editText.setText(label);
                            addressnew.setText(addressnewdata);
                            Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude());
                            Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude());
                           // lattt=String.valueOf(mLastKnownLocation.getLatitude());
                            //longnew=String.valueOf(mLastKnownLocation.getLongitude());
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(latttttt,
//                                            longgg), DEFAULT_ZOOM));
//
//            editText.setText(label);
//            addressnew.setText(addressnewdata);

//                            new Timer().schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    mMap.clear();
//
//
//                                    mMap.addMarker(new MarkerOptions()
//                                            .title(getString(R.string.app_name))
//                                            .position(latLng)
//                                            .snippet(getString(R.string.app_name)));
//                                    editText.setText(label);
//                                    addressnew.setText(addressnewdata);
//                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(latttttt,
//                                            longgg), DEFAULT_ZOOM));
//                                    // this code will be executed after 2 seconds
//                                }
//                            }, 3000);

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(latttttt,
                                            longgg), DEFAULT_ZOOM));                        }

                        //  getCurrentPlaceLikelihoods();
                    }
                });
            }


        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = location;

                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1);

                                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                city = addresses.get(0).getLocality();
                                state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName(); //

                                editText.setText(city);
                                addressnew.setText(address);
// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            final LatLng mDefaultLocation1 = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .title(city)
                                    .position(mDefaultLocation1)
                                    .snippet(state));

                            Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude());
                            Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude());
                            lattt=String.valueOf(mLastKnownLocation.getLatitude());
                            longnew=String.valueOf(mLastKnownLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        }

                      //  getCurrentPlaceLikelihoods();
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Fetch a list of likely places, and show the current place on the map - provided the user
     * has granted location permission.
     */
    private void pickCurrentPlacenew(Double latttttt,Double longg) {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {

            final Location location = new Location("yourprovidername");
            location.setLatitude(latttttt);
            location.setLongitude(longg);
            getDeviceLocationnew(latttttt,longg);


        } else {

            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            final Location location = new Location("yourprovidername");
            location.setLatitude(latttttt);
            location.setLongitude(longg);
            getDeviceLocationnew(latttttt,longg);




            // Add a default marker, because the user hasn't selected a place.
//            mMap.addMarker(new MarkerOptions()
//                    .title(getString(R.string.app_name))
//                    .position(latLng)
//                    .snippet(getString(R.string.app_name)));
//            editText.setText(label);
//            mLastKnownLocation=location;
//            addressnew.setText(addressnewdata);
            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            getDeviceLocation();
            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.app_name))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.app_name)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }
    public void logi1(String token,String type) {
        try {
            if(type.equals("add")){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("label",editText.getText().toString());
                map.put("address",addressnew.getText().toString());
                map.put("lat",lattt);
//            map.put("auuid",auuid);
                map.put("long",longnew);
                JSONObject json = new JSONObject(map);
                VolleyTasks.makeVolleyPost(this, Constent.address_insert, json, "map_add",token);

            }else if(type.equals("edit")){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("label",editText.getText().toString());
                map.put("address",addressnew.getText().toString());
                map.put("lat",lattt);
                 map.put("auuid",auuid);
                map.put("long",longnew);
                JSONObject json = new JSONObject(map);
                VolleyTasks.makeVolleyPostactiviy( this,Constent.address_update, json, "map_update",token);

            }

        }catch (Exception e){
            Toast.makeText(MapsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }
    /**
     * When user taps an item in the Places list, add a marker to the map with the place details
     */
    private AdapterView.OnItemClickListener listClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // position will give us the index of which place was selected in the array
            LatLng markerLatLng = mLikelyPlaceLatLngs[position];
            String markerSnippet = mLikelyPlaceAddresses[position];
            if (mLikelyPlaceAttributions[position] != null) {
                markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[position];
            }

            // Add a marker for the selected place, with an info window
            // showing information about that place.
            mMap.addMarker(new MarkerOptions()
                    .title(mLikelyPlaceNames[position])
                    .position(markerLatLng)
                    .snippet(markerSnippet));

            // Position the map's camera at the location of the marker.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng));
        }
    };

    @Override
    public void handleResult(String method_name, JSONObject response) {
        if (method_name.equals("map_add")) {
            try {
                String status = response.getString("message");
                Toast.makeText(MapsActivity.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                // 16 bytes IV

                if(code==200){
                    Intent a = new Intent(MapsActivity.this, Profileedit.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    MapsActivity.this.finish();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else         if (method_name.equals("map_update")) {
            try {
                String status = response.getString("message");
                Toast.makeText(MapsActivity.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                // 16 bytes IV

                if(code==200){
                    Intent a = new Intent(MapsActivity.this, Profileedit.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    MapsActivity.this.finish();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

    @Override
    public void handleError(VolleyError e) {

    }

    /**
     * Display a list allowing the user to select a place from a list of likely places.
     */
//    private void fillPlacesList() {
//        // Set up an ArrayAdapter to convert likely places into TextViews to populate the ListView
//        ArrayAdapter<String> placesAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_2, mLikelyPlaceNames);
//        lstPlaces.setAdapter(placesAdapter);
//        lstPlaces.setOnItemClickListener(listClickedHandler);
//    }

}