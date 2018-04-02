package com.example.arcibald160.callblocker.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arcibald160.callblocker.CustomAdapter;
import com.example.arcibald160.callblocker.R;
import com.example.arcibald160.callblocker.data.BlockListContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arcibald160 on 27.03.18..
 */

public class Tab2Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1234;

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);

        // needed for querying existing data
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        // floating action button for adding new number on block list
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_blocked_id);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Defines a new Uri object that receives the result of the insertion
                Uri mNewUri;

                // Defines an object to contain the new values to insert
                ContentValues mNewBlockedNumber = new ContentValues();

                /*
                 * Sets the values of each column and inserts the word. The arguments to the "put"
                 * method are "column name" and "value"
                 */
                Date dt = new Date();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                String date = dateFormat.format(dt);
                String time = timeFormat.format(dt);

                mNewBlockedNumber.put(BlockListContract.BlockListEntry.COLUMN_NUMBER, "09199988776");
                mNewBlockedNumber.put(BlockListContract.BlockListEntry.COLUMN_DATE, date);
                mNewBlockedNumber.put(BlockListContract.BlockListEntry.COLUMN_TIME, time);

                mNewUri = getContext().getContentResolver().insert(
                        BlockListContract.BlockListEntry.CONTENT_URI,   // the user dictionary content URI
                        mNewBlockedNumber                          // the values to insert
                );
            }
        });

        // Recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_tab2);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // specify an adapter (see also next example)
        mAdapter = new CustomAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    // CursorLoader for displaying data from content provider
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                BlockListContract.BlockListEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.updateData(data);
//        data.moveToFirst();
//        mTextView.setText("");
//        String text = "";
//        while (!data.isAfterLast()) {
//            text += "\n";
//            for(int i=0; i<5; i++) {
//                text += " " + data.getString(i);
//            }
//            data.moveToNext();
//        }
//        mTextView.setText(text);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.updateData(null);
    }
}
