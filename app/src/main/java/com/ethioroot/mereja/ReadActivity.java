package com.ethioroot.mereja;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import static android.widget.Toast.LENGTH_LONG;

public class ReadActivity extends AppCompatActivity  implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        // ...
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_like:

                return true;
            case R.id.action_reward:
                showReardVideo();
                return true;
            case  R.id.action_share:

                return true;
            default:
                showReardVideo();
                return super.onOptionsItemSelected(item);
        }

    }


    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getResources().getString(R.string.reward_1),
                new AdRequest.Builder().build());
    }



    private AdView mAdView,mAdView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        MobileAds.initialize(this,
                getResources().getString(R.string.admob_app_id));



        mAdView = findViewById(R.id.adView);
        mAdView2=findViewById(R.id.adView2);



        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdRequest adRequest1=new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest1);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interatial_1));
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

//_______________________rewarding



//==++++++++++++++--------------- reward video

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();



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


    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "Come Back ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "Reward Video Fail to Load", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this,"Video Ready Tab on the Diamond icon on top",LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "We Gonna Reward you when you Finish this Video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoCompleted() {
        loadRewardedVideoAd();
    }


    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    public void showReardVideo(){

        mRewardedVideoAd.show();
    }

}
