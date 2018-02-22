package com.homework.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private GameView gameView;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.grade);
        gameView = findViewById(R.id.game);
        test = findViewById(R.id.test);
        gameView.setOnNumChangeListener(new GameView.OnNumChangeListener() {
            @Override
            public void OnChange() {
                if (textView != null && gameView != null)
                    textView.setText("总分：   " + gameView.totalGrade);
                test.setText(Animation.getAnimation());
            }
        });

    }
}
