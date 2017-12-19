package com.meda.remeda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //Report r=new Report;
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        btn = (Button) findViewById(R.id.btn1);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                /*Intent i=getIntent();
                String str=i.getStringExtra("UserName");
                Report r=(Report)getApplication();
                r.setUsername(str);*/
                Intent intnt = new Intent(v.getContext(), Display.class);
                startActivity(intnt);

            }
        });
    }

    public void onPreviousClick(View v){
        if(v.getId() == R.id.Bprevious){
            startActivity(new Intent(this,PreviousRecord.class));
        }
    }

    public void onProfileClick(View v){
        if(v.getId() == R.id.Bprofile){
            startActivity(new Intent(this,ProfileActivity.class));
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
