package com.estudiates.ingemath.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.estudiates.ingemath.R;
import com.estudiates.ingemath.utils.Juego;
import com.estudiates.ingemath.utils.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class CardsActivity extends YouTubeBaseActivity {

    private static final String TAG = "CardsActivity";

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener youTubeInitializedListener;
    Button button, botonJugar, botonSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        Log.d(TAG, "onCreating: Starting.");

        button = findViewById(R.id.button1);
        youTubePlayerView = findViewById(R.id.youtubeView1);
        inicializedYouTube();

        botonJugar = (Button) findViewById(R.id.botonJugar);
        botonSalir = (Button) findViewById(R.id.botonSalir);

        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar();
            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });


    }

    private void inicializedYouTube(){

        youTubeInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "OnClick: Done Intializing.");
                //youTubePlayer.loadPlaylist(getYoutubeId("https://www.youtube.com/watch?v=AoZpzAoC1Qg"));
                youTubePlayer.loadVideo(getYoutubeId("https://www.youtube.com/watch?v=AoZpzAoC1Qg"));

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "OnClick: faile Intializing.");

            }
        };

    }


    private void playVideo(){
        Log.d(TAG, "onClick: Intializing YouTube Player.");
        youTubePlayerView.initialize(YoutubeConfig.getApiKey(), youTubeInitializedListener);

    }

    public String getYoutubeId(String ytUrl)
    {
        String youtubeID ="";
        if (ytUrl.contains("://youtu.be/")){
            youtubeID=ytUrl.substring(ytUrl.lastIndexOf("/") + 1);
            ytUrl="http://youtube.com/watch?v=" + youtubeID;
        }else if (ytUrl.contains("watch?v=")){
            if (ytUrl.contains("&")){
                ytUrl=ytUrl.substring(0, ytUrl.indexOf('&'));
            }
            if (ytUrl.contains("https")){
                ytUrl.replace("https", "http");
            }
            youtubeID=ytUrl.substring(ytUrl.indexOf("watch?v=") + 8);
        }

        return youtubeID;
    }

    public void jugar(){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }
}
