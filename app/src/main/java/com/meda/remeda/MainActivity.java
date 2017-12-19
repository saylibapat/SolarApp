package com.meda.remeda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    Button b1, b2;
    EditText ed1, ed2;
    DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
    ProgressDialog progressDialog;
    TextView tx1;
    //int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,HomeScreen.class));
            return;
        }

        b1 = (Button) findViewById(R.id.Blogin);
        ed1 = (EditText) findViewById(R.id.TFusername);
        ed2 = (EditText) findViewById(R.id.TFpassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        b2 = (Button) findViewById(R.id.Bregister);
        //tx1 = (TextView) findViewById(R.id.attempts);
        //tx1.setVisibility(View.GONE);
        ed1.setTextColor(Color.parseColor("#FFFFFF"));
        ed2.setTextColor(Color.parseColor("#FFFFFF"));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if (ed1.getText().toString().equals("a") && ed2.getText().toString().equals("1"))
               /*  if(databaseHelper.checkUser(ed1.getText().toString().trim()
                        , ed2.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...", Toast.LENGTH_SHORT).show();

                    //Intent intent = new Intent(Barcode.class);
                    Intent intent = new Intent(v.getContext(), HomeScreen.class);
                    // EditText editText = (EditText) findViewById(R.id.TFusername);
                    String message = ed1.getText().toString();
                    intent.putExtra("UserName", message);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    //tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));
                    tx1.setTextColor(Color.RED);

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }*/
                userLogin();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();
                Intent i1=new Intent(v.getContext(),SignUp.class);
                startActivity(i1);
            }
        });
    }
    private void userLogin(){
        final String username = ed1.getText().toString().trim();
        final String password = ed2.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response1);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(obj.getString("name"),obj.getString("email"),obj.getString("username"));
                                //Toast.makeText(getApplicationContext(),"User login successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}

