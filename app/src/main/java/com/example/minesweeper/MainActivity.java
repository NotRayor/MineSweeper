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

    static final int BOARD_WIDTH_SMALL = 512;
    static final int BOARD_HEIGHT_SMALL = 512;
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

    int sRows = 0;
    int sCols = 0;


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
                        Mine block = (Mine) v;
                        searchMine(block.row, block.col);
                        block.initImage();
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

    // 클릭 시, 연속되어 배치된 빈타일, 지뢰인접타일들을 모두 밝힌다.
    // 어떻게 밝히는가? 지뢰가 아니라면 true로, Mine이 Click 된 것처럼 만든다.
    // 이때 연산해서, Mine에 숫자를 부여해준다. // 혹은 게임 시작시 셋팅할때 밑바탕에 숫자를 깔아둘 수도 있기는 하다..
    // 이런 마인 찾기 작업을 여기서 처리하는 게 맞는가.. 음..
    // 지뢰가 있으면 True로 하는 식으로 해결해보자. 그러면 그 갯수를 가지고 파악할 수 있지 않을까? 아닌가 재귀 처리면 그걸 그렇게 인식하진 못하나..
    public boolean searchMine(int x, int y) {

        Log.e("searchMine", "x:" + x + ", " + "y:" + y);

        // 이게.. 음..
        if (x < 0 || y < 0 || x >= rows || y >= cols) {
            Log.e("searchMine", "initiate" + rows + ", " + cols);
            return false;
        }
        // 지뢰라면 동작 끝
        else if (btnArray[x][y].isMine) {
            Log.e("searchMine", "initiate2");
            return true;
        }
        // 이미 방문한 타일이라면 동작 끝
        else if (btnArray[x][y].isVisible){
            return false;
        }
        // 이제 연산을 하자
        else {
            Log.e("searchMine", "initiate3");
            btnArray[x][y].isVisible = true;

            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {

                    if ((x == i && y == j) || i < 0 || j < 0 || i >= rows || j >= cols ){
                        continue;
                    }
                    if(searchMine(i,j)) {
                        btnArray[x][y].nearMine++;
                        Log.e("nearMine Find", "x = " + x + ", " + y + " nearMine " + btnArray[x][y].nearMine);
                    }
                }
            }

            Log.e("nearMine", String.format("%s", btnArray[x][y].nearMine));

            // 지뢰가 아니라면 세팅! 아니면 엥??
            if (!btnArray[x][y].isMine) {
                btnArray[x][y].initImage();
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








