package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // 환경설정 값
    static final int CELL_SIZE_SMALL = 60;
    static final int CELL_SIZE_MIDIUM = 80;
    static final int CELL_SIZE_LARGE = 100;
    static int counter = 0;


    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout gameBoard;
    Button mineCount;
    Button gameTimer;
    Button btn1;
    Mine btnArray[][];
    Thread timerThread;


    static int cols, rows;
    int mineNum = 0;
    int sMineNum = 0;
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
        mineCount = (Button) findViewById(R.id.mineCount);
        gameTimer = (Button) findViewById(R.id.gameTimer);



    }

    // 게임판 초기화
    public void initGame() {
        // 타이머 쓰레드가 실행되고 있다면, 타이머 초기화하고 다시 시작,
        if (timerThread != null) {
            timerThread.interrupt();
            counter = 0;
        }
        timerThread = new Thread() {
            @Override
            public void run() {
                // interrupt() 발생 시, InterruptedException을 발생시킨다.

                Log.e("Counter = ", counter + "");
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        counter++;
                        gameTimer.setText(String.format("%d", counter));
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    Log.e("Timer Thread Error", "");
                } finally {
                    Log.e("Thread Dead", this.getName() + " " + Thread.currentThread().isInterrupted());
                }
            }
        };
        timerThread.start();

        // gameBoard 모든 뷰 청소,
        gameBoard.removeAllViews();
    }

    // 게임판 만들기 ( 지뢰 갯수, 타일 갯수 판단 )
    public void makeGame() {
        initGame();

        Random rand = new Random();
        int nMineNum = 0;
        double mineRatio = 0;
        int width = gameBoard.getWidth();
        int height = gameBoard.getHeight();

        rows = height / cellSize;
        cols = width / cellSize;

        btnArray = new Mine[rows][cols]; // 행열 크기의 버튼을 생성!

        // 게임판 내부 셀 배치
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
                        // 깃발이 안 꽂혔다면, 연산 실행
                        if (!block.isFlag) {
                            searchMine(block.row, block.col);
                        }
                    }
                });
                btnArray[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Mine block = (Mine) v;
                        // 전체 추정 지뢰 갯수를 반환 값을 통해 조정,
                        sMineNum += block.calcFlag(sMineNum == 0);
                        mineCount.setText(String.format("%d", sMineNum));
                        // 게임이 끝났는 지 확인,
                        gameEnd();
                        return true;
                    }
                });
                myLayout.addView(btnArray[i][j], params);
            }
        }

        // 난이도에 따른 지뢰 설정
        if (true) {
            mineRatio = 0.15;
            mineNum = (int) (rows * cols * mineRatio);
            mineCount.setText(String.format("%d", mineNum));
            sMineNum = mineNum;
        }

        // 지뢰 랜덤 설치
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
        // 지뢰 선택
        if (btnArray[x][y].isMine) {
            btnArray[x][y].initImage();
            Toast.makeText(getApplicationContext(), "패배! 지뢰를 선택했습니다.", Toast.LENGTH_LONG).show();
            gameFail();
            return false;
            // 이미 방문한 셀
        } else if (btnArray[x][y].isVisible) {
            return false;
        } else {
            // 현재 셀 기준 주변 셀 모두 확인, 지뢰 갯수 파악
            for (int sRow = x - 1; sRow <= x + 1; sRow++) {
                for (int sCol = y - 1; sCol <= y + 1; sCol++) {

                    if (sRow == x && sCol == y) {
                        continue;
                    }
                    if (sRow < 0 || sCol < 0 || sRow >= rows || sCol >= cols) {
                        continue;
                    }
                    if (btnArray[sRow][sCol].isMine) {
                        btnArray[x][y].nearMine++;
                    }
                }
            }

            // 주변에 지뢰가 있다.
            if (btnArray[x][y].nearMine != 0) {
                btnArray[x][y].initImage();
            }
            // 주변에 지뢰가 없다. 주변에 셀 기준으로 다시 재귀함수 실행,
            else {
                btnArray[x][y].initImage();
                Log.e("searchMine", "searchMine recursive enter");
                for (int sRow = x - 1; sRow <= x + 1; sRow++) {
                    for (int sCol = y - 1; sCol <= y + 1; sCol++) {
                        // 예외사항, 자신을 선택하거나, 맵 바깥을 빠져나가면, 다음 루틴으로 넘긴다.
                        if (sRow == x && sCol == y) {
                            continue;
                        }
                        if (sRow < 0 || sCol < 0 || sRow >= rows || sCol >= cols) {
                            continue;
                        }
                        searchMine(sRow, sCol);
                    }
                }
            }
            return false;
        }
    }


    protected void gameEnd() {
        boolean isFail = false;

        if (sMineNum == 0) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    // isMineClear, 마인제거시 true, 실패시 false, 한번이라도 false가 뜨면 if문 처리
                    if (!btnArray[i][j].isMineClear()) {
                        isFail = true;
                    }
                    // 선택되지 않은 셀이 남아있을 경우, 게임 승부 판정을 하면 안된다.
                    if (!btnArray[i][j].isVisible() && !btnArray[i][j].isFlag()){
                        return;
                    }
                }
            }
            if (isFail)
                gameFail();
            else
                gameWin();
        }

    }

    protected void gameWin() {
        Toast.makeText(getApplication(), "승리하셨습니다. " + "걸린 시간 : " + counter, Toast.LENGTH_LONG).show();
    }

    protected void gameFail() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                btnArray[i][j].isVisible = true;
                if (btnArray[i][j].isMine) {
                    btnArray[i][j].initImage();
                }
            }

        }
    }


    // set, get 메소드들 구현부

    //get
    static public int getCellSize() {

        // 추후, 환경설정의 셀 선택 값에 따라 셀의 크기를 정하도록 구현
        return CELL_SIZE_LARGE;
    }


}








