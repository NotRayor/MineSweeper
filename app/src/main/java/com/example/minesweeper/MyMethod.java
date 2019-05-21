package com.example.minesweeper;

import android.content.Context;
import android.os.Vibrator;

public class MyMethod extends Thread{
     Vibrator vib;

    MyMethod(Context context){

        vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    void Vibrate(int ms){
        vib.vibrate(ms);
    }





    @Override
    public void run() {


    }
}
