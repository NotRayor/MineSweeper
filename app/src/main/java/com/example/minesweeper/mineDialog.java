package com.example.minesweeper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class mineDialog extends Dialog {

    MainActivity main;
    mineDialog dlg;

    // 환경설정 값
    static final int CELL_SIZE_SMALL = 60;
    static final int CELL_SIZE_MIDIUM = 80;
    static final int CELL_SIZE_LARGE = 100;

    static final double DF_EAZY = 0.15;
    static final double DF_NORMAL = 0.20;
    static final double DF_HARD = 0.30;

    int cellSize;
    double df;

    Button acceptButton;

    RadioButton szLowButton;
    RadioButton szMiddleButton;
    RadioButton szBigButton;

    RadioButton dfEasyButton;
    RadioButton dfNormalButton;
    RadioButton dfHardButton;

    RadioGroup sizeGroup;
    RadioGroup difficultyGroup;


    // 생성자??
    public mineDialog(Context context, int size, double diffi) {
        super(context);
        setContentView(R.layout.mine_dialog);

        dlg = this;

        Log.e("mineDialog", "Activate");

        cellSize = size;
        df = diffi;

        acceptButton = (Button)findViewById(R.id.accept);

        sizeGroup = (RadioGroup) findViewById(R.id.sizeGroup);
        difficultyGroup = (RadioGroup) findViewById(R.id.difficultyGroup);

        szLowButton = (RadioButton) findViewById(R.id.sizeLowButton);
        szMiddleButton = (RadioButton) findViewById(R.id.sizeMiddleButton);
        szBigButton = (RadioButton) findViewById(R.id.sizeBigButton);

        dfEasyButton = (RadioButton) findViewById(R.id.difficultyEasyButton);
        dfNormalButton = (RadioButton) findViewById(R.id.difficultyNormalButton);
        dfHardButton = (RadioButton) findViewById(R.id.difficultyHardButton);




        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main = (MainActivity) dlg.getOwnerActivity(); // 자기를 소유한 대화상자 액티비티의 참조변수를 반환하는 메소드,
                main.setOption(cellSize,df);
                dismiss();
            }
        });

        // 셀크기가 변화할 때 호출되는 콜백 메소드
        sizeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sizeLowButton:
                        cellSize = CELL_SIZE_SMALL;
                        break;
                    case R.id.sizeMiddleButton:
                        cellSize = CELL_SIZE_MIDIUM;
                        break;
                    case R.id.sizeBigButton:
                        cellSize = CELL_SIZE_LARGE;
                        break;
                }
            }
        });

        // 난이도 변화할 때 호출되는 콜백 메소드
        difficultyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.difficultyEasyButton:
                        df = DF_EAZY;
                        break;
                    case R.id.difficultyNormalButton:
                        df = DF_NORMAL;
                        break;
                    case R.id.difficultyHardButton:
                        df = DF_HARD;
                        break;
                }
            }
        });

        // 값에 맞게 초기화,
        if (df == DF_EAZY) {
            dfEasyButton.setChecked(true);
        } else if (df == DF_NORMAL) {
            dfNormalButton.setChecked(true);
        } else if (df == DF_HARD){
            dfHardButton.setChecked(true);
        }

        if (cellSize == CELL_SIZE_SMALL){
            szLowButton.setChecked(true);
        }else if(cellSize ==CELL_SIZE_MIDIUM){
            szMiddleButton.setChecked(true);
        }else{
            szBigButton.setChecked(true);
        }


    }


    void setupDialog() {

    }


}
