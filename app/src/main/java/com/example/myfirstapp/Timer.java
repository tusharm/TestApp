package com.example.myfirstapp;

import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.CompoundButton;

class Timer implements CompoundButton.OnCheckedChangeListener {
    private double interval;
    private final Chronometer chronometer;

    Timer(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        } else {
            chronometer.stop();
            interval = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000.0;
        }
    }

    public double getInterval() {
        return interval;
    }
}
