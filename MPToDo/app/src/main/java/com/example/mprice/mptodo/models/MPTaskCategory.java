package com.example.mprice.mptodo.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by mprice on 1/14/16.
 */
@ModelContainer
@Table(database = MPTaskDatabase.class)
public class MPTaskCategory extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String title;
    
    List<MPTask> tasks;

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "tasks")
    public List<MPTask> getTasks() {
        if (tasks == null || tasks.isEmpty()) {
            tasks = SQLite.select()
                    .from(MPTask.class)
                    .where(MPTask_Table.categoryForeignKeyContainer_id.eq(id))
                    .orderBy(MPTask_Table.priority, true)
                    .queryList();
        }
        return tasks;
    }

    @Override
    public String toString() {
return title;
    }

}
