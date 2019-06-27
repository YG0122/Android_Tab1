package com.example.newcontact;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
    ListView list;
    LinearLayout ll;
//    Button loadBtn;\
    RecyclerView mRecyclerView=null;
    SimpleTextAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ll = (LinearLayout) findViewById(R.id.LinearLayout1);
//
//        list = (ListView) findViewById(R.id.listView1);

        ArrayList<String> contacts = new ArrayList<String>();

        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        JSONArray jArray = new JSONArray();
        try {


            while (c.moveToNext()) {

                JSONObject sObject = new JSONObject();

                String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                sObject.put("Name", contactName);
                sObject.put("Phone Number", phNumber);
                jArray.put(sObject);

                contacts.add(contactName + ":" + phNumber);

            }
        }catch(JSONException e){
            e.printStackTrace();
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