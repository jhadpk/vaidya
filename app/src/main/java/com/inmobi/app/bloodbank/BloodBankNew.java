package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inmobi.app.bloodbank.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepak.jha on 11/08/15.
 */
public class BloodBankNew  extends Activity implements GoogleMap.OnMyLocationChangeListener {
    GoogleMap googlemap;
    Button search;
    Spinner bloodgroup;
    public JSONArray bloodbank;
    protected Context context;
    String bloodgp, phone, name, lat, lon, place;
    String provider;
    Criteria criteria;
    Location myLocation;
    LatLng ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gmap);
        googlemap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googlemap.setMyLocationEnabled(true);
        googlemap.getUiSettings().setZoomGesturesEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        myLocation = locationManager.getLastKnownLocation(provider);
        googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googlemap.animateCamera(CameraUpdateFactory.zoomTo(14));

        googlemap.addMarker(new MarkerOptions().position(latLng).title("Last location!").snippet("Home Address"));



        search = (Button) findViewById(R.id.bloodNeed);
        bloodgroup = (Spinner) findViewById(R.id.bloodsearch);


        String strblood="[{\"bloodgp\":\"A+\",\"name\":\"Krishna\",\"phone\":\"9876543210\",\"lat\":\"12.926054\",\"lon\":\"77.680721\",\"place\":\"Near RMZ Ecospace\"},{\"bloodgp\":\"B+\",\"name\":\"Krishna\",\"phone\":\"9876543210\",\"lat\":\"12.956562\",\"lon\":\"77.700599\",\"place\":\"Near Marathahalli\"},{\"bloodgp\":\"O+\",\"name\":\"Krishna\",\"phone\":\"9876543210\",\"lat\":\"12.934550\",\"lon\":\"77.677070\",\"place\":\"Near Bellandur Lake\"},{\"bloodgp\":\"AB+\",\"name\":\"Krishna\",\"phone\":\"9876543210\",\"lat\":\"12.955256\",\"lon\":\"77.681537\",\"place\":\"Near HAL\"},{\"bloodgp\":\"AB-\",\"name\":\"Krishna\",\"phone\":\"9876543210\",\"lat\":\"12.939388\",\"lon\":\"77.703726\",\"place\":\"Near Coconut plantation\"},{\"bloodgp\":\"O-\",\"name\":\"Krishna\",\"phone\":\"9876543210\",\"lat\":\"12.912827\",\"lon\":\"77.652995\",\"place\":\"Near NIFT\"}]";
        try{
            bloodbank = new JSONArray(strblood);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject person = new JSONObject();
                for(int i=0; i<bloodbank.length();i++) {

                    try {
                        person = bloodbank.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        bloodgp = person.getString("bloodgp");
                        Log.d("The blood group is "+bloodgp,"Message");
                        System.out.println("The blood group is "+bloodgp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("The bloodgroup is "+bloodgroup,"Message1");
                    System.out.println("The bloodgroup is "+bloodgroup.getSelectedItem().toString());
                    if (bloodgp.equals(bloodgroup.getSelectedItem().toString())) {
                        try {
                            phone = person.getString("phone");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            name = person.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            lat = person.getString("lat");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            lon = person.getString("lon");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            place = person.getString("place");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("The lat long  is "+lat+ " " +lon,"Messageinside");
                        ll = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
                        googlemap.addMarker(new MarkerOptions().position(ll).title(place));
                    }
                }

                /*final LatLng ll = new LatLng(lat,lon);
                googlemap.addMarker(new MarkerOptions().position(ll).title("Point"));
                Log.d("The Lat long is "+ll.latitude + ll.longitude,"");*/
            }
        });



    }



    @Override
    public void onResume() {
        super.onResume();
        googlemap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        googlemap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googlemap.animateCamera(CameraUpdateFactory.zoomTo(14));
        googlemap.addMarker(new MarkerOptions().position(latLng)
                .title("last location").snippet("Location on resume"));

    }


    @Override
    public void onMyLocationChange(Location location) {
        System.out.println("Location is changed to office");
    }
}

