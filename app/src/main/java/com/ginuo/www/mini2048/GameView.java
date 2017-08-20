package com.ginuo.www.mini2048;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/8/20.
 */

//自定义View，用于存放所有方块的格子布局
public class GameView extends GridView {


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initGameView();
    }

    //主要用于设置对手势（滑动方向）的监听，并根据滑动方向产生动作
    private void initGameView(){
        setOnTouchListener(new OnTouchListener() {
            private double x, y, offset_X, offset_Y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //通过按下和提起的位置偏移确定滑动方向
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:   //起点
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offset_X = event.getX() - x;
                        offset_Y = event.getY() - y;
                        break;
                }
                //计算移动方向，不要对动作幅度太敏感！！！！！！
                if(Math.abs(offset_X) > Math.abs(offset_Y)){
                    if(offset_X < -5){
                        Log.d("GameView", "move to left");
                    }else if(offset_X > 5){
                        Log.d("GameView", "move to right");
                    }
                }else{
                    if(offset_Y < -5){
                        Log.d("GameView", "move to up");
                    }else if(offset_Y > 5){
                        Log.d("GameView", "move to down");
                    }
                }
                //根据Google文档：
                // return True if the listener has consumed the event, false otherwise.
                // onTouch若返回false，则其他相关监听器（如OnClickListener）能继续监听到同一动作
                //如果为false，则表示该动作已被onTouch“消耗”了，其他监听器无法继续监听到
                return true;
            }
        });
    }
}