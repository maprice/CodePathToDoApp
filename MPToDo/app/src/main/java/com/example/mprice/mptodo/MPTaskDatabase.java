package com.example.mprice.mptodo;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by mprice on 1/13/16.
 */

@Database(name = MPTaskDatabase.NAME, version = MPTaskDatabase.VERSION)
public class MPTaskDatabase extends BaseModel {
    public static final String NAME = "ToDo";

    public static final int VERSION = 1;
}