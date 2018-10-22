package com.ethioroot.mereja;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class ReadActivity extends AppCompatActivity {



    private  String image="";
    private  String title="";
    private  String shortDisc="";
    private  String description="";
    private String movieid="";
    private String cast="";
    private  String gener="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);



        try {
            image=getIntent().getStringExtra("thumb_small");
            title=getIntent().getStringExtra("title");
            description=getIntent().getStringExtra("detail");

            gener=getIntent().getStringExtra("category");
        }catch(Exception ex){
            Log.e("Error on Intent : ",ex.getMessage());

        }
        TextView txttitle=findViewById(R.id.txttitle);
        txttitle.setText(title);

        TextView txtdetail=findViewById(R.id.txtdetail);

             txtdetail.setText(Html.fromHtml(description));

        ImageView imagepostertop=findViewById(R.id.imgposter);
        Picasso.with(getApplicationContext()).load(image).placeholder(R.drawable.placeholder)// Place holder image from drawable folder
                .error(R.drawable.placeholder).fit().centerCrop()
                .into(imagepostertop);

    }
}
