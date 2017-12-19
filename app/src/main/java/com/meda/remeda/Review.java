package com.meda.remeda;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anushree on 15/8/17.
 */

public class Review extends AppCompatActivity {
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    private ImageView iv;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_review);
        String name,manufacturer,date,location,username,number;
        String barcode;
        progressDialog = new ProgressDialog(this);
        tv1 = (TextView) findViewById(R.id.tvUser);
        tv2 = (TextView) findViewById(R.id.tvModelNo);
        tv3 = (TextView) findViewById(R.id.tvModelName);
        tv4 = (TextView) findViewById(R.id.tvManu);
        tv5 = (TextView) findViewById(R.id.tvBarcode);
        tv6 = (TextView) findViewById(R.id.tvDate);
        //TextView tv7 = (TextView) findViewById(R.id.rLocation);
        tv7 = (TextView) findViewById(R.id.tvAddress);
        Report r = (Report) getApplication();
        //str = r.getUsername();
        tv1.setText(SharedPrefManager.getInstance(this).getUserUsername());;
        number = r.getModelNo();
        //str = "" + number;
        tv2.setText(number);
        name = r.getModelName();
        tv3.setText(name);
        manufacturer = r.getManufacturer();
        tv4.setText(manufacturer);
        barcode = r.getBarcode();
        tv5.setText(barcode);
        date = r.getDate();
        tv6.setText(date);
        location=r.getLocation();
        /*String loc=new String();
        int i=0;

        while(i<location.length()){
            if(i%20==0){
                loc=loc+"\n";
            }else{
                loc=loc+location.charAt(i);
            }
            i++;
        }*/
        tv7.setText(location);
        iv = (ImageView) findViewById(R.id.iv);
        Bitmap bmp = BitmapFactory.decodeByteArray(r.getImage(), 0, r.getImage().length);
        iv.setImageBitmap(bmp);
    }

    private String imageToString(){
        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();*/
        Report r = (Report) getApplication();
        byte[] imageBytes = r.getImage();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;

    }
    private void createDataEntry(){
        Report r = (Report) getApplication();
        final String imagestr = imageToString();
        final String namestr = tv1.getText().toString().trim();
        final String modelnostr = tv2.getText().toString().trim();
        final String modelnamestr = tv3.getText().toString().trim();
        final String manustr = tv4.getText().toString().trim();
        final String barcodestr = tv5.getText().toString().trim();
        final String datestr = tv6.getText().toString().trim();
        final String addressstr = tv7.getText().toString().trim();
        final String remarksstr = r.getRemarks();
        final String latitudestr = "" + r.getLatitude();
        final String longitudestr = "" + r.getLongitude();
        //final String imageData = r.getImage();

        progressDialog.setMessage("Feeding Data...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_FORM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ModelName",modelnamestr);
                params.put("ModelNumber",modelnostr);
                params.put("Manufacturer",manustr);
                params.put("InstallationDate",datestr);
                params.put("Barcode",barcodestr);
                params.put("Location",addressstr);
                params.put("Latitude",latitudestr);
                params.put("Longitude",longitudestr);
                params.put("Username",namestr);
                params.put("Remarks",remarksstr);
                params.put("Image",imagestr);
                //params.put("Image",imageData);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    /*private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;

    }*/

    public void onSubmitClick(View v){
        if(v.getId() == R.id.Bsubmit){
            createDataEntry();
            //Toast.makeText(getApplicationContext(),"Submitted successfully",Toast.LENGTH_SHORT).show();
        }
    }
    public void onHomeScreenClick(View v){
        if(v.getId() == R.id.Bhome){
            Intent i=new Intent(Review.this,HomeScreen.class);
            startActivity(i);
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
