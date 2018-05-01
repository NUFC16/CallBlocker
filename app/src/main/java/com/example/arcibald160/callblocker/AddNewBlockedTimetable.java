package com.example.arcibald160.callblocker;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.arcibald160.callblocker.data.BlockListContract;
import com.example.arcibald160.callblocker.tools.TimePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class AddNewBlockedTimetable extends AppCompatActivity {

    EditText mEditTimeFrom, mEditTimeUntil;
    Button mSubmitButton;

    private GestureDetector gestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_view);

        mEditTimeFrom = (EditText) findViewById(R.id.edit_time_from);
        mEditTimeUntil = (EditText) findViewById(R.id.edit_time_until);
        mSubmitButton = (Button) findViewById(R.id.submit_button);

        // trigger only on one click
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

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditTimeFrom.getText().toString()) || TextUtils.isEmpty(mEditTimeFrom.getText().toString())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_number), Toast.LENGTH_LONG).show();
                    return;
                }
                addDataToBlockedTimetable();
                finish();
            }
        });


    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    // find whole hierarchy tree
    private List<View> getAllChildrenBFS(View v) {
        List<View> visited = new ArrayList<View>();
        List<View> unvisited = new ArrayList<View>();
        unvisited.add(v);

        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            visited.add(child);
            if (!(child instanceof ViewGroup)) continue;
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i=0; i<childCount; i++) unvisited.add(group.getChildAt(i));
        }

        return visited;
    }

    // find all monday-sunday toggle buttons
    private void addDataToBlockedTimetable(){
        // Defines an object to contain the new values to insert
        ContentValues mNewBlockedTimetable = new ContentValues();

        // insert timeframe
        mNewBlockedTimetable.put(BlockListContract.BlockedTimetable.COLUMN_TIME_FROM, mEditTimeFrom.getText().toString());
        mNewBlockedTimetable.put(BlockListContract.BlockedTimetable.COLUMN_TIME_UNTIL, mEditTimeUntil.getText().toString());

        // insert toggle buttons state
        //get all elements
        List<View> allElements = getAllChildrenBFS(findViewById(R.id.timetable_root_id));
        String [] contentProviderAttributes = new String[] {
            BlockListContract.BlockedTimetable.COLUMN_MONDAY,
            BlockListContract.BlockedTimetable.COLUMN_TUESDAY,
            BlockListContract.BlockedTimetable.COLUMN_WEDNESDAY,
            BlockListContract.BlockedTimetable.COLUMN_THURSDAY,
            BlockListContract.BlockedTimetable.COLUMN_FRIDAY,
            BlockListContract.BlockedTimetable.COLUMN_SATURDAY,
            BlockListContract.BlockedTimetable.COLUMN_SUNDAY
        };

//        // filter elements by toggle button
//        ArrayList toggleButtonList = new ArrayList<>();

        int j=0;
        for (int i = 0; i < allElements.size(); i++) {
            final View currElement = allElements.get(i);
            if (currElement instanceof ToggleButton) {
                int booleanParse = ((ToggleButton) currElement).isChecked() ? 1:0;
                mNewBlockedTimetable.put(contentProviderAttributes[j], Integer.toString(booleanParse));
                j++;
            }
        }

        // insert data in content provider
        Uri returnUri = getApplicationContext().getContentResolver().insert(
                BlockListContract.BlockedTimetable.CONTENT_URI,   // the user dictionary content URI
                mNewBlockedTimetable                          // the values to insert
        );
    }

}
