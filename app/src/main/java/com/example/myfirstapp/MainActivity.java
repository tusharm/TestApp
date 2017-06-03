package com.example.myfirstapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ToggleButton;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText distanceView = (EditText) findViewById(R.id.distanceText);
        final EditText tempView = (EditText) findViewById(R.id.tempText);

        final EditText velocityView = (EditText) findViewById(R.id.soundVelocityText);
        velocityView.setFocusable(false);

        final EditText timeView = (EditText) findViewById(R.id.timeText);
        timeView.setFocusable(false);

        SoundMetricsCalculator calculator =
                new SoundMetricsCalculator(distanceView, tempView, velocityView, timeView);
        tempView.setOnFocusChangeListener(calculator);
        distanceView.setOnFocusChangeListener(calculator);

        final Timer firstTimer = new Timer((Chronometer) findViewById(R.id.simpleChronometer1));
        ToggleButton firstTimerButton = (ToggleButton) findViewById(R.id.timerButton1);
        firstTimerButton.setOnCheckedChangeListener(firstTimer);

        final Timer secondTimer = new Timer((Chronometer) findViewById(R.id.simpleChronometer2));
        ToggleButton secondTimerButton = (ToggleButton) findViewById(R.id.timerButton2);
        secondTimerButton.setOnCheckedChangeListener(secondTimer);

        Button submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mean = roundToOneDecimal((firstTimer.getInterval() + secondTimer.getInterval()) / 2.0);
                double difference = roundToOneDecimal(mean - parseDouble(timeView.getText().toString()));
                double correction = roundToOneDecimal(difference * parseDouble(velocityView.getText().toString()));

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(String.valueOf(correction));
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private double roundToOneDecimal(double num) {
        return Math.round(num * 10) / 10.0;
    }
}
