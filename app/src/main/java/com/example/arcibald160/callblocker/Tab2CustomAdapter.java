package com.example.arcibald160.callblocker;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arcibald160.callblocker.data.BlockListContract;

public class Tab2CustomAdapter extends RecyclerView.Adapter<Tab2CustomAdapter.BlockedViewHolder> {
    private Context mContext;
    // Class variables for the Cursor that holds blocked calls data
    private Cursor mCursor;
    private int selectedPos = RecyclerView.NO_POSITION;

    public Tab2CustomAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BlockedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // new view
        View view = LayoutInflater.from(mContext).inflate(R.layout.blacklist_view, parent, false);
        return new BlockedViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final BlockedViewHolder holder, int position) {
        // Here I am just highlighting the background
        holder.itemView.setBackgroundColor(selectedPos == position ? Color.LTGRAY : Color.TRANSPARENT);

        int idIndex = mCursor.getColumnIndex(BlockListContract.BlockListEntry._ID);
        int nameIndex = mCursor.getColumnIndex(BlockListContract.BlockListEntry.COLUMN_NAME);
        int numberIndex = mCursor.getColumnIndex(BlockListContract.BlockListEntry.COLUMN_NUMBER);

        // get to the right location in the cursor
        mCursor.moveToPosition(position);

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String name = mCursor.getString(nameIndex);

        // if name is unknown
        name = (name == null) ? mContext.getString(R.string.unknown): name;
        String number = mCursor.getString(numberIndex);

        //Set values
        holder.itemView.setTag(id);
        holder.removeBlockedImageView.setTag(id);
        holder.blockedNameView.setText(name);
        holder.blockedNumberView.setText(number);
        holder.removeBlockedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();

                // Build appropriate uri with String row id appended
                String stringId = Integer.toString(id);
                Uri uri = BlockListContract.BlockListEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
                mContext.getContentResolver().delete(uri, null, null);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class BlockedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView blockedNameView;
        TextView blockedNumberView;
        ImageView removeBlockedImageView;

        public BlockedViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            removeBlockedImageView = (ImageView) itemView.findViewById(R.id.remove_blocked_contact_id);
            blockedNameView = (TextView) itemView.findViewById(R.id.blocked_name_id);
            blockedNumberView = (TextView) itemView.findViewById(R.id.blocked_number_id);
        }

        @Override
        public void onClick(View v) {
            // Below line is just like a safety check, because sometimes holder could be null,
            // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);

            // Do your another stuff for your onClick
        }
    }
}
