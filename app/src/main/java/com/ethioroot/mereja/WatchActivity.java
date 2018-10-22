package com.ethioroot.mereja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class WatchActivity   extends YouTubeBaseActivity {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String videoId="";
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
        setContentView(R.layout.activity_watch);



        try {
            videoId = getIntent().getStringExtra("vidId");
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
        txtdetail.setText(description);
        ImageView imagepostertop=findViewById(R.id.imgposter);
        Picasso.with(getApplicationContext()).load(image).placeholder(R.drawable.placeholder)// Place holder image from drawable folder
                .error(R.drawable.placeholder).fit().centerCrop()
                .into(imagepostertop);


        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.youtube_view);

        youTubePlayerView.initialize(Config.YOUTUBE_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(videoId);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });



    }
}
