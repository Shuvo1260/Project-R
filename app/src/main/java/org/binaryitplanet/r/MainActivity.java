package org.binaryitplanet.r;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.skyfishjy.library.RippleBackground;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private RippleBackground rippleBackground;
    private ImageButton play;
    private MediaPlayer mediaPlayer;

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
        play = findViewById(R.id.play);

        // Setting play button on click listener
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starting animation
                play.setVisibility(View.INVISIBLE);
                rippleBackground.startRippleAnimation();

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
                        play.setVisibility(View.VISIBLE);
                        mediaPlayer.release();
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
            mediaPlayer.release();
        }
    }
}