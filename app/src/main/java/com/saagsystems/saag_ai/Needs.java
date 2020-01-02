package com.saagsystems.saag_ai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/*public class Needs extends AppCompatActivity {
    Button submit;
    EditText etName, etContact, etAdd, etDob, etEmer;
    CheckBox vImpaired;
    String name, address, phone, data, birth, vImp = "no", emer;
    public final static String NAME = "name";
    public final static String ADD = "add";
    public final static String PHno = "phno";
    public static final String DATA = "data";
    public static final String BIRTH = "birth";
    public static final String VIMP = "vimp";
    public static final String EMER = "emer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    setContentView(R.layout.activity_needs);


        //Grabbing References
        submit = (Button) findViewById(R.id.buttonSubmit);
        etName = (EditText) findViewById(R.id.editTextName);
        etAdd = (EditText) findViewById(R.id.editTextAdd);
        etContact = (EditText) findViewById(R.id.editTextContact);
        etDob = (EditText) findViewById(R.id.editTextdob);
        vImpaired = (CheckBox) findViewById(R.id.checkBoxYes);
        etEmer = (EditText) findViewById(R.id.et_emergency);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Initializing variables
                name = etName.getText().toString();
                address = etAdd.getText().toString();
                phone = etContact.getText().toString();
                birth = etDob.getText().toString();
                if (vImpaired.isChecked()) {
                    vImp = "yes";
                }
                emer = etEmer.getText().toString();
                //Saves to database
                try {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(NAME,name);
                    editor.putString(ADD,address);
                    editor.putString(PHno,phone);
                    editor.putString(BIRTH,birth);
                    editor.putString(VIMP,vImp);
                    editor.putString(EMER,emer);
                    editor.commit();
                    /*
                    AayaDatabase enterDatabase = new AayaDatabase(Needs.this);
                    enterDatabase.open();
                    enterDatabase.createEntry(name, address, phone, birth, vImp);
                    data = enterDatabase.getData();
                    enterDatabase.close();
                    */
             /*   } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    //Toast.makeText(Needs.this, data, Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(Needs.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    }
}
*/
//  package com.saagsystems.saag_track;
import android.accessibilityservice.GestureDescription;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
//import android.support.v7.app.AppCompatActivity;

public class Needs extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<LatLng> listLatLng;
    private RelativeLayout rlMapLayout;
    HashMap<Marker, LatLngBean> hashMapMarker = new HashMap<Marker, LatLngBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        rlMapLayout = (RelativeLayout) findViewById(R.id.rlMapLayout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gooogleMap) {
        mMap = gooogleMap;
        setUpMapIfNeeded();
        setData();
    }

    private void setData() {
        ArrayList<LatLngBean> arrayList = new ArrayList<LatLngBean>();
        LatLngBean bean = new LatLngBean();
        bean.setTitle("Ahmedabad");
        //  bean.setSnippet("Hello,Ahmedabad");
        bean.setLatitude("23.0300");
        bean.setLongitude("72.5800");
        arrayList.add(bean);
        LatLngBean bean1 = new LatLngBean();
        bean1.setTitle("Surat");
        //  bean1.setSnippet("Hello,Surat");
        bean1.setLatitude("21.1700");
        bean1.setLongitude("72.8300");
        arrayList.add(bean1);
        LatLngBean bean2 = new LatLngBean();
        bean2.setTitle("Vadodara");
        //   bean2.setSnippet("Hello,Vadodara");
        bean2.setLatitude("22.3000");
        bean2.setLongitude("73.2000");
        arrayList.add(bean2);
        LoadingGoogleMap(arrayList);
    }

    /**
     * @author Hasmukh Bhadani
     * Set googleMap if require
     */
    private void setUpMapIfNeeded() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        // Google Play Services are not available
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            if (mMap == null) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                //  googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }
            }
        }
    }

    /**
     * @author Hasmukh Bhadani
     * Loading Data to the GoogleMap
     */
    // -------------------------Google Map
    void LoadingGoogleMap(ArrayList<LatLngBean> arrayList) {
        if (mMap != null) {
            mMap.clear();
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            if (arrayList.size() > 0) {
                try {
                    listLatLng = new ArrayList<LatLng>();
                    for (int i = 0; i < arrayList.size(); i++) {
                        LatLngBean bean = arrayList.get(i);
                        if (bean.getLatitude().length() > 0 && bean.getLongitude().length() > 0) {
                            double lat = Double.parseDouble(bean.getLatitude());
                            double lon = Double.parseDouble(bean.getLongitude());
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lon))
                                    .title(bean.getTitle())
                                    //  .snippet(bean.getSnippet())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            //Add Marker to Hashmap
                            hashMapMarker.put(marker, bean);
                            //Set Zoom Level of Map pin
                            LatLng object = new LatLng(lat, lon);
                            listLatLng.add(object);
                        }
                    }
                    SetZoomlevel(listLatLng);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker position) {
                        LatLngBean bean = hashMapMarker.get(position);
                        Toast.makeText(getApplicationContext(), bean.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @author Hasmukh Bhadani
     * Set Zoom level all pin withing screen on GoogleMap
     */
    public void SetZoomlevel(ArrayList<LatLng> listLatLng) {
        if (listLatLng != null && listLatLng.size() == 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listLatLng.get(0), 10));
        } else if (listLatLng != null && listLatLng.size() > 1) {
            final Builder builder = LatLngBounds.builder();
            for (int i = 0; i < listLatLng.size(); i++) {
                builder.include(listLatLng.get(i));
            }
            final ViewTreeObserver treeObserver = rlMapLayout.getViewTreeObserver();
            treeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    if (mMap != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), findViewById(R.id.map)
                                .getWidth(), findViewById(R.id.map).getHeight(), 80));
                        rlMapLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }
}