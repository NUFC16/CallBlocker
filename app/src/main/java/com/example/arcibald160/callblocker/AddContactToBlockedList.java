package com.example.arcibald160.callblocker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arcibald160.callblocker.data.BlockListContract;

public class AddContactToBlockedList extends AppCompatActivity {

    private EditText mBlockedNumberView;
    private EditText mBlockedNameView;
    private Button mContactsButton;
    private Button mAddButton;

    private static final int PICK_CONTACT_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_to_blocked_list);

        mBlockedNumberView = (EditText) findViewById(R.id.number_input_id);
        mBlockedNameView = (EditText) findViewById(R.id.name_input_id);

        mContactsButton = (Button) findViewById(R.id.contacts_button_id);
        mAddButton = (Button) findViewById(R.id.add_blocked_button_id);

        mContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // reset edit text
                mBlockedNumberView.setText("");
                mBlockedNameView.setText("");

                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT_CODE);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mBlockedNumberView.getText().toString())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_number), Toast.LENGTH_LONG).show();
                }
                // Defines a new Uri object that receives the result of the insertion
                Uri mNewUri;

                // Defines an object to contain the new values to insert
                ContentValues mNewBlockedNumber = new ContentValues();

                /*
                 * Sets the values of each column and inserts the word. The arguments to the "put"
                 * method are "column name" and "value"
                 */
//                Date dt = new Date();
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//
//                String date = dateFormat.format(dt);
//                String time = timeFormat.format(dt);

                mNewBlockedNumber.put(BlockListContract.BlockListEntry.COLUMN_NUMBER, mBlockedNumberView.getText().toString());

                if (!TextUtils.isEmpty(mBlockedNameView.getText().toString())) {
                    mNewBlockedNumber.put(BlockListContract.BlockListEntry.COLUMN_NAME, mBlockedNameView.getText().toString());
                }
//                mNewBlockedNumber.put(BlockListContract.BlockListEntry.COLUMN_DATE, date);
//                mNewBlockedNumber.put(BlockListContract.BlockListEntry.COLUMN_TIME, time);

                mNewUri = getApplicationContext().getContentResolver().insert(
                        BlockListContract.BlockListEntry.CONTENT_URI,   // the user dictionary content URI
                        mNewBlockedNumber                          // the values to insert
                );
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT_CODE) {

            if (resultCode == RESULT_OK) {
                Uri uriContact = data.getData();

                // get Cursor from picked contact
                Cursor cursorID = this.getContentResolver().query(
                        uriContact,
                        new String[]{ContactsContract.Contacts._ID},
                        null,
                        null,
                        null
                );

                // get string id of picked contact
                String contactID = null;
                if (cursorID.moveToFirst()) {
                    contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
                }
                cursorID.close();

                Cursor cursorPhone = this.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                        new String[]{contactID}, null);

                // get phone number from contact
                if (cursorPhone.moveToFirst()) {
                    String phoneNum = cursorPhone.getString(
                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mBlockedNumberView.setText(phoneNum);
                }

                // querying contact data store
                Cursor cursor = this.getContentResolver().query(
                        uriContact,
                        null,
                        null,
                        null,
                        null
                );

                // get name from contact
                if (cursor.moveToFirst()) {
                    String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    mBlockedNameView.setText(contactName);
                }
            }
        }
    }
}
