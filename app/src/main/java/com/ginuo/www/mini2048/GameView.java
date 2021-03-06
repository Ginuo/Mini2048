package com.ginuo.www.mini2048;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/8/20.
 */

//自定义View，用于存放所有方块的布局
public class GameView extends LinearLayout{
    private Card[][] cardsMap = new Card[4][4];      //二维数组，存放卡片
    private static GameView gameView;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameView = this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gameView = this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        gameView = this;
        initGameView();
    }

    public static GameView getGameView() {
        return gameView;
    }

    //往左滑动时
    public void swipeLeft(){
        Card old[][] = getOldCards();  //保存原来的卡片，用于比较是否有移动
        boolean hasMoved = false;   //只有存在移动了的卡片时（不一定要合并了）才能出现新的卡片
        //将各行非零的元素都挤到左边
        for(int row = 0; row < 4; row++){
            int tmp[] = new int[4];      //按顺序保存下当前行中的非零的元素
            int index = 0;
            for(int i = 0; i < 4; i++)
                if(cardsMap[row][i].getNum() != 0)
                    tmp[index++] = cardsMap[row][i].getNum();
            for(int i = 0; i < 4; i++){
                if(i < index){
                    cardsMap[row][i].setNum(tmp[i]);
                }else{
                    cardsMap[row][i].setNum(0);
                }
            }

        }
        //合并相等的非零元素
        for(int row = 0; row < 4; row++){
            for(int i = 0; i < 3; i++){
                if(cardsMap[row][i+1].equals(cardsMap[row][i])){
                    int x = cardsMap[row][i].getNum();
                    if(x != 0) {
                        hasMoved = true;
                        cardsMap[row][i].setNum(x * 2);
                        cardsMap[row][i + 1].setNum(0);
                        MainActivity.getMainActivity().addScore(cardsMap[row][i].getNum());
                    }
                }
            }
        }
        //将各行非零的元素都挤到左边
        for(int row = 0; row < 4; row++){
            int tmp[] = new int[4];
            int index = 0;
            for(int i = 0; i < 4; i++)
                if(cardsMap[row][i].getNum() != 0)
                    tmp[index++] = cardsMap[row][i].getNum();
            for(int i = 0; i < 4; i++){
                if(i < index){
                    cardsMap[row][i].setNum(tmp[i]);
                }
                else    cardsMap[row][i].setNum(0);
                if(! cardsMap[row][i].equals(old[row][i]))
                    hasMoved = true;
                Log.d("old", "old:"+old[row][i].getNum()+"  new:"+cardsMap[row][i].getNum());
            }
        }
        if(hasMoved){
            addRandomNum();
            checkCompleted();
        }
    }

    //往右滑动时
    public void swipeRight(){
        Card old[][] = getOldCards();
        boolean hasMoved = false;
        //将各行非零的元素都挤到右边
        for(int row = 0; row < 4; row++){
            int tmp[] = new int[4];
            int index = 3;
            for(int i = 3; i >= 0; i--)
                if(cardsMap[row][i].getNum() != 0)
                    tmp[index--] = cardsMap[row][i].getNum();
            for(int i = 3; i >= 0; i--) {
                if (i > index ){
                    cardsMap[row][i].setNum(tmp[i]);
                }else
                    cardsMap[row][i].setNum(0);
            }

        }
        //合并相等的非零元素
        for(int row = 0; row < 4; row++){
            for(int i = 3; i > 0; i--){
                if(cardsMap[row][i-1].equals(cardsMap[row][i])){
                    int x = cardsMap[row][i].getNum();
                    if(x != 0) {
                        hasMoved = true;      //******
                        cardsMap[row][i].setNum(x * 2);
                        cardsMap[row][i - 1].setNum(0);
                        MainActivity.getMainActivity().addScore(cardsMap[row][i].getNum());
                    }
                }
            }
        }
        //将各行非零的元素都挤到左边
        for(int row = 0; row < 4; row++){
            int tmp[] = new int[4];
            int index = 3;
            for(int i = 3; i >= 0; i--)
                if(cardsMap[row][i].getNum() != 0)
                    tmp[index--] = cardsMap[row][i].getNum();
            for(int i = 3; i >= 0; i--){
                if( i > index){
                    cardsMap[row][i].setNum(tmp[i]);
                }
                else    cardsMap[row][i].setNum(0);
                if(! cardsMap[row][i].equals(old[row][i]))
                    hasMoved = true;
            }

        }
        if(hasMoved){
            //仅在有方块被移动了才出现新的方块
            addRandomNum();
            checkCompleted();
        }

    }

    //上滑
    public void swipeUp(){
        Card old[][] = getOldCards();
        boolean hasMoved = false;   //****待处理，只有存在移动了的卡片时（不一定要合并了）才能出现新的卡片
        //将各行非零的元素都挤到左边
        for(int col = 0; col < 4; col++){
            int tmp[] = new int[4];      //按顺序保存下当前行中的非零的元素
            int index = 0;
            for(int i = 0; i < 4; i++)
                if(cardsMap[i][col].getNum() != 0)
                    tmp[index++] = cardsMap[i][col].getNum();
            for(int i = 0; i < 4; i++){
                if(i < index){
                    cardsMap[i][col].setNum(tmp[i]);
                }
                else{
                    cardsMap[i][col].setNum(0);
                }
            }

        }
        //合并相等的非零元素
        for(int col = 0; col < 4; col++){
            for(int i = 0; i < 3; i++){
                if(cardsMap[i+1][col].equals(cardsMap[i][col])){
                    int x = cardsMap[i][col].getNum();
                    if(x != 0) {
                        hasMoved= true;
                        cardsMap[i][col].setNum(x * 2);
                        cardsMap[i+1][col].setNum(0);
                        MainActivity.getMainActivity().addScore(cardsMap[i][col].getNum());
                    }
                }
            }
        }
        //将各行非零的元素都挤到左边
        for(int col = 0; col < 4; col++){
            int tmp[] = new int[4];
            int index = 0;
            for(int i = 0; i < 4; i++)
                if(cardsMap[i][col].getNum() != 0)
                    tmp[index++] = cardsMap[i][col].getNum();
            for(int i = 0; i < 4; i++){
                if(i < index){
                    cardsMap[i][col].setNum(tmp[i]);
                }
                else    cardsMap[i][col].setNum(0);
                if(!cardsMap[i][col].equals(old[i][col]))
                    hasMoved = true;
            }
        }
        if(hasMoved){
            addRandomNum();
            checkCompleted();
        }
    }

    //下滑
    public void swipeDown(){
        Card old[][] = getOldCards();
        boolean hasMoved = false;
        //将各行非零的元素都挤到下边
        for(int col = 0; col < 4; col++){
            int tmp[] = new int[4];
            int index = 3;
            for(int i = 3; i >= 0; i--)
                if(cardsMap[i][col].getNum() != 0)
                    tmp[index--] = cardsMap[i][col].getNum();
            for(int i = 3; i >= 0; i--) {
                if (i > index ){
                    cardsMap[i][col].setNum(tmp[i]);
                }else
                    cardsMap[i][col].setNum(0);
            }
        }

        //合并相等的非零元素
        for(int col = 0; col < 4; col++){
            for(int i = 3; i > 0; i--){
                if(cardsMap[i-1][col].equals(cardsMap[i][col])){
                    int x = cardsMap[i][col].getNum();
                    if(x != 0) {
                        hasMoved = true;      //一、有合并
                        cardsMap[i][col].setNum(x * 2);
                        cardsMap[i-1][col].setNum(0);
                        MainActivity.getMainActivity().addScore(cardsMap[i][col].getNum());
                    }
                }
            }
        }
        //重新将各行非零的元素都挤到下边
        for(int col = 0; col < 4; col++){
            int tmp[] = new int[4];
            int index = 3;
            for(int i = 3; i >= 0; i--)
                if(cardsMap[i][col].getNum() != 0)
                    tmp[index--] = cardsMap[i][col].getNum();
            for(int i = 3; i >= 0; i--){
                if( i > index){
                    cardsMap[i][col].setNum(tmp[i]);
                }
                else    cardsMap[i][col].setNum(0);
                if(!cardsMap[i][col].equals(old[i][col]))     //二、与原位置不同
                    hasMoved = true;
            }

        }

        if(hasMoved){
            addRandomNum();
            checkCompleted();
        }
    }

    //主要用于设置对手势（滑动方向）的监听，并根据滑动方向产生动作
    private void initGameView(){
        setOrientation(VERTICAL);
        setBackgroundColor(0xFFBBADA0);     //设置画布颜色（GameView）,ff前缀表示不透明

        setOnTouchListener(new View.OnTouchListener() {
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
                        //计算移动方向，不要对动作幅度太敏感！！！！！！
                        if(Math.abs(offset_X) > Math.abs(offset_Y)){
                            if(offset_X < -5){
                                swipeLeft();
                                Log.d("GameView", "move to left");
                            }else if(offset_X > 5){
                                swipeRight();
                                Log.d("GameView", "move to right");
                            }
                        }else{
                            if(offset_Y < -5){
                                swipeUp();
                                Log.d("GameView", "move to up");
                            }else if(offset_Y > 5){
                                swipeDown();
                                Log.d("GameView", "move to down");
                            }
                        }
                        break;
                }

                //根据Google文档：
                // return True if the listener has consumed the event, false otherwise.
                // onTouch若返回false，则其他相关监听器（如OnClickListener）能继续监听到同一动作
                //如果为false，则表示该动作已被onTouch“消耗”了，其他监听器无法继续监听到
                return true;
            }
        });
    }

    //动态计算卡片应有的大小
    //这个方法只会在启动时执行一次（因为清单文件中对屏幕做了配置，横屏时仍然不会改变）
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //求卡片宽度, -10是为了留出屏幕两侧的margin，÷4是因为总共有4×4的卡片
        int cardWidth = (Math.min(w,h) - 10) / 4;
        addCards(cardWidth);

        startGame();     //初始化游戏
    }

    //添加4×4的卡片到GameView中,height==width
    public void addCards(int cardWidth){

        Card c;

        LinearLayout line;
        LinearLayout.LayoutParams lineLp;
        //将16张卡片每行4张地排列到布局上
        for(int i = 0; i < 4; i++){
            line = new LinearLayout(getContext());
            lineLp = new LinearLayout.LayoutParams(-1, cardWidth);
            addView(line, lineLp);
            for(int j = 0; j < 4; j++){
                c = new Card(getContext());
                c.setNum(0);   //暂时将卡片上的数字全设为空
                //addView(c, cardWidth, cardWidth);
                LinearLayout.LayoutParams lp =
                        new LinearLayout.LayoutParams(cardWidth, cardWidth);
                line.addView(c, lp);
                cardsMap[i][j] = c;      //将卡片存放到二维数组
            }
        }
    }

    public Card[][] getOldCards(){
        Card old[][] = new Card[4][4];
        Card c;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                int num = cardsMap[i][j].getNum();
                c = new Card(getContext());
                c.setNum(num);
                old[i][j] = c;
            }
        }
        return old;
    }

    public void checkCompleted(){
        boolean finished = true;
        for(int i = 0; i < 4; i++){    //先遍历中间的2×2张卡片，并与其上下左右对比
            for(int j = 0; j < 4; j++){
                Card center = cardsMap[i][j];
                if(center.getNum() <= 0)    finished = false;
                else{
                    if(i-1 >= 0 && (cardsMap[i-1][j].getNum() == 0 || cardsMap[i][j].equals(cardsMap[i-1][j])))
                        finished = false;
                    if(i+1 < 4 && (cardsMap[i+1][j].getNum() == 0 || cardsMap[i][j].equals(cardsMap[i+1][j])))
                        finished = false;
                    if(j-1 >= 0 && (cardsMap[i][j-1].getNum() == 0 || cardsMap[i][j].equals(cardsMap[i][j-1])))
                        finished = false;
                    if(j+1 < 4 && (cardsMap[i][j+1].getNum() == 0 || cardsMap[i][j].equals(cardsMap[i][j+1])))
                        finished = false;
                }
            }
        }
        if(finished){
            Log.d("GameView","Game Over");
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.getMainActivity());
            dialog.setTitle("游戏结束");
            dialog.setMessage("是否重新开始？");
            dialog.setCancelable(false);
            dialog.setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                    MainActivity.getMainActivity().clearScore();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }

    public void startGame(){

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                cardsMap[i][j].setNum(0);     //重置
            }
        }
        addRandomNum();              //给两个位置添加随机值
        addRandomNum();

    }

    public void addRandomNum(){
        List<Point> coordinate = new ArrayList<Point>();  //记录下二维数组中所有的空白位置的坐标
                                                    //然后从中随机选取一个坐标，并为对应的卡片赋值为2或4
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(cardsMap[i][j].getNum() <= 0)
                    coordinate.add(new Point(i,j));
            }
        }
        if(!coordinate.isEmpty()){
            Point p = coordinate.remove((int)(Math.random() * coordinate.size()));
            cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);     //random()生成
        }
    }


}