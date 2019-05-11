package com.example.minesweeper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/*
 * Mine 클래스
 * 각 타일들에 대한 정보들을 담고 있는 객체다.

 */

public class Mine extends ImageButton {
    int cellSize;
    int row, col; // row 행, col 열
    int nearMine = 0;

    boolean isMine = false;
    boolean isVisible = false;
    MineListener lis;

    public class MineListener {
        /*
        @Override
        public void onClick(View v) {

            Log.e("myMine", isMine + "");

            if (isMine) {
                // 게임 패배 구현부

            }
            else{

            }
        }
        */
    }

    public Mine(Context context) {
        super(context);

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

        //lis = new MineListener();
        //setOnClickListener(lis);
    }
    public void checkMine(int x, int y) {


    }


/*
    public boolean searchMine(int x, int y) {

        // 이게.. 음..
        if (x < 0 || y < 0 || x > rows || y > cols) {
            return false;
        }
        // 이미 밝혀진 타일이면 false, 수행 안함
        else if (btnArray[x][y].isVisible) {
            return false;
        }
        // 이제 연산을 하자
        else {
            // 각 8번의 재귀함수 속에서... 체크해서 값 추가해야함, 근데 재귀에서 계속 올라오면 결국은 죄다 true 아니겠는가?
            // 주위 8개 체크하는 건 다른 함수로 체크하자?  아닌데..

            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {

                    if (x == i && y == j) {
                        continue;
                    }

                    if (btnArray[i][j].isMine) {
                        btnArray[x][y].nearMine++;
                    }
                }
            }

            if (!btnArray[x][y].isMine){
                btnArray[x][y].initImage();
                searchMine(x - 1, y + 1);
                searchMine(x, y + 1);
                searchMine(x + 1, y + 1);
                searchMine(x + 1, y);
                searchMine(x + 1, y - 1);
                searchMine(x, y - 1);
                searchMine(x - 1, y - 1);
                searchMine(x - 1, y);

                return true;
            }

            return false;
        }

    }*/

    // init
    public void initImage(){
        Log.e("InitImage", "nearMine" + nearMine);
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

    // set, get 메소드 구현부
    public void setImage(int id){
        this.setImageResource(id);
    }



    // set 메소드
    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }



}
