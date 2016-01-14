package com.example.mprice.mptodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by mprice on 1/13/16.
 */
@Table(database = MPTaskDatabase.class)
public class MPTask extends BaseModel {
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name;
}
