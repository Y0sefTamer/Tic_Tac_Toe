package com.dk.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultDialog extends Dialog {

    private  final  String message;
    private MainActivity mainActivity;
    private  PLaySoloActivity playSoloActivity;

    public ResultDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
    }
    public ResultDialog(@NonNull Context context,String message,PLaySoloActivity playSoloActivity) {
        super(context);
        this.message = message;
        this.playSoloActivity = playSoloActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView messageText = findViewById(R.id.message_text);
        messageText.setText(message);
        Button startAgainButton = findViewById(R.id.start_again_button);
        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainActivity != null){
                    mainActivity.restartMatch();
                    dismiss();
                }else{
                    playSoloActivity.restartMatch();
                    dismiss();
                }


            }
        });

    }
}