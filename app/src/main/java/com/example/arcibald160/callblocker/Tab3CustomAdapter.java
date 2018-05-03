package com.example.arcibald160.callblocker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arcibald160.callblocker.data.BlockListContract;


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

        final String [] daysOfWeekColumns = BlockListContract.BlockedTimetable.getDaysOfWeekColumns();
        final int daysLength = daysOfWeekColumns.length;
        int [] daysOfWeekIndices = new int[daysLength];


        for(int i=0; i<daysLength; i++) {
            daysOfWeekIndices[i] = mCursor.getColumnIndex(daysOfWeekColumns[i]);
        }

        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        final String timeFrom = mCursor.getString(timeFromIndex);
        final String timeUntil = mCursor.getString(timeUntilIndex);

        final String [] daysOfWeekValues = new String[daysLength];
        for(int i=0; i<daysLength; i++) {
            daysOfWeekValues[i] = mCursor.getString(daysOfWeekIndices[i]);
        }

        //Set values
        holder.itemView.setTag(id);
        holder.blockAllTimeFrom.setText(timeFrom);
        holder.blockAllTimeUntil.setText(timeUntil);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddNewBlockedTimetable.class);
                // use existing strings in db helper so everything is on the same place
                intent.putExtra(BlockListContract.BlockedTimetable._ID, id);
                intent.putExtra(BlockListContract.BlockedTimetable.COLUMN_TIME_FROM, timeFrom);
                intent.putExtra(BlockListContract.BlockedTimetable.COLUMN_TIME_UNTIL, timeUntil);

                for(int i=0; i<daysLength; i++) {
                    intent.putExtra(daysOfWeekColumns[i], daysOfWeekValues[i]);
                }
                mContext.startActivity(intent);
            }
        });
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
        LinearLayout parentLayout;

        public BlockAllTimetableHolder(View itemView) {
            super(itemView);

            blockAllTimeFrom = (TextView) itemView.findViewById(R.id.blocktime_from_id);
            blockAllTimeUntil = (TextView) itemView.findViewById(R.id.blocktime_until_id);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.timetable_layout_id);
        }
    }
}
