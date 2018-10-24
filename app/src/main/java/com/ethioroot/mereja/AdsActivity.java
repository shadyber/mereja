package com.ethioroot.mereja;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static android.widget.Toast.LENGTH_LONG;

public class AdsActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView,mAdView2;

    FloatingActionButton fab;
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
            case R.id.action_profile:
                Intent profile =new Intent(AdsActivity.this,ProfileActivity.class);
                startActivity(profile);
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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);


        MobileAds.initialize(this,
                getResources().getString(R.string.admob_app_id));


        mAdView = findViewById(R.id.adviewsmart);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);



        mAdView2 = findViewById(R.id.adviewsmart2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        mAdView2 = new AdView(this);
        mAdView2.setAdSize(AdSize.SMART_BANNER);






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
                mInterstitialAd.show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                RewardManager.AddPoint("point",1,getApplicationContext());

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                RewardManager.AddPoint("point",1,getApplicationContext());

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.

            }
        });


         final Handler handler=new Handler();
         Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                handler.postDelayed(this,5000);
            }
        };
        handler.postDelayed(runnable,5000);
//_______________________rewarding



//==++++++++++++++--------------- reward video

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();



    }
    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
        RewardManager.AddPoint("point",reward.getAmount(),getApplicationContext());
        Toast.makeText(getApplicationContext(),"Your Get "+reward.getAmount()+" "+reward.getType()+" ",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "Come Back Soon You Get Extra 1 Coint",
                Toast.LENGTH_SHORT).show();
        RewardManager.AddPoint("point",1,getApplicationContext());

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
        showReardVideo();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        RewardManager.AddPoint("point",1,getApplicationContext());

        Toast.makeText(this, "We Gonna Reward you when you Finish this Video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        RewardManager.AddPoint("point",1,getApplicationContext());

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
