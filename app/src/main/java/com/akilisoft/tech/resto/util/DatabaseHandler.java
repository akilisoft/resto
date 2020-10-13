package com.akilisoft.tech.resto.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Copyright (c) 2020, NIKISS. All Rights Reserved.
 * <p>
 * Save to the extent permitted by law, you may not use, copy, modify, distribute or
 * create derivative works of this material or any part of it without the prior
 * written consent of  OUEDRAOGO ISSOUF NIKISS.email:ouedraogo.nikiss@gmail.com
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the software.
 * Created on 13,octobre,2020
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private  static final int DATABASE_VERSION= 1;
    protected static final String DATABASE_NAME= "resto.sqlite";
    String CREATE_TABLE_USER= "CREATE TABLE USER ( " +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "EMAIL VARCHAR(32) NOT NULL UNIQUE," +
            "PASSWORD VARCHAR(32) NOT NULL," +
            "NAME VARCHAR(32)," +
            "PHONE VARCHAR(32))";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER ");
    }
}

