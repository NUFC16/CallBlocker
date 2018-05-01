package com.example.arcibald160.callblocker;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.arcibald160.callblocker.tools.TimePickerFragment;

public class AddNewBlockedTimetable extends AppCompatActivity {

    EditText mEditTimeFrom, mEditTimeUntil;
    private GestureDetector gestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_view);

        mEditTimeFrom = (EditText) findViewById(R.id.edit_time_from);
        mEditTimeUntil = (EditText) findViewById(R.id.edit_time_until);

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        mEditTimeFrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (gestureDetector.onTouchEvent(motionEvent)) {
                    DialogFragment newFragment = new TimePickerFragment(mEditTimeFrom);
                    newFragment.show(getSupportFragmentManager(), "timePicker");
                    return true;
                }
                return false;
            }
        });

        mEditTimeUntil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (gestureDetector.onTouchEvent(motionEvent)) {
                    DialogFragment newFragment = new TimePickerFragment(mEditTimeUntil);
                    newFragment.show(getSupportFragmentManager(), "timePicker");
                    return true;
                }
                return false;
            }
        });
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

}
