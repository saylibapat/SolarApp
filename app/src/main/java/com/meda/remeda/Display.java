package com.meda.remeda;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Toshiba on 7/21/2017.
 */

public class Display extends AppCompatActivity {
    ImageView result_photo;
    public static final int requestCamera =1,resultGallery=0;
    private Bitmap bitmap;
    public EditText remarksText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        Button click1 = (Button)findViewById(R.id.Bcapture);
        remarksText = (EditText) findViewById(R.id.editRemarks);
       // remarksText.setTextColor(Color.parseColor("#3F51B5"));
       // Button click2 = (Button)findViewById(R.id.Bgallery);
        result_photo = (ImageView)findViewById(R.id.imageView);
        Report r=(Report)getApplication();
        if(r.getImage()!=null){
            Bitmap bmp = BitmapFactory.decodeByteArray(r.getImage(), 0, r.getImage().length);
            result_photo.setImageBitmap(bmp);
        }
        if(!hasCamera()){
            click1.setEnabled(false);
        }
    }

    /*@Override
    public void onBackPressed() {
        Intent i = new Intent(Display.this,HomeScreen.class);
        startActivity(i);
    }*/

    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void launchCamera(View v){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,requestCamera);
    }

    public void onGalleryClick(View v){
        if(v.getId() == R.id.Bgallery){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent,resultGallery);
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == requestCamera && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            result_photo.setImageBitmap(bitmap);
        }
        if(requestCode == resultGallery && resultCode == RESULT_OK && data!=null){
            Uri selectedImagePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImagePath);
                result_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

           // result_photo.setImageURI(selectedImagePath);
            /*try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImagePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                result_photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        }
    }
    private byte[] bitmapToByteArray(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }


    public  void onNext(View v)
    {
        if(v.getId()==R.id.Bto_info)
        {
            Report r = (Report) getApplication();
            r.setImage(bitmapToByteArray());
            r.setRemarks(remarksText.getText().toString());
            Intent i=new Intent(Display.this,Information.class);
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
               // finish();
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
