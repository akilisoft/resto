package com.akilisoft.tech.resto.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Copyright (c) 2020, NIKISS. All Rights Reserved.
 * <p>
 * Save to the extent permitted by law, you may not use, copy, modify, distribute or
 * create derivative works of this material or any part of it without the prior
 * written consent of  OUEDRAOGO ISSOUF NIKISS.email:ouedraogo.nikiss@gmail.com
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the software.
 * Created on 10,octobre,2020
 */
public class TableSqliteController extends DatabaseHandler {
    public TableSqliteController(Context context){
        super(context);
    }

    public boolean userCreate(String email, String password){

        String name =" Mobile";
        ContentValues values = new ContentValues();
        values.put("EMAIL",email);
        values.put("PASSWORD",password);
        values.put("NAME",name);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSUcces= db.insert("USER",null,values) > 0;
        db.close();
        return createSUcces;
    }

    public boolean auth(String email,String password) {

        String sql = "SELECT password from USER where email='"+email+"';";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            String reponse = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            if(!reponse.isEmpty() && reponse.equals(password))
                return true;
        }

        cursor.close();
        db.close();

        return false;

    }

}
