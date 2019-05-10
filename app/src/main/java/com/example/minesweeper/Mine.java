package com.example.minesweeper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/*
* Mine 클래스
* 각 타일들에 대한 정보들을 담고 있는 객체다.


 */

public class Mine extends ImageButton {
    int row, col; // 열과 행
    boolean isMine = false;
    boolean isTouched = false;

    public Mine(Context context) {
        super(context);
    }
    public Mine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
