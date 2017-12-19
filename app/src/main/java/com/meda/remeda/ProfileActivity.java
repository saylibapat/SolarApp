package com.meda.remeda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsername,tvName,tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvName = (TextView) findViewById(R.id.tvname);
        tvEmail = (TextView) findViewById(R.id.tvemail);
        tvUsername = (TextView) findViewById(R.id.tvusername);

        tvName.setText(SharedPrefManager.getInstance(this).getUserName());
        tvEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        tvUsername.setText(SharedPrefManager.getInstance(this).getUserUsername());

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
