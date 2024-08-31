package com.dk.tictactoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.dk.tictactoe.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String playerOneName, playerTwoName ,scorePlayerOnetext,scorePlayerTwotext;
    int playerOneScore = 0,playertwoScore = 0;
    private final List<int[]> combinationsList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //to lock landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //get data from addplayeractivity
        playerOneName = getIntent().getStringExtra("player1");
        playerTwoName = getIntent().getStringExtra("player2");
        scorePlayerOnetext = ":           "+playertwoScore;
        scorePlayerTwotext = ":           "+playertwoScore;
        if (playerOneName.length() > 5) playerOneName = playerOneName.substring(0, 5);
        if (playerTwoName.length() > 5) playerTwoName = playerTwoName.substring(0, 5);
        binding.player1Name.setText(playerOneName);
        binding.player2Name.setText(playerTwoName);
        binding.player1Score.setText(scorePlayerOnetext);
        binding.player2Score.setText(scorePlayerTwotext);
        binding.whoPlayText.setText(""+playerOneName+" Turn");

        // setup the game
        combinationsList.add(new int[]{0, 1, 2});
        combinationsList.add(new int[]{3, 4, 5});
        combinationsList.add(new int[]{6, 7, 8});
        combinationsList.add(new int[]{0, 3, 6});
        combinationsList.add(new int[]{1, 4, 7});
        combinationsList.add(new int[]{2, 5, 8});
        combinationsList.add(new int[]{2, 4, 6});
        combinationsList.add(new int[]{0, 4, 8});

        binding.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(0)){
                    performAction((ImageView) v,0);
                }
            }
        });
        binding.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(1)){
                    performAction((ImageView) v,1);
                }
            }
        });
        binding.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(2)){
                    performAction((ImageView) v,2);
                }
            }
        });
        binding.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(3)){
                    performAction((ImageView) v,3);
                }
            }
        });
        binding.img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(4)){
                    performAction((ImageView) v,4);
                }
            }
        });
        binding.img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(5)){
                    performAction((ImageView) v,5);
                }
            }
        });
        binding.img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(6)){
                    performAction((ImageView) v,6);
                }
            }
        });
        binding.img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(7)){
                    performAction((ImageView) v,7);
                }
            }
        });
        binding.img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(8)){
                    performAction((ImageView) v,8);
                }
            }
        });
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartMatchBtn();
            }
        });
        binding.aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
        binding.backArrowMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });



    }
    private void performAction(ImageView imageView, int selectBoxPosition){
        boxPositions[selectBoxPosition] = playerTurn;

        if(playerTurn == 1){
            imageView.setImageResource(R.drawable.xicon);
            if(checkResult()){
                ResultDialog resultDialog = new ResultDialog(MainActivity.this,binding.player1Name.getText().toString()
                        +" is a Winner!",MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
                scorePlayerOnetext = ":           "+scorePLayerOneChange();
                binding.player1Score.setText(scorePlayerOnetext);
            } else if (totalSelectedBoxes == 9) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this,"It's a Draw!",MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;
                
            }

        }else{
            imageView.setImageResource(R.drawable.oicon);
            if(checkResult()){
                ResultDialog resultDialog = new ResultDialog(MainActivity.this,binding.player2Name.getText().toString()
                        +" is a Winner!",MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
                scorePlayerTwotext = ":           "+scorePLayerTwoChange();
                binding.player2Score.setText(scorePlayerTwotext);
            } else if (totalSelectedBoxes == 9) {
                ResultDialog resultDialog = new ResultDialog(MainActivity.this,"It's a Draw!",MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;

            }
        }
    }
    private void changePlayerTurn(int currentPlayerTurn){
        playerTurn = currentPlayerTurn;
        if(playerTurn == 1){
            binding.whoPlayText.setText(""+playerOneName+" Turn");
        }else{
            binding.whoPlayText.setText(""+playerTwoName+" Turn");
        }
    }
    private boolean checkResult(){
        boolean isResult = false;
        for(int i = 0; i < combinationsList.size(); i++){
            final int[] combination = combinationsList.get(i);

            if(boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) isResult = true;
        }
        return isResult;
    }
    private boolean isBoxSelectable(int boxPosition) {
        boolean isSelectable = false;
        
        if(boxPositions[boxPosition] == 0) isSelectable = true;

        return isSelectable;
    }
    public void  restartMatch(){
        boxPositions = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerTurn = 1;
        totalSelectedBoxes = 1;
        binding.img1.setImageResource(R.drawable.transparentbackground);
        binding.img2.setImageResource(R.drawable.transparentbackground);
        binding.img3.setImageResource(R.drawable.transparentbackground);
        binding.img4.setImageResource(R.drawable.transparentbackground);
        binding.img5.setImageResource(R.drawable.transparentbackground);
        binding.img6.setImageResource(R.drawable.transparentbackground);
        binding.img7.setImageResource(R.drawable.transparentbackground);
        binding.img8.setImageResource(R.drawable.transparentbackground);
        binding.img9.setImageResource(R.drawable.transparentbackground);
    }
    public void  restartMatchBtn(){
        boxPositions = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerTurn = 1;
        totalSelectedBoxes = 1;
        binding.img1.setImageResource(R.drawable.transparentbackground);
        binding.img2.setImageResource(R.drawable.transparentbackground);
        binding.img3.setImageResource(R.drawable.transparentbackground);
        binding.img4.setImageResource(R.drawable.transparentbackground);
        binding.img5.setImageResource(R.drawable.transparentbackground);
        binding.img6.setImageResource(R.drawable.transparentbackground);
        binding.img7.setImageResource(R.drawable.transparentbackground);
        binding.img8.setImageResource(R.drawable.transparentbackground);
        binding.img9.setImageResource(R.drawable.transparentbackground);
        scorePlayerOnetext = ":           "+0;
        scorePlayerTwotext = ":           "+0;
        playerOneScore = 0;
        playertwoScore = 0;
        binding.player2Score.setText(scorePlayerTwotext);
        binding.player1Score.setText(scorePlayerOnetext);
        binding.whoPlayText.setText(""+playerOneName+" Turn");
    }
    public int scorePLayerOneChange(){
        return ++playerOneScore;
    }
    public int scorePLayerTwoChange(){
        return ++playertwoScore;
    }
}