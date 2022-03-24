package com.regularsoul.catchthepepe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView scoreText;
    TextView timeText;
    int score;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    Handler handler;
    Runnable runnable;

    ImageView[] imageArray;
    private AdView mAdView;


    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        timeText = findViewById(R.id.TimeText);
        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);
        scoreText=findViewById(R.id.ScoreText);

        imageArray= new ImageView[]{ imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};
        hideImages();

        score = 0;

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time Off");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray){

                    image.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                    alert.setTitle("Restart?");
                    alert.setMessage("I'm sure you can do better!");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Restart
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    alert.show();
                }

            }
        }.start();

    }
    public void increaseScore(View view){
        mediaPlayer = MediaPlayer.create(this,R.raw.pepeses);


        mediaPlayer.start();
        score++;

        scoreText.setText("Score :" + score);

    }
    public void hideImages(){

        handler= new Handler();

        runnable=new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray){

                    image.setVisibility(View.INVISIBLE);
                }
                Random random= new Random();
                int i=random.nextInt(9);
                imageArray[i].setVisibility(imageView.VISIBLE);
                handler.postDelayed(this,700);

            }
        };

        handler.post(runnable);

    }
}