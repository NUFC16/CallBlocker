package com.example.arcibald160.callblocker.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class BlockListContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.arcibald160.callblocker";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "numbers" directory
    public static final String PATH_BLOCKED_NUMBERS = "numbers";
    public static final String PATH_BLOCKED_CALLS = "calls";

    /* BlockListEntry is an inner class that defines the contents of the blocked calls table */
    public static final class BlockListEntry implements BaseColumns {

        // BlockListEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BLOCKED_NUMBERS).build();

        public static final String TABLE_NAME = "blocked_numbers";

        // "_ID" column is auto-produced
        public static final String
                COLUMN_NUMBER = "number",
                COLUMN_NAME = "contact_name";
    }

    public static final class BlockedCallsReceived implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BLOCKED_CALLS).build();

        public static final String TABLE_NAME = "blocked_calls";

        public static final String
                COLUMN_NUMBER = "number",
                COLUMN_NAME = "contact_name",
                COLUMN_TIME = "time",
                COLUMN_DATE = "date";
    }
}
