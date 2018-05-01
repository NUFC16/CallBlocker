package com.example.arcibald160.callblocker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arcibald160.callblocker.data.BlockListContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Tab3CustomAdapter extends RecyclerView.Adapter<Tab3CustomAdapter.BlockAllTimetableHolder> {
    private Context mContext;
    private Cursor mCursor;

    public Tab3CustomAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Tab3CustomAdapter.BlockAllTimetableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.blockall_timetable_view, parent, false);
        return new Tab3CustomAdapter.BlockAllTimetableHolder(view);
    }

    @Override
    public void onBindViewHolder(Tab3CustomAdapter.BlockAllTimetableHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(BlockListContract.BlockedTimetable._ID);
        int timeFromIndex = mCursor.getColumnIndex(BlockListContract.BlockedTimetable.COLUMN_TIME_FROM);
        int timeUntilIndex = mCursor.getColumnIndex(BlockListContract.BlockedTimetable.COLUMN_TIME_UNTIL);

        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        String timeFrom = mCursor.getString(timeFromIndex);
        String timeUntil = mCursor.getString(timeUntilIndex);

        //Set values
        holder.itemView.setTag(id);
        holder.blockAllTimeFrom.setText(timeFrom);
        holder.blockAllTimeUntil.setText(timeUntil);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor updateData(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class BlockAllTimetableHolder extends RecyclerView.ViewHolder {

        TextView blockAllTimeFrom, blockAllTimeUntil;
        public BlockAllTimetableHolder(View itemView) {
            super(itemView);

            blockAllTimeFrom = (TextView) itemView.findViewById(R.id.blocktime_from_id);
            blockAllTimeUntil = (TextView) itemView.findViewById(R.id.blocktime_until_id);
        }
    }
}
