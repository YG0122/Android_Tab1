package com.example.Tab5;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class gridAdapter extends BaseAdapter {
    JSONArray mData = null;
    ImageView icon;

    gridAdapter(JSONArray list) {
        mData = list;
    }

    public class ViewHolder {
        ImageView icon;
        TextView name;
    }


    public final int getCount() {
        int count=0;

        return mData.length();
    }

    public final Object getItem(int position) {
        Object ret = null;
        try {
            ret = mData.getJSONObject(position);
        } catch (JSONException e) {
            e.getStackTrace();
        }
        return ret;
    }

    public final long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview, parent, false);
            convertView.setLayoutParams(new GridView.LayoutParams(250, 220));
        }
        holder.icon = convertView.findViewById(R.id.imageView1);
        if (mData != null) {
            try {
                JSONObject item = mData.getJSONObject(position);
                Bitmap photo = (Bitmap) item.get("Photo");
                if (photo != null) {
                    holder.icon.setImageBitmap(photo);
                } else {

//                holder.icon.setImageDrawable(parent.getContext().getResources().getDrawable(R.drawable.profileicon));
                }

            } catch (JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception");
                e.getStackTrace();
            }

        }
        return convertView;

    }
}
