package com.example.Tab5;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
    private ViewGroup Parent;
    private JSONArray mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon ;
        TextView name;
        TextView number;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
//            textView1 = itemView.findViewById(R.id.text1) ;
            icon = itemView.findViewById(R.id.icon) ;
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SimpleTextAdapter(JSONArray list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Parent=parent;
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
        try {
            JSONObject item = mData.getJSONObject(position);
            Bitmap photo=(Bitmap)item.get("Photo");
            if(photo!=null){
                holder.icon.setImageBitmap((Bitmap)item.get("Photo"));
            }
            else{

                holder.icon.setImageDrawable(Parent.getContext().getResources().getDrawable(R.drawable.profileicon));
            }
            holder.name.setText((String)item.get("Name"));
            holder.number.setText((String)item.get("Phone Number"));
//            String name = (String) mData.getJSONObject(position).get("Name");
        } catch(JSONException e){
            Log.e("MYAPP", "unexpected JSON exception");
            e.getStackTrace();
        }

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {

        return mData.length();
    }

}
