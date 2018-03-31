package com.example.arcibald160.callblocker.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class BlockListContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.callblocker";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_TASKS = "numbers";

    /* BlockListEntry is an inner class that defines the contents of the task table */
    public static final class BlockListEntry implements BaseColumns {

        // BlockListEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // BlockListEntry table and column names
        public static final String TABLE_NAME = "blocked_calls";

        // Since BlockListEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String
                COLUMN_NUMBER = "number",
                COLUMN_NAME = "contact_name",
                COLUMN_TIME = "time",
                COLUMN_DATE = "date";
    }
}
