package com.wordpress.amaz1ngc0de.animations;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import asynctasks.TimerAsyncTask;

public class GameFragment extends Fragment {

    //Boxes
    private TextView mRedBox, mGreenBox, mBlueBox, mBlackBox;
    //Labels
    private TextView txtHitCounter, txtHighScore, txtRemainingTime;
    //Buttons
    private Button btnFreeze;

    private Random random;
    private CountDownTimer countDownTimer;
    private int count;
    private int countSum;
    private int highScore;

    private boolean timeIsRunning;

    private Context ctx;
    private View view;

    public GameFragment() {
        //Nao iniciar componentes de tela aqui
        //iniciar eles depois de inflar o layout
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);
        ctx = view.getContext();

        final SharedPreferences prefs = ctx.getSharedPreferences("hitColor", ctx.MODE_PRIVATE);
        highScore = prefs.getInt("highScore", 0);

        startScreenVariables();
        startComponents();
        setLabel(txtHighScore, R.string.str_high_score, highScore);

        //mesmo evento para todos quadrados
        setSquaresClickEvent(mRedBox, mBlueBox, mGreenBox, mBlackBox);

//        setUpTimer();

//        setFreezeEvent(btnFreeze);

        return view;
    }

    private void setFreezeEvent(Button btnFreeze) {
        btnFreeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerAsyncTask tta = new TimerAsyncTask(txtRemainingTime, getActivity(), countSum);
                tta.execute(10);
            }
        });
    }

    private void startScreenVariables(){
        random = new Random(1);
        count = 1;
        countSum = 0;
        timeIsRunning = false;
    }

    private void startComponents(){
        btnFreeze = (Button) view.findViewById(R.id.btn_freeze);

        txtRemainingTime = (TextView) view.findViewById(R.id.txt_remaining_time);
        txtHitCounter = (TextView) view.findViewById(R.id.txt_hit_counter);
        txtHighScore = (TextView) view.findViewById(R.id.txt_high_score);

        mRedBox = (TextView) view.findViewById(R.id.red_box);
        mGreenBox = (TextView) view.findViewById(R.id.green_box);
        mBlueBox = (TextView) view.findViewById(R.id.blue_box);
        mBlackBox = (TextView) view.findViewById(R.id.black_box);
    }

    private void setSquaresClickEvent(View... views){
        for(View v : views){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventClick();
                }
            });
        }
    }

    public void eventClick() {
        toggleVisibility();

        if(!timeIsRunning) {
            TimerAsyncTask tta = new TimerAsyncTask(txtRemainingTime, getActivity(), countSum);
            tta.execute(10);

            //timeIsRunning = true;
        }

        countSum++;
        setLabel(txtHitCounter, R.string.str_acertos, countSum);
    }


//    private void setUpTimer(){
//        countDownTimer = new CountDownTimer(10000, 1000) {
//
//            @Override
//            public void onTick(long l) {
//                txtRemainingTime.setText(getString(R.string.str_remaining_time)+ " " + l/1000);
//            }
//
//            @Override
//            public void onFinish() {
//                timeIsRunning = false;
//                Intent i = new Intent(ctx, MainActivity.class);
//                i.putExtra("CountSum", countSum);
//                startActivity(i);
//
//                //txtRemainingTime.setText(R.string.str_remaining_time);
//            }
//        };
//    }

    public void setLabel(TextView label, int labelStringId, int count){
        StringBuilder sb = new StringBuilder();
        sb.append(getString(labelStringId)).append(" ").append(count);
        label.setText(sb.toString());
    }

    private void toggleVisibility() {
        int randomNumber = random.nextInt(4);

        mRedBox.setVisibility(View.INVISIBLE);
        mBlueBox.setVisibility(View.INVISIBLE);
        mGreenBox.setVisibility(View.INVISIBLE);
        mBlackBox.setVisibility(View.INVISIBLE);

        switch (randomNumber){
            case 0:
                if(mRedBox.getVisibility() == View.VISIBLE){
                    mRedBox.setVisibility(View.INVISIBLE);

                    mRedBox.setVisibility(View.VISIBLE);
                    mRedBox.setText(String.valueOf(count));
                }
                else {
                    mRedBox.setVisibility(View.VISIBLE);
                    mRedBox.setText("");
                    count = 1;
                }
                break;

            case 1:
                if(mGreenBox.getVisibility() == View.VISIBLE){
                    mGreenBox.setVisibility(View.INVISIBLE);

                    mGreenBox.setVisibility(View.VISIBLE);
                    mGreenBox.setText(String.valueOf(count));
                }
                else {
                    mGreenBox.setVisibility(View.VISIBLE);
                    mGreenBox.setText("");
                    count = 1;
                }
                break;

            case 2:
                if(mBlueBox.getVisibility() == View.VISIBLE){
                    mBlueBox.setVisibility(View.INVISIBLE);

                    mBlueBox.setVisibility(View.VISIBLE);
                    mBlueBox.setText(String.valueOf(count));
                }
                else {
                    mBlueBox.setVisibility(View.VISIBLE);
                    mBlueBox.setText("");
                    count = 1;
                }
                break;

            case 3:
                if(mBlackBox.getVisibility() == View.VISIBLE){
                    mBlackBox.setVisibility(View.INVISIBLE);

                    mBlackBox.setVisibility(View.VISIBLE);
                    mBlackBox.setText(String.valueOf(count));
                    count++;
                }
                else {
                    mBlackBox.setVisibility(View.VISIBLE);
                    mBlackBox.setText("");
                    count = 1;
                }
                break;
        }
    }
}
