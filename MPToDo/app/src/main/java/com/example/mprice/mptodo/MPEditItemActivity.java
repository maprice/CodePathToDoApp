package com.example.mprice.mptodo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.mprice.mptodo.models.MPTask;
import com.example.mprice.mptodo.models.MPTaskCategory;
import com.example.mprice.mptodo.models.MPTask_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MPEditItemActivity extends AppCompatActivity {

    @Bind(R.id.spinnerCategory) Spinner spinnerCategory;
    @Bind(R.id.spinnerPriority) Spinner spinnerPriority;
    @Bind(R.id.editTextName) EditText editTextName;
    @Bind(R.id.checkBox) CheckBox checkBox;
    @Bind(R.id.datePicker) DatePicker datePicker;
    @Bind(R.id.view_edit_item) LinearLayout layout;

    private MPTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpedit_item);
        ButterKnife.bind(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);

        List<MPTaskCategory> devices = SQLite.select().from(MPTaskCategory.class).queryList();

        ArrayAdapter<MPTaskCategory> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, devices);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(dataAdapter);


        long taskId = getIntent().getLongExtra("taskId", 0);
        int colorId = getIntent().getIntExtra("color", 0);

        Resources res = this.getResources();
        int[] colorArray = res.getIntArray(R.array.androidcolors);

        int color = colorId % colorArray.length;



        if (taskId < 0) {
            editTextName.setText("");
        } else {
            mTask = SQLite.select().from(MPTask.class).where(MPTask_Table.id.eq(taskId)).querySingle();


            if (mTask != null) {

                layout.setBackgroundColor(colorArray[color]);

                editTextName.setText(mTask.name);
                MPTaskCategory category = mTask.categoryForeignKeyContainer.load();

                for (int i = 0; i < devices.size(); i++) {
                    if (devices.get(i).title.equals(category.title)) {
                        spinnerCategory.setSelection(i);
                    }
                }

                checkBox.setChecked(mTask.complete);

                datePicker.updateDate(mTask.year, mTask.month, mTask.day);
            }
        }
    }

    public void onSaveClick(View v){
        if (mTask == null) {
            mTask = new MPTask();
        }

        mTask.name = editTextName.getText().toString();
        mTask.priority = spinnerPriority.getSelectedItemPosition();
        mTask.complete = checkBox.isChecked();
        mTask.year = datePicker.getYear();
        mTask.month = datePicker.getMonth();
        mTask.day = datePicker.getDayOfMonth();

        MPTaskCategory category = (MPTaskCategory) spinnerCategory.getSelectedItem();
        Log.e("Tag", category.title);
        mTask.addToCategory(category);
        mTask.save();
        finish(); // closes the activity


    }


}
