package com.ethioroot.mereja;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class ReadActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    FloatingActionButton fab;

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


        MobileAds.initialize(this,
                "ca-app-pub-3780418992794226~3021814007");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3780418992794226/3129625210");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

fab=findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
});
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


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
