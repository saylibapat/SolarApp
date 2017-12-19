package com.meda.remeda;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PreviousRecord extends AppCompatActivity {

    private EditText num;
    private Button get;
    private TextView Tresult;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_record);
        num = (EditText) findViewById(R.id.Enumber);
        get = (Button) findViewById(R.id.Bget);
        Tresult = (TextView) findViewById(R.id.tvSet);
        //num.setTextColor(Color.parseColor("#3F51B5"));
    }

    public void onGetPrevious(View v){
        if(v.getId() == R.id.Bget){
            getData();
        }
    }

    private void getData() {
        String number = num.getText().toString().trim();
        if (number.equals("")) {
            Toast.makeText(this, "Please enter a model number", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url =Constants.URL_GET + number;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PreviousRecord.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String remarks = "";
        String name = "";
        String number = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            JSONObject Data = result.getJSONObject(0);
            remarks = Data.getString(Constants.KEY_REMARKS);
            name = Data.getString(Constants.KEY_USERNAME);
            number = Data.getString(Constants.KEY_MODELNUMBER);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Tresult.setText("Model Number: \t" + number + "\nRemarks: \t" + remarks + "\nInspected by: \t" + name);
    }

}
