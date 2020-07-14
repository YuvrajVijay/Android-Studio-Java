package com.example.earthquakecheaker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.earthquakecheaker.MODEL.EarthQuake;
import com.example.earthquakecheaker.UI.CustomInfoWindow;
import com.example.earthquakecheaker.UTIL.constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.example.earthquakecheaker.UTIL.constants.LIMIT;
import static com.example.earthquakecheaker.UTIL.constants.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnInfoWindowClickListener ,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location loc;
    private RequestQueue queue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        queue= Volley.newRequestQueue(this);
        getEarthquake();



    }

    private void getEarthquake() {

        final EarthQuake earthQuake =new EarthQuake();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                constants.URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray features=response.getJSONArray("features");
                            for(int i=0;i< constants.LIMIT;i++){
                                JSONObject properties=features.getJSONObject(i).getJSONObject("properties");
                                JSONArray coordinate=features.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");

                                double lon=coordinate.getDouble(0);
                                double lat=coordinate.getDouble(1);
                                Log.d("blabla", "onResponse: "+lon+" , "+lat);
                                earthQuake.setPlace(properties.getString("place"));
                                earthQuake.setType(properties.getString("type"));
                                earthQuake.setTime(properties.getLong("time"));
                                earthQuake.setMagnitude(properties.getDouble("mag"));
                                earthQuake.setLat(lat);
                                earthQuake.setLon(lon);
                                earthQuake.setDetailLink(properties.getString("detail"));

                                java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                                String formattedDate=dateFormat.format(new Date(properties.getLong("time")).getTime());

                                MarkerOptions markerOptions=new MarkerOptions();
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                markerOptions.title(earthQuake.getPlace());
                                markerOptions.position(new LatLng(lat,lon));
                                markerOptions.snippet("Magnitube: "+earthQuake.getMagnitude()
                                +"\n"+"Date:" +formattedDate);


                                Marker marker=mMap.addMarker(markerOptions);
                                marker.setTag(earthQuake.getDetailLink());

                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),1));
                            }
                        } catch (JSONException e) {
                            Log.d("blabla", "onResponse: "+"nope");

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
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
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this) ;
        // Add a marker in Sydney and move the camera
//        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                loc=location;
//                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//                Log.d("locationcheck", "onLocationChanged: " + location.toString());
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
//        }else{
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            ==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        getQuakeDetails(marker.getTag().toString());
    }

    private void getQuakeDetails(String url) {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                url,null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String detailsUrl="";
                        try {
                            JSONObject properties =response.getJSONObject("properties");
                            JSONObject products=properties.getJSONObject("products");
                            JSONArray geoserve=products.getJSONArray("geoserve");
                            for(int i=0;i<geoserve.length();i++){
                                JSONObject geoserveObj=geoserve.getJSONObject(i);
                                JSONObject contentObj=geoserveObj.getJSONObject("contents");
                                JSONObject geoJsonObj=contentObj.getJSONObject("geoserve.json");
                                detailsUrl=geoJsonObj.getString("url");
                            }
                            Log.d("urlurl", "onResponse: "+detailsUrl);
                        } catch (JSONException e) {
                            Log.d("urlurl", "onResponse: "+detailsUrl);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}