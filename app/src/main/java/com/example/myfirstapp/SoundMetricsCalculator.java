package com.example.myfirstapp;

import android.view.View;
import android.widget.EditText;

import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;

class SoundMetricsCalculator implements View.OnFocusChangeListener {
    private final EditText distanceView;
    private final EditText temperatureView;
    private final EditText velocityView;
    private final EditText timeView;

    public SoundMetricsCalculator(
            EditText distanceView,
            EditText temperatureView,
            EditText velocityView,
            EditText timeView
    ) {
        this.distanceView = distanceView;
        this.temperatureView = temperatureView;
        this.velocityView = velocityView;
        this.timeView = timeView;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            double distance = toDouble(distanceView);
            double temperature = toDouble(temperatureView);
            double velocity = computeSoundVelocity(temperature);
            double time = computeSoundTravelTime(velocity, distance);

            velocityView.setText(valueOf(velocity));
            timeView.setText(valueOf(time));
        }
    }

    private double computeSoundVelocity(double temperature) {
        return 333.3 + ((temperature - 10) * 0.6);
    }

    private double computeSoundTravelTime(double velocity, double distance) {
        return (distance / velocity) - 0.1;
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
