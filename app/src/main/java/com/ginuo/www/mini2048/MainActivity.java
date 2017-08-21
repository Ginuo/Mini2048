package com.ginuo.www.mini2048;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView showScore;
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

        showScore = (TextView)findViewById(R.id.showScore);
        show_score(0);
    }

    public void show_score(int _score){
        showScore.setText(_score+"");
    }


    public void addScore(int increment){
        score += increment;
        show_score(score);
    }
}
