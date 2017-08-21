package com.ginuo.www.mini2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener{
    private TextView txt_showScore;
    private Button btn_restart;
    private int score;
    private static MainActivity mainActivity = null;

    public MainActivity(){
        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        txt_showScore = (TextView)findViewById(R.id.showScore);
        show_score(0);
        btn_restart = (Button) findViewById(R.id.restart);
        btn_restart.setOnClickListener(this);
    }

    public void show_score(int _score){
        txt_showScore.setText(_score+"");
    }

    public void clearScore(){
        score = 0;
        show_score(score);
    }

    public void addScore(int increment){
        score += increment;
        show_score(score);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.restart:
                GameView.getGameView().startGame();
                clearScore();
        }
    }
}
