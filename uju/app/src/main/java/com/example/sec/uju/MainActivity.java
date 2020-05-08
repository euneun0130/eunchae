package com.example.sec.uju;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    Bitmap spaceship;
    int spaceship_x, spaceship_y;
    Bitmap leftkey, rightkey;
    int leftkey_x, leftkey_y;
    int rightkey_x, rightkey_y;
    int Width, Height;
    int button_width;
    Bitmap screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        Width = display.getWidth();
        Height = display.getHeight();
        spaceship = BitmapFactory.decodeResource(getResources(),R.drawable.spaceship);
        int x = Width/8;
        int y = Height/11;
        spaceship = Bitmap.createScaledBitmap(spaceship,x,y,true);

        spaceship_x = Width*1/9;
        spaceship_y=Height*6/9;

        leftkey = BitmapFactory.decodeResource(getResources(),R.drawable.leftkey);
        leftkey_x = Width*5/9;
        leftkey_y = Height*7/9;
        button_width=Width/6;

        leftkey = Bitmap.createScaledBitmap(leftkey,button_width,button_width,true);

        rightkey = BitmapFactory.decodeResource(getResources(),R.drawable.rightkey);
        rightkey_x = Width*7/9;
        rightkey_y = Height*7/9;

        rightkey = Bitmap.createScaledBitmap(rightkey,button_width,button_width,true);

        screen = BitmapFactory.decodeResource(getResources(),R.drawable.screen);
        screen = Bitmap.createScaledBitmap(screen,Width, Height, true);
    }

    class MyView extends View {
        MyView(Context context){
            super(context);
            setBackgroundColor(Color.BLUE);
        }

        @Override
        public void onDraw(Canvas canvas){
            Paint p1 = new Paint();
            p1.setColor(Color.RED);
            p1.setTextSize(50);
            canvas.drawBitmap(screen,0,0,p1);

            canvas.drawBitmap(spaceship, spaceship_x, spaceship_y, p1);
            canvas.drawBitmap(leftkey,leftkey_x,leftkey_y,p1);
            canvas.drawBitmap(rightkey,rightkey_x,rightkey_y,p1);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            int x=0, y=0;

            if(event.getAction()==MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_MOVE){
                x = (int) event.getX();
                y = (int) event.getY();
            }

            if((x>leftkey_x)&&(x<leftkey_x+button_width)&&(y>leftkey_y)&&(x<leftkey_y+button_width))
                spaceship_x-=20;

            if((x>rightkey_x)&&(x<rightkey_x+button_width)&&(y>rightkey_y)&&(x<rightkey_y+button_width))
                spaceship_x+=20;

            invalidate();
            return true;
        }
    }
}
