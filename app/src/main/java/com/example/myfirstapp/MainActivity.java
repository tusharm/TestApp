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
import static java.lang.String.valueOf;

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
                double distance = toDouble(distanceView);
                double temperature = toDouble(tempView);
                double velocity = roundToOneDecimal(getPrevailingVelocity(temperature));
                double time = roundToOneDecimal(getPredictedTime(velocity, distance));

                velocityView.setText(valueOf(velocity));
                timeView.setText(valueOf(time));

                double mean = roundToOneDecimal((firstTimer.getInterval() + secondTimer.getInterval()) / 2.0);
                double difference = roundToOneDecimal(mean - parseDouble(timeView.getText().toString()));
                double correction = roundToOneDecimal(difference * parseDouble(velocityView.getText().toString()));

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(valueOf(correction));
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private double getPrevailingVelocity(double temperature) {
        return 333.3 + ((temperature - 10) * 0.6);
    }

    private double getPredictedTime(double velocity, double distance) {
        return (distance / velocity) - 0.1;
    }

    private double roundToOneDecimal(double num) {
        return Math.round(num * 10) / 10.0;
    }

    private double toDouble(EditText view) {
        String text = view.getText().toString();
        if (text != null && !text.isEmpty()) {
            try {
                return parseDouble(text);
            } catch (Throwable t) {
                return 0.0;
            }
        }

        return 0.0;
    }
}
