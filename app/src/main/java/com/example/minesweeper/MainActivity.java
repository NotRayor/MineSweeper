package com.example.minesweeper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final int CELL_SIZE_SMALL = 60;
    static final int CELL_SIZE_MIDIUM = 70;
    static final int CELL_SIZE_LARGE = 100;


    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout gameBoard;
    Button btn1;
    Mine btnArray[][];

    static int cols, rows;
    int mineNum = 0;
    int cellSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = (LinearLayout) findViewById(R.id.game_board);
        cellSize = CELL_SIZE_LARGE;

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

        rows = height / cellSize;
        cols = width / cellSize;

        btnArray = new Mine[rows][cols]; // 행열 크기의 버튼을 생성!

        // 셀을 정했는데 그럼 버튼들 배치는 어떻게??
        for (int i = 0; i < rows; i++) {
            LinearLayout myLayout = new LinearLayout(this);
            myLayout.setOrientation(LinearLayout.HORIZONTAL);
            gameBoard.addView(myLayout);

            for (int j = 0; j < cols; j++) {
                btnArray[i][j] = new Mine(this, i, j);
                btnArray[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("onClick", "onClick Initiate");
                        Mine block = (Mine) v;
                        searchMine(block.row, block.col);
                        Log.e("onClick", block.row + "," + block.col);
                    }
                });

                myLayout.addView(btnArray[i][j], params);
            }
        }


        double mineRatio = 0;

        // 난이도에 따른 지뢰 설정
        if (true) {
            mineRatio = 0.15;
            mineNum = (int) (rows * cols * mineRatio);
        }

        // 지뢰 랜덤 설치
        Random rand = new Random();
        int nMineNum = 0;

        while (nMineNum < mineNum) {
            nMineNum++;
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);

            // 만약 이미 지뢰가 있는 타일이면, 루프를 한번 더 돌리도록 정의
            if (btnArray[randRow][randCol].isMine) {
                nMineNum--;
            }
            btnArray[randRow][randCol].setIsMine(true);

        }


        Log.e("Width Height Values", width + "," + height);
        Log.e("Cols Rows Values", "rows = " + rows + "," + "cols = " + cols);
        Log.e("mineNum", String.format("mineNum : %d \n", mineNum));
    }


    // 인접타일을 계산하고 예외.. 식으로 아까와는 좀 다르게,
    // 반환식이 쓸데가 있나?? 주위를 다시 둘러보는 경우에 true로 하자
    public boolean searchMine(int x, int y) {
        Log.e("saerchMine start", "start" + x +", " + y);

        if (btnArray[x][y].isMine) {
            Log.e("searchMine", "Mine Selected");
            btnArray[x][y].initImage();
            return false;
        } else if (btnArray[x][y].isVisible) {
            Log.e("searchMine", "Visit Block");
            return false;
        } else {
            for (int sRow = x - 1; sRow <= x + 1; sRow++) {
                for (int sCol = y - 1; sCol <= y + 1; sCol++) {

                    if (sRow == x && sCol == y) {
                        continue;
                    }
                    if (sRow < 0 || sCol < 0 || sRow >= rows || sCol >= cols) {
                        continue;
                    }

                    Log.e("searchMine", "UnKnown Block " + sRow + ", " + sCol);
                    if (btnArray[sRow][sCol].isMine) {
                        btnArray[x][y].nearMine++;
                    }
                }
            }
            Log.e("btnArray", x + "," + y + ", nearMine : " + btnArray[x][y].nearMine);

            if (btnArray[x][y].nearMine != 0) {
                btnArray[x][y].initImage();
            } else {
                Log.e("searchMine", "searchMine recursive enter");
                btnArray[x][y].initImage();
                for (int sRow = x - 1; sRow <= x + 1; sRow++) {
                    for (int sCol = y - 1; sCol <= y + 1; sCol++) {
                        Log.e("searchMine", "calc... "  + sRow + ", " + sCol + " x : " + x + " y : " + y + "cols " + cols + "rows " + rows);
                        if (sRow == x && sCol == y) {
                            continue;
                        }
                        if (sRow < 0 || sCol < 0 || sRow >= rows || sCol >= cols) {
                            continue;
                        }

                        Log.e("searchMine", "searchMine recursive");
                        searchMine(sRow, sCol);
                    }
                }
            }

            return false;

        }


    }


    // set, get 메소드들 구현부

    //get
    static public int getCellSize() {

        // 추후, 환경설정의 셀 선택 값에 따라 셀의 크기를 정하도록 구현
        return CELL_SIZE_LARGE;
    }


}








