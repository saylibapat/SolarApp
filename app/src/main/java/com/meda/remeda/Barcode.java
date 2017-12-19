package com.meda.remeda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Barcode extends AppCompatActivity {

    //public DatabaseHelper helper = new DatabaseHelper(this);

    public static EditText tvresult;
    private  Button btn;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        tvresult = (EditText) findViewById(R.id.tvresult);
        //tvresult.setTextColor(Color.parseColor("#3F51B5"));

        btn = (Button) findViewById(R.id.barcode);
        Report r=(Report)getApplication();
        if(r.getBarcode()!=null){
            tvresult.setText((r.getBarcode()));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barcode.this, ScanActivity.class);
                startActivity(intent);
                //Report r = (Report) getApplication();
                /*byte[] barray = r.getImage();
                ImageView tv = (ImageView) findViewById(R.id.imageView);
                Bitmap bitmap = BitmapFactory.decodeByteArray(barray,0,barray.length);
                tv.setImageBitmap(bitmap);*/
            }
        });
        button2 = (Button) findViewById(R.id.Next);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report r=(Report)getApplication();
                r.setBarcode(tvresult.getText().toString());
                //helper.insertInformation(r);
                Intent intent1 = new Intent(Barcode.this, Review.class);
                startActivity(intent1);

            }
        });
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
