package com.example.arcibald160.callblocker.tools;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arcibald160.callblocker.AddNewBlockedTimetable;
import com.example.arcibald160.callblocker.R;
import com.example.arcibald160.callblocker.Tab3CustomAdapter;

public class Tab3Fragment extends Fragment {

    private static final int LOADER_ID = 1300;

    private RecyclerView mRecyclerView;
    private Tab3CustomAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment, container, false);

        // Recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_tab3);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_blockall_id);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent timetableIntent = new Intent(getContext(), AddNewBlockedTimetable.class);
                startActivity(timetableIntent);
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // specify an adapter (see also next example)
        mAdapter = new Tab3CustomAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
