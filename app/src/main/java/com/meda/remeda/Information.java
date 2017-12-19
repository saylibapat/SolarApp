package com.meda.remeda;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Toshiba on 7/22/2017.
 */

public class Information extends AppCompatActivity {

    Button btnDatePicker,btnShowLocation;
    EditText txtDate,IName,INumber,IManu;
    TextView latlong;
    EditText addr;
    private int mYear, mMonth, mDay;
    String date,result;
    Double latitude,longitude;
    Date formatdate;
    GPSTracker gps;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_info);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        txtDate=(EditText)findViewById(R.id.in_date);
        IName=(EditText)findViewById(R.id.FName);
        INumber=(EditText)findViewById(R.id.FNumber);
        IManu=(EditText)findViewById(R.id.FManu);
        latlong=(TextView)findViewById(R.id.Latlong);
        addr=(EditText)findViewById(R.id.Address);
        /*txtDate.setTextColor(Color.parseColor("#3F51B5"));
        IName.setTextColor(Color.parseColor("#3F51B5"));
        INumber.setTextColor(Color.parseColor("#3F51B5"));
        IManu.setTextColor(Color.parseColor("#3F51B5"));
        addr.setTextColor(Color.parseColor("#3F51B5"));*/


        //Log.v("abc","def");

        Report r=(Report)getApplication();
        if(r.getDate()!=null){
            txtDate.setText(r.getDate());
        }
        if(r.getModelName()!=null){
            IName.setText(r.getModelName());
        }
        if(r.getManufacturer()!=null){
            IManu.setText(r.getManufacturer());
        }
        if(r.getModelNo()!=null){
            INumber.setText(r.getModelNo());
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will,execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnShowLocation = (Button) findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(Information.this);
                result=new String();

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Report r=(Report)getApplication();
                    r.setLatitude(latitude);
                    r.setLongitude(longitude);

                    Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                    try{
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addressList != null && addressList.size() > 0) {
                            Address address = addressList.get(0);
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                                sb.append(address.getAddressLine(i)).append(","); //.append("\n");
                            }
                            //sb.append("\n").append(address.getLocality()).append("\n");
                            //sb.append(address.getPostalCode()).append("\n");
                            sb.append(address.getCountryName());
                            result = sb.toString();
                            //TimeUnit.SECONDS.sleep(1);
                        }
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),"Problem locating you",Toast.LENGTH_SHORT).show();
                    }
                    finally {
                        if(latitude!=null && longitude!=null){
                            latlong.setText("Latitude: "+ Double.toString(latitude)+"\nLongitude: "+Double.toString(longitude));
                        }
                        if(result!=null){
                            addr.setText(result);
                        }
                        // \n is for new line
                    /*Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude +"\nAddress: "+result, Toast.LENGTH_LONG).show();*/
                    }
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                //try{
                                date=txtDate.getText().toString();
                                //DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
                                //formatdate=(Date)formatter.parse(date);
                                //}
                                //catch(ParseException p){
                                //    Toast unsuccessful = Toast.makeText(Information.this,"Invalid Date Format",Toast.LENGTH_SHORT);
                                //   unsuccessful.show();
                                //}

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }


        });
    }
    public void onSubmitClick(View v){
        if(v.getId()== R.id.bsubmit)
        {
            Report r=(Report)getApplication();
            r.setDate(date);
            r.setModelName(IName.getText().toString());
            r.setManufacturer(IManu.getText().toString());
            r.setModelNo(INumber.getText().toString());
            r.setLatitude(latitude);
            r.setLongitude(longitude);
            r.setLocation(addr.getText().toString());
            //Toast submitted=Toast.makeText(Information.this,"Successfully submitted",Toast.LENGTH_LONG);
            //submitted.show();
            Intent barcode=new Intent(Information.this,Barcode.class);
            startActivity(barcode);

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this,"Directing to Settings...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuAbout:
                Toast.makeText(this,"Meet the developers",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,AboutDevelopers.class));
                break;
        }
        return true;
    }
}

/*
 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this,"Directing to Settings...",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
 */