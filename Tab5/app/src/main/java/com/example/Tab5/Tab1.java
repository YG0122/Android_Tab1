package com.example.Tab5;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class Tab1 extends Fragment {
    RecyclerView mRecyclerView=null;
    SimpleTextAdapter mAdapter = null;


    @Override
   public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

        Cursor c = getActivity().getContentResolver().query(
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
                    ContentResolver cr= getActivity().getContentResolver();
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
            mAdapter = new SimpleTextAdapter(jArray);


//
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler1);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

}
