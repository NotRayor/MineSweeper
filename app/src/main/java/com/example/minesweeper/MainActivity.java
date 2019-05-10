package com.example.minesweeper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    static final int BOARD_WIDTH_SMALL = 512;
    static final int BOARD_HEIGHT_SMALL = 512;

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout gameBoard;
    Button btn1;
    int mineNum = 0;
    int cellSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = (LinearLayout) findViewById(R.id.game_board);
        cellSize = 100;

        btn1 = (Button) findViewById(R.id.startButton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeGame();
            }
        });

    }

    // 게임판 초기화
    public void initGame() {
        gameBoard.removeAllViews();

    }

    // 게임판 만들기 ( 지뢰 갯수, 타일 갯수 판단 )
    public void makeGame() {
        initGame();

        int width = gameBoard.getWidth();
        int height = gameBoard.getHeight();

        int rows = width / cellSize;
        int cols = height / cellSize;

        ImageButton btnArray[][] = new ImageButton[cols][rows]; // 행열 크기의 버튼을 생성!

        // 셀을 정했는데 그럼 버튼들 배치는 어떻게??
        for (int i = 0; i < cols; i++) {
            LinearLayout myLayout = new LinearLayout(this);
            myLayout.setOrientation(LinearLayout.HORIZONTAL);
            gameBoard.addView(myLayout);

            for (int j = 0; j < rows; j++) {
                btnArray[i][j] = new ImageButton(this);
                btnArray[i][j].setImageResource(R.drawable.cell);
                btnArray[i][j].setMinimumWidth(cellSize);
                btnArray[i][j].setMaxWidth(cellSize);
                btnArray[i][j].setMinimumHeight(cellSize);
                btnArray[i][j].setMaxHeight(cellSize);
                btnArray[i][j].setAdjustViewBounds(true);
                btnArray[i][j].setScaleType(ImageView.ScaleType.FIT_XY);
                btnArray[i][j].setPadding(0,0,0,0);

                btnArray[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ImageButton)v).setImageResource(R.drawable.cell_0);
                    }
                });

                myLayout.addView(btnArray[i][j], params);
            }
        }


        double mineRatio = 0;

        // 난이도에 따른 지뢰 설정
        if (true) {
            mineRatio = 0.15;
            mineNum = (int) (cols * rows * mineRatio);
        }

        // 지뢰 랜덤 설치

        Log.e("Width Height Values", width + "," + height);
        Log.e("Cols Rows Values", "cols = " + cols + "," + "rows = " + rows);
        Log.e("mineNum", String.format("mineNum : %d \n", mineNum));
    }

}








