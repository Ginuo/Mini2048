package com.ginuo.www.mini2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/20.
 */

public class Card extends FrameLayout {
    private int num;     //要在卡片上显示的数字
    private TextView label;     //用于显示数字

    public void initCard(){
        //setBackgroundColor(0xffe2e4da);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0xFFCDC0B4);       //33前缀表示半透明
        label.setGravity(Gravity.CENTER);
        //注意：LayoutParams在FrameLayout和LinearLayout中都存在，如果import了这两个类，要注意区分！！！
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1,-1);  //-1-1表示填充满整个父容器
        lp.setMargins(10,10,0,0);      //通过设置TextView的margin设置卡片之间的间隔
        addView(this.label, lp);      //让label填充满Card
        setNum(0);     //必须向创建label对象，才能setNum
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCard();
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCard();
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initCard();
    }

    public Card(Context context) {
        super(context);
        initCard();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num <= 0){
            label.setText("");
        }else{
            label.setText(num+"");      //显示数字
        }

    }

    //重写equals方法，用于判断两张卡片上的数字是否相等
    public boolean equals(Card obj) {
        return getNum() == obj.getNum();
    }








}
