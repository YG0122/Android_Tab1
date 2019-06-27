package com.example.newcontact;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    RecyclerView mRecyclerView=null;
    SimpleTextAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        JSONArray jArray = new JSONArray();

        Bitmap photo;
        try {


            while (c.moveToNext()) {

                JSONObject sObject = new JSONObject();
                String contactID = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                long id=Long.parseLong(contactID);
                ContentResolver cr=getContentResolver();
                Uri imageUrl = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
                InputStream io = ContactsContract.Contacts.openContactPhotoInputStream(cr, imageUrl);

                if(io!=null){
                    photo=resizingBitmap(BitmapFactory.decodeStream(io));

                }
                else{photo=resizingBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.profileicon));
                    }
                sObject.put("Name", contactName);
                sObject.put("Phone Number", phNumber);

                sObject.put("Photo", photo);
                jArray.put(sObject);


            }
        }catch(JSONException e){
            System.out.print("TT");
            Log.e("MYAPP", "Unexpected");
            System.out.print("EE");
        }
        c.close();
        mRecyclerView = findViewById(R.id.recycler1);
        mAdapter = new SimpleTextAdapter(jArray);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        SimpleTextAdapter adapter = new SimpleTextAdapter(jArray);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                getApplicationContext(), R.layout.test, contacts);

//        list.setAdapter(adapter);
//        loadBtn = (Button) findViewById(R.id.button1);
//        loadBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                LoadContactsAyscn lca = new LoadContactsAyscn();
//                lca.execute();
//            }
//        });
//        LoadContactsAyscn lca = new LoadContactsAyscn();
//        lca.execute();

    }
    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input != null)
            return resizingBitmap(BitmapFactory.decodeStream(input));
        else
            Log.d("PHOTO","first try failed to load photo");

        byte[] photoBytes = null;
        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
        try {
            if (c.moveToFirst())
                photoBytes = c.getBlob(0);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            c.close();
        }

        if (photoBytes != null)
            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));

        else
            Log.d("PHOTO", "second try also failed");
        return null;
    }

    public Bitmap resizingBitmap(Bitmap oBitmap) {
        if (oBitmap == null)
            return null;
        float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,55, getResources().getDisplayMetrics());
        Bitmap rBitmap = null;
//        if (width > resizing_size) {
//            float mWidth = (float) (width / 100);
//            float fScale = (float) (resizing_size / mWidth);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//
//        } else if (height > resizing_size) {
//            float mHeight = (float) (height / 100);
//            float fScale = (float) (resizing_size / mHeight);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//        }
        float mWidth = (float) (width / 100);
        float fScale = (float) (resizing_size / mWidth);
        width *= (fScale / 100);
        height *= (fScale / 100);
//        float mHeight = (float) (height / 100);
//        float fScale = (float) (resizing_size / mHeight);
//        width *= (fScale / 100);
//        height *= (fScale / 100);

//        Log.d("rBitmap : " + width + ", " + height);
        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int) height, true);
        return rBitmap;

    }
//    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
//        ProgressDialog pd;
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//            pd = ProgressDialog.show(MainActivity.this, "Loading Contacts",
//                    "Please Wait");
//        }

//        @Override
//        protected ArrayList<String> doInBackground(Void... params) {
//            // TODO Auto-generated method stub
//            ArrayList<String> contacts = new ArrayList<String>();
//
//            Cursor c = getContentResolver().query(
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                    null, null, null);
//            while (c.moveToNext()) {
//
//                String contactName = c
//                        .getString(c
//                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String phNumber = c
//                        .getString(c
//                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                contacts.add(contactName + ":" + phNumber);
//
//            }
//            c.close();
//
//            return contacts;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<String> contacts) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(contacts);
//
//            pd.cancel();
//
////            ll.removeView(loadBtn);
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                    getApplicationContext(), R.layout.test, contacts);
//
//            list.setAdapter(adapter);
//
//        }

//    }
}