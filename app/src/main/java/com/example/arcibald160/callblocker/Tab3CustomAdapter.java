package com.example.arcibald160.callblocker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arcibald160.callblocker.data.BlockListContract;
import com.example.arcibald160.callblocker.tools.CursorHelper;


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
        mCursor.moveToPosition(position);
        final CursorHelper cHelper = new CursorHelper(mCursor);

        //Set values
        holder.itemView.setTag(cHelper.id);
        holder.blockAllTimeFrom.setText(cHelper.timeFrom);
        holder.blockAllTimeUntil.setText(cHelper.timeUntil);
        holder.switchIsActive.setChecked(cHelper.is_activated());
        holder.switchIsActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(mContext, "Block time table is " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();

                // Defines an object to contain the new values to insert
                ContentValues dbContentValues = new ContentValues();
                int booleanParse = isChecked ? 1:0;
                dbContentValues.put(BlockListContract.BlockedTimetable.COLUMN_IS_ACTIVATED, booleanParse);

                Uri uri = BlockListContract.BlockedTimetable.CONTENT_URI.buildUpon().appendPath(Integer.toString(cHelper.id)).build();
                int returnVal = mContext.getContentResolver().update(
                        uri,                        // the user dictionary content URI
                        dbContentValues,            // the values to insert
                        null,
                        null
                );
            }
        });
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddNewBlockedTimetable.class);
                intent.putExtra(BlockListContract.BlockedTimetable._ID, cHelper.id);
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
        Switch switchIsActive;
        LinearLayout parentLayout;

        public BlockAllTimetableHolder(View itemView) {
            super(itemView);

            blockAllTimeFrom = (TextView) itemView.findViewById(R.id.blocktime_from_id);
            blockAllTimeUntil = (TextView) itemView.findViewById(R.id.blocktime_until_id);
            switchIsActive = (Switch) itemView.findViewById(R.id.blockall_toggle_id);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.timetable_layout_id);
        }
    }
}