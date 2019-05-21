package com.example.minesweeper;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
/*
 * Mine 클래스
 * 각 타일들에 대한 정보들을 담고 있는 객체다.

 */

public class Mine extends ImageButton {
    Context context;
    int cellSize;
    int row, col; // row 행, col 열
    int nearMine = 0;

    boolean isMine = false;
    boolean isVisible = false;
    boolean isFlag = false;

    private Vibrator vibrator;

    public Mine(Context context) {
        super(context);
        this.context = context;

        cellSize = MainActivity.getCellSize();
        setImageResource(R.drawable.cell);
        setMinimumWidth(cellSize);
        setMaxWidth(cellSize);
        setMinimumHeight(cellSize);
        setMaxHeight(cellSize);
        setAdjustViewBounds(true);
        setScaleType(ImageView.ScaleType.FIT_XY);
        setPadding(0, 0, 0, 0);

        //lis = new MineListener();
        //setOnClickListener(lis);
    }
    public Mine(Context context, int x, int y) {
        super(context);
        this.context = context;

        cellSize = MainActivity.getCellSize();
        setImageResource(R.drawable.cell);
        setMinimumWidth(cellSize);
        setMaxWidth(cellSize);
        setMinimumHeight(cellSize);
        setMaxHeight(cellSize);
        setAdjustViewBounds(true);
        setScaleType(ImageView.ScaleType.FIT_XY);
        setPadding(0, 0, 0, 0);

        row = x;
        col = y;

    }
    public void checkMine(int x, int y) {
        if(x < 0 || y < 0 || x >= row || y >= col){
            return;
        }

    }


    public void setFlagImage(){
        if(isFlag){
            setImageResource(R.drawable.flag);
        }
        else{
            setImageResource(R.drawable.cell);
        }
    }

    // init
    public void initImage(){
        if(isFlag){
            setImageResource(R.drawable.flag);
            return;
        }

        Log.e("InitImage", "nearMine" + nearMine);
        this.isVisible = true;

        if (isMine) {
            // 지뢰를 골랐다.
            setImageResource(R.drawable.mine);

        } else {
            if (nearMine == 0) {
                setImageResource(R.drawable.cell_0);
            } else if (nearMine == 1) {
                setImageResource(R.drawable.cell_1);
            } else if (nearMine == 2) {
                setImageResource(R.drawable.cell_2);
            } else if (nearMine == 3) {
                setImageResource(R.drawable.cell_3);
            } else if (nearMine == 4) {
                setImageResource(R.drawable.cell_4);
            } else if (nearMine == 5) {
                setImageResource(R.drawable.cell_5);
            } else {
                setImageResource(R.drawable.cell);
            }
        }
    }

    // 반환 값이 지뢰 카운트의 값에 더해진다.
    public int calcFlag(boolean isFull) {
        int cnt = 0;
        Toast.makeText(context, "깃발깃발", Toast.LENGTH_LONG).show();
        MyMethod my = new MyMethod(context);
        my.Vibrate(100);

        if(isFull){
            Toast.makeText(context, "깃발이 가득 찼습니다.", Toast.LENGTH_LONG).show();
            return cnt;
        }


        if(isFlag){
            isFlag = false;
            cnt = 1;
        }
        else{
            isFlag = true;
            cnt = -1;
        }
        setFlagImage();

        return cnt;
    }

    public boolean isMineClear(){
        if(isMine && isFlag){
            return true;
        }
        else if(!isMine && !isFlag){
            return true;
        }
        // 마인제거 실패,
        return false;
    }

    // set, get 메소드 구현부
    public void setImage(int id){
        this.setImageResource(id);
    }



    // set 메소드
    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNearMine() {
        return nearMine;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isFlag() {
        return isFlag;
    }
}
