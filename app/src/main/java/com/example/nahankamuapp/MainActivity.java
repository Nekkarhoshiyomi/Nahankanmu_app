package com.example.nahankamuapp;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.media.MediaPlayer;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ShakeListener之初期化.
        shakeListener = new ShakeListener(this);
        shakeListener.register();

        //TextViewの取得
        TextView answer = findViewById(R.id.answer);
        Button button = findViewById(R.id.btn);
        TextView count = findViewById(R.id.count);
        Button resetButton = findViewById(R.id.resetButton);

        //数字の初期化
        final long[] left = {0};
        final long[] right = {0};
        final int[] frequency = {0};

        //結果の初期化
        answer.setText("0-0");
        count.setText("0回");

        //seの初期化
        mediaPlayer = MediaPlayer.create(this, R.raw.sansanyon_se);

        // ボタンのクリックリスナー
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // 加算モード
                mediaPlayer.seekTo(0);
                mediaPlayer.start();

                left[0] += 33;
                right[0] += 4;

                frequency[0] += 1;

                answer.setText(left[0] + "-" + right[0]);
                count.setText(frequency[0] + "回");
            }
        });

        shakeListener.setOnShakeListener(() -> {
            String textCopy = left[0] + "-" + right[0];

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData clip = ClipData.newPlainText("label", textCopy);
            clipboard.setPrimaryClip(clip);
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                left[0] = 0;
                right[0] = 0;

                frequency[0] = 0;

                answer.setText("0-0");
                count.setText("0回");
            }
        });

    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}