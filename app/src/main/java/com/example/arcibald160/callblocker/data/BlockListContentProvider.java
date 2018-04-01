package com.example.arcibald160.callblocker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by arcibald160 on 01.04.18..
 */

public class BlockListContentProvider extends ContentProvider {

    public static final int BLOCKED_NUMBERS = 100;
    public static final int BLOCKED_NUMBERS_WITH_ID = 101;
    public static final int BLOCKED_NUMBERS_WITH_TIME = 102;
    public static final int BLOCKED_NUMBERS_WITH_DATE = 103;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BlockListContract.AUTHORITY, BlockListContract.PATH_BLOCKED_NUMBERS, BLOCKED_NUMBERS);
//        uriMatcher.addURI(BlockListContract.AUTHORITY, BlockListContract.PATH_BLOCKED_NUMBERS + "/#", BLOCKED_NUMBERS_WITH_ID);
//        uriMatcher.addURI(BlockListContract.AUTHORITY, BlockListContract.PATH_BLOCKED_NUMBERS + "/time/#", BLOCKED_NUMBERS_WITH_TIME);
//        uriMatcher.addURI(BlockListContract.AUTHORITY, BlockListContract.PATH_BLOCKED_NUMBERS + "/date/#", BLOCKED_NUMBERS_WITH_DATE);
        return uriMatcher;
    }

    private BlockListDbHelper mBlockListDbHelper;

    @Override
    public boolean onCreate() {
        // init BlockListDbHelper
        Context context = getContext();
        mBlockListDbHelper = new BlockListDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        SQLiteDatabase db = mBlockListDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            // If the incoming URI was for all of table3
            case BLOCKED_NUMBERS:
                retCursor =  db.query(
                        BlockListContract.BlockListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

//            // If the incoming URI was for a single row
//            case BLOCKED_NUMBERS_WITH_ID:
//
//                /*
//                 * Because this URI was for a single row, the _ID value part is
//                 * present. Get the last path segment from the URI; this is the _ID value.
//                 * Then, append the value to the WHERE clause for the query
//                 */
//                retCursor =  db.query(
//                        BlockListContract.BlockListEntry.TABLE_NAME,
//                        projection,
//                        selection + "_ID = " + uri.getLastPathSegment(),
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        // Get access to the task database (to write new data to)
        final SQLiteDatabase db = mBlockListDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case BLOCKED_NUMBERS:
                // Insert new values into the database
                // Inserting values into tasks table
                long id = db.insert(BlockListContract.BlockListEntry.TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(BlockListContract.BlockListEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
