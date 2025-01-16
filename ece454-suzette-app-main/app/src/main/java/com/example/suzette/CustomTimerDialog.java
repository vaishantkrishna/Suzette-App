package com.example.suzette;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomTimerDialog extends Dialog {
    private CountDownTimer countDownTimer;

    public CustomTimerDialog(Context context, long timeInMillis) {
        super(context);

        // Inflate your custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.timer_dialog, null);

        // Set your dialog's content view
        setContentView(dialogView);

        final TextView timerTextView = dialogView.findViewById(R.id.timerTextView);
        int seconds = (int) (timeInMillis / 1000);
        int minutes = seconds / 60;
        seconds %= 60;


        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
        Button startButton = dialogView.findViewById(R.id.startButton); // Assuming you have a button to start
        Button cancelButton = dialogView.findViewById(R.id.cancelButton); // Assuming you have a button to cancel

        startButton.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel(); // Cancel any existing timer
            }

            // Create and start a new countdown timer
            countDownTimer = new CountDownTimer(timeInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    seconds %= 60;


                    timerTextView.setText(String.format("%02d:%02d", minutes, seconds));

                }

                @Override
                public void onFinish() {
                    timerTextView.setText("00:00");
                    dismiss();

                }
            };

            countDownTimer.start(); // Start the timer
        });

        cancelButton.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel(); // Cancel the timer
            }
            dismiss(); // Dismiss the dialog on cancel
        });
    }
}

