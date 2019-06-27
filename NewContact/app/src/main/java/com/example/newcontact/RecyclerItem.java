package com.example.newcontact;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private String nameStr ;
    private String numberStr ;


    public void setName(String name) {
        nameStr = name ;
    }
    public void setNum(String number) {
        numberStr = number ;
    }

    public String getName() {
        return this.nameStr ;
    }
    public String getNum() {
        return this.numberStr ;
    }
}

