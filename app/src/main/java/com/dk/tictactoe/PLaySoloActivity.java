package com.dk.tictactoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.dk.tictactoe.databinding.ActivityMainBinding;
import com.dk.tictactoe.databinding.ActivityPlaySoloBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PLaySoloActivity extends AppCompatActivity {

    ActivityPlaySoloBinding binding;
    String playerOneName, playerTwoName ,scorePlayerOnetext,scorePlayerTwotext;
    int playerOneScore = 0,playertwoScore = 0;
    private final List<int[]> combinationsList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;
    private List<ImageView> imageViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_solo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //to lock landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_solo);

        //data for show
        playerOneName = "You";
        playerTwoName = "Bot";
        scorePlayerOnetext = ":           "+playertwoScore;
        scorePlayerTwotext = ":           "+playertwoScore;
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
                    performAction((ImageView) v,0,playerTurn);

                }
            }
        });
        binding.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(1)){
                    performAction((ImageView) v,1,playerTurn);
                }
            }
        });
        binding.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(2)){
                    performAction((ImageView) v,2,playerTurn);
                }
            }
        });
        binding.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(3)){
                    performAction((ImageView) v,3,playerTurn);
                }
            }
        });
        binding.img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(4)){
                    performAction((ImageView) v,4,playerTurn);
                }
            }
        });
        binding.img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(5)){
                    performAction((ImageView) v,5,playerTurn);
                }
            }
        });
        binding.img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(6)){
                    performAction((ImageView) v,6,playerTurn);
                }
            }
        });
        binding.img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(7)){
                    performAction((ImageView) v,7,playerTurn);
                }
            }
        });
        binding.img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBoxSelectable(8)){
                    performAction((ImageView) v,8,playerTurn);
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

       // setup the ai player
        imageViews = new ArrayList<>();
        imageViews.add(binding.img1);
        imageViews.add(binding.img2);
        imageViews.add(binding.img3);
        imageViews.add(binding.img4);
        imageViews.add(binding.img5);
        imageViews.add(binding.img6);
        imageViews.add(binding.img7);
        imageViews.add(binding.img8);
        imageViews.add(binding.img9);


    }
    private void performAction(ImageView imageView, int selectBoxPosition, int currentPlayerTurn){
        boxPositions[selectBoxPosition] = currentPlayerTurn;

        if(currentPlayerTurn == 1){
            imageView.setImageResource(R.drawable.xicon);
            if(checkResult()){
                ResultDialog resultDialog = new ResultDialog(PLaySoloActivity.this,binding.player1Name.getText().toString()
                        +" is a Winner!",PLaySoloActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
                scorePlayerOnetext = ":           "+scorePLayerOneChange();
                binding.player1Score.setText(scorePlayerOnetext);
            } else if (totalSelectedBoxes == 9) {
                ResultDialog resultDialog = new ResultDialog(PLaySoloActivity.this,"It's a Draw!",PLaySoloActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;

            }

        }else{
            imageView.setImageResource(R.drawable.oicon);
            if(checkResult()){
                ResultDialog resultDialog = new ResultDialog(PLaySoloActivity.this,binding.player2Name.getText().toString()
                        +" is a Winner!",PLaySoloActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
                scorePlayerTwotext = ":           "+scorePLayerTwoChange();
                binding.player2Score.setText(scorePlayerTwotext);
            } else if (totalSelectedBoxes == 9) {
                ResultDialog resultDialog = new ResultDialog(PLaySoloActivity.this,"It's a Draw!",PLaySoloActivity.this);
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
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!checkResult() && totalSelectedBoxes < 9) { // Check if game is still ongoing
                    aiMove(imageViews);
                }
            }, 1000);
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
    public void aiMove(List<ImageView> imageViews) {
        int chosenMove = -1;
        // 1. Check for immediate win for AI
        for (int i = 0; i < 9; i++) {
            if (boxPositions[i] == 0) {
                boxPositions[i] = 2;if (checkResult()) {
                    chosenMove = i; // Found a winning move
                    break;
                }
                boxPositions[i] = 0;

            }
        }
        // 2. Check for immediate threat from human (only if no winning move found)
        if (chosenMove == -1) {
            for (int i = 0; i < 9; i++) {
                if (boxPositions[i] == 0) {
                    boxPositions[i] = 1; // Simulate human move
                    if (checkResult()) {
                        chosenMove = i; // Found an immediate threat
                        break;
                    } else {
                        // Check for potential threats (human could win in two moves)
                        for (int j = 0; j < 9; j++) {
                            if (boxPositions[j] == 0) {
                                boxPositions[j] = 1; // Simulate another human move
                                if (checkResult()) {
                                    chosenMove = j; // Found a potential threat
                                    break;
                                }
                                boxPositions[j] = 0; // Reset for next check
                            }
                        }
                        if (chosenMove != -1) break; // Found a potential threat, no need to continue
                    }
                    boxPositions[i] = 0; // Reset for next check
                }
            }
        }

        // 3. Simple strategic choices (only if no win/block found)
        if (chosenMove == -1) {
            if (boxPositions[4] == 0) {
                chosenMove = 4; // Center
            } else {
                List<Integer> availableCorners = new ArrayList<>();
                List<Integer> availableEdges = new ArrayList<>();

                if (!availableCorners.isEmpty()) {
                    chosenMove = availableCorners.get(new Random().nextInt(availableCorners.size()));
                } else if (!availableEdges.isEmpty()) {
                    chosenMove = availableEdges.get(new Random().nextInt(availableEdges.size()));
                }
            }
        }
        if (chosenMove == -1) {
            // ... (Your existing Minimax logic to find bestMove)
            if (!imageViews.isEmpty()) {
                int bestScore = Integer.MIN_VALUE;
                int bestMove = -1;

                for (int i = 0; i < 9; i++) {
                    if (boxPositions[i] == 0) {
                        boxPositions[i] = 2; // Try AI move
                        totalSelectedBoxes++;
                        int score = minimax(0, 1); // Evaluate the move (from human's perspective)
                        boxPositions[i] = 0;
                        totalSelectedBoxes--;

                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = i;
                        }
                    }
                }
                chosenMove = bestMove;
            }
        }


            // 5. Perform the chosen move (if valid)
        if (chosenMove != -1) {
            performAction(imageViews.get(chosenMove), chosenMove, 2); // Pass AI's turn (2)
        }

    }
    private int minimax(int depth, int player) {
        if (checkResult()) {
            return player == 1 ? -1 : 1; // AI wins: +1, Human wins: -1
        } else if (totalSelectedBoxes == 9) {
            return 0; // Draw
        }
        if (depth == 0 || checkResult() || totalSelectedBoxes == 9) {
            // ... (your base case evaluation)
            evaluateBoard();
        }

        int bestScore = player == 2 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (boxPositions[i] == 0) { // Check for empty cells
                boxPositions[i] = player;
                totalSelectedBoxes++;
                int score = minimax(depth + 1, player == 2 ? 1 : 2);
                boxPositions[i] = 0;
                totalSelectedBoxes--;

                if (player == 2) { // AI (maximizing player)
                    bestScore = Math.max(score, bestScore);
                } else { // Human (minimizing player)
                    bestScore = Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }
    private int evaluateBoard() {
        if (checkResult()) {
            return playerTurn == 2 ? 10 : -10; // AI wins: +10, Human wins: -10
        } else if (totalSelectedBoxes == 9) {
            return 0; // Draw
        } else {
            int score = 0;
            // Add logic to assign scores based on board position (e.g., center = +3, corners = +2, etc.)
            return score;
        }
    }
    }


