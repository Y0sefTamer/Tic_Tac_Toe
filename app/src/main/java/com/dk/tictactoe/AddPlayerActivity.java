package com.dk.tictactoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.dk.tictactoe.databinding.ActivityAddPlayerBinding;

public class AddPlayerActivity extends AppCompatActivity {

    ActivityAddPlayerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //to lock landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_player);

       binding.startBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if ( (binding.namePlayer1.getText().toString().isEmpty() || binding.namePlayer2.getText().toString().isEmpty())) {
                   Toast.makeText(getApplicationContext(), "Please enter player name", Toast.LENGTH_SHORT).show();
               } else {
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   intent.putExtra("player1", binding.namePlayer1.getText().toString().toLowerCase());
                   intent.putExtra("player2", binding.namePlayer2.getText().toString().toLowerCase());
                   startActivity(intent);

               }
           }
       });



    }
}