package com.example.nahankamuapp;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.Button;
import android.view.View;
import android.media.MediaPlayer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

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

        Switch swichan = findViewById(R.id.swichan);
        TextView mode = findViewById(R.id.swichan);
        TextView answer = findViewById(R.id.answer);
        TextView button_mode = findViewById(R.id.btn);
        Button button = findViewById(R.id.btn);

        final long[] left = {33};
        final long[] right = {4};

        mediaPlayer = MediaPlayer.create(this, R.raw.sansanyon_se);

        // ボタンのクリックリスナー
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swichan.isChecked()) {
                    // 倍々モード
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();

                    left[0] *= 2;
                    right[0] *= 2;
                } else {
                    // 加算モード
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    left[0] += 33;
                    right[0] += 4;
                }
                answer.setText(left[0] + "-" + right[0]);
            }
        });

        // スイッチの切り替えリスナー
        swichan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mode.setText("倍々");
                    button_mode.setText("倍々");

                    left[0] = 33;
                    right[0] = 4;

                    answer.setText(left[0] + "-" + right[0]);
                } else {
                    mode.setText("加算");
                    button_mode.setText("加算");

                    left[0] = 33;
                    right[0] = 4;

                    answer.setText(left[0] + "-" + right[0]);
                }
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