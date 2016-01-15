package com.example.mprice.mptodo.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

/**
 * Created by mprice on 1/13/16.
 */
@Table(database = MPTaskDatabase.class)
public class MPTask extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;

    @ForeignKey(saveForeignKeyModel = false)
    public ForeignKeyContainer<MPTaskCategory> categoryForeignKeyContainer;

    /**
     * Example of setting the model for the queen.
     */
    public void addToCategory(MPTaskCategory category) {
        categoryForeignKeyContainer = FlowManager.getContainerAdapter(MPTaskCategory.class).toForeignKeyContainer(category);
    }
}
