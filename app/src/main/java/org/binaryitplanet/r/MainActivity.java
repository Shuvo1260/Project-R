package org.binaryitplanet.r;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.skyfishjy.library.RippleBackground;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private RippleBackground rippleBackground;
    private RippleBackground buttonRippleBackground;
    private Button play;
    private MediaPlayer mediaPlayer;
    private ImageView done;

    private int[] audioList = {
            R.raw.one,
            R.raw.two,
            R.raw.three,
            R.raw.four,
            R.raw.five,
            R.raw.six
    };

    private int max = 6;
    private int min = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding views
        rippleBackground = findViewById(R.id.rippleEffect);
        buttonRippleBackground = findViewById(R.id.buttRippleEffect);
        play = findViewById(R.id.play);
        done = findViewById(R.id.done);

        // Setting play button on click listener
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starting animation
                play.setEnabled(false);
                rippleBackground.startRippleAnimation();
                buttonRippleBackground.startRippleAnimation();

                Random rand = new Random();
                int index = rand.nextInt(6);
                Log.d(TAG, "onClick: " + index);
                // Preaparing mediaplayer
                mediaPlayer = MediaPlayer.create(MainActivity.this, audioList[index]);

                // Starting mediaplayer
                mediaPlayer.start();

                // On audio Complete listener
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d(TAG, "OnComplete");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        rippleBackground.stopRippleAnimation();
                        buttonRippleBackground.stopRippleAnimation();
                        play.setEnabled(true);
                        mediaPlayer.release();
                        SystemClock.sleep(100);
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ding);
                        mediaPlayer.start();
                        done.setVisibility(View.VISIBLE);

                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                done.setVisibility(View.GONE);
                            }
                        }, 1500);

                    }
                });
                Log.d(TAG, "onClick: ");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            rippleBackground.stopRippleAnimation();
            buttonRippleBackground.stopRippleAnimation();
            play.setEnabled(true);
            mediaPlayer.release();
        }
    }
}