package com.meda.remeda;

/**
 * Created by Toshiba on 9/22/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 9/21/2017.
 */

public class AboutDevelopers extends AppCompatActivity {
    private TextView tvone,tvtwo;
    //public ImageView mitwpu, mittbi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_dev);
        tvone=(TextView)findViewById(R.id.textView12);
        tvtwo=(TextView)findViewById(R.id.textView13);

       // mitwpu = (ImageView) findViewById(R.id.imageView7);
        //mittbi = (ImageView) findViewById(R.id.imageView8);

        //mitwpu.setClickable(true);
      //  mitwpu.setImageURI();
        //mitwpu.setMovementMethod(LinkMovementMethod.getInstance());

    }

}

