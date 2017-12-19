/*package com.example.toshiba.activitydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Toshiba on 7/21/2017.
 */

/*public class SignUp extends Activity{
    public DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }
    public void onConfirmDetailsClick(View v){
        if(v.getId() == R.id.Bconfirm){
            EditText name = (EditText) findViewById(R.id.TFname);
            EditText email = (EditText) findViewById(R.id.TFemail);
            EditText uname = (EditText) findViewById(R.id.TFunamesignup);
            EditText pass1 = (EditText) findViewById(R.id.TFpass1signup);
            EditText pass2 = (EditText) findViewById(R.id.TFpass2signup);

            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String unamestr = uname.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            if(!pass1str.equals(pass2str)){
                Toast pass = Toast.makeText(SignUp.this,"Passwords don't match",Toast.LENGTH_SHORT);
                pass.show();
            }
            else{
                Contacts c= new Contacts();
                c.setName(namestr);
                c.setEmail(emailstr);
                c.setUsername(unamestr);
                c.setPassword(pass1str);

                helper.insertContact(c);

                Toast successful = Toast.makeText(SignUp.this,"Successfully Registered",Toast.LENGTH_LONG);
                successful.show();
            }





        }
    }
}*/
package com.meda.remeda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Toshiba on 7/21/2017.
 */

public class SignUp extends Activity{
    public DatabaseHelper helper = new DatabaseHelper(this);
    private EditText name,email,uname,pass1,pass2;
    private ProgressDialog progressDialog;
    //  InputValidation iv=new InputValidation(getBaseContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,HomeScreen.class));
            return;
        }

        name = (EditText) findViewById(R.id.TFname);
        email = (EditText) findViewById(R.id.TFemail);
        uname = (EditText) findViewById(R.id.TFunamesignup);
        pass1 = (EditText) findViewById(R.id.TFpass1signup);
        pass2 = (EditText) findViewById(R.id.TFpass2signup);
        name.setTextColor(Color.parseColor("#FFFFFF"));
        email.setTextColor(Color.parseColor("#FFFFFF"));
        uname.setTextColor(Color.parseColor("#FFFFFF"));
        pass1.setTextColor(Color.parseColor("#FFFFFF"));
        pass2.setTextColor(Color.parseColor("#FFFFFF"));
        progressDialog = new ProgressDialog(this);
    }

    public void onConfirmDetailsClick(View v) {
        if (v.getId() == R.id.Bconfirm) {
            //registerUser();
            /*EditText name = (EditText) findViewById(R.id.TFname);
            EditText email = (EditText) findViewById(R.id.TFemail);
            EditText uname = (EditText) findViewById(R.id.TFunamesignup);
            EditText pass1 = (EditText) findViewById(R.id.TFpass1signup);
            EditText pass2 = (EditText) findViewById(R.id.TFpass2signup);   */


            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String unamestr = uname.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            AwesomeValidation awesomeValidation;
            awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

            awesomeValidation.addValidation(this, R.id.TFname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
            awesomeValidation.addValidation(this, R.id.TFemail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
            //awesomeValidation.addValidation(this, R.id.TFpass1signup, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);

           /* if (!pass1str.equals(pass2str)) {
                Toast pass = Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT);
                pass.show();
            }*/
            /*if (!iv.isInputEditTextFilled(email,// textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!iv.isInputEditTextEmail(uname, /*textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!iv.isInputEditTextMatches(pass1, pass2, getString(R.string.error_message_email))) {
                return;
                */

            //  else {
            if (awesomeValidation.validate() && pass1str.equals(pass2str) == true) {
                /*if (helper.unamevalid(unamestr) == true) {
                    Contacts c = new Contacts();
                    c.setName(namestr);
                    c.setEmail(emailstr);
                    c.setUsername(unamestr);
                    c.setPassword(pass1str);*/

                    //helper.insertContact(c);
                    registerUser();

                    /* NOW: Toast successful = Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_LONG);
                    successful.show();*/
                    // EditText editText = (EditText) findViewById(R.id.TFusername);
                    //String message = ed1.getText().toString();
                    //intent.putExtra(EXTRA_MESSAGE, message);
               // }
                /*else
                {
                    Toast unsuccessful = Toast.makeText(SignUp.this, "Username already exists!!", Toast.LENGTH_LONG);
                }*/

            }else{
                Toast pass = Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT);
                pass.show();
            }
        }
    }
    public void onClickLogin(View v){
        if(v.getId() == R.id.Bloginnow){
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
        }
    }
    private void registerUser(){
        final String namestr = name.getText().toString().trim();
        final String emailstr = email.getText().toString().trim();
        final String unamestr = uname.getText().toString().trim();
        final String pass1str = pass1.getText().toString().trim();
        //String pass2str = pass2.getText().toString().trim();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        //Log.d("Response",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast successful = Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_LONG);
                        successful.show();
                        //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",namestr);
                params.put("email",emailstr);
                params.put("username",unamestr);
                params.put("password",pass1str);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
