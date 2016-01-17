package com.example.mprice.mptodo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.mprice.mptodo.utils.MPUtils;
import com.example.mprice.mptodo.R;
import com.example.mprice.mptodo.models.MPTask;
import com.example.mprice.mptodo.models.MPTaskCategory;
import com.example.mprice.mptodo.models.MPTask_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mprice on 1/16/16.
 */
public class MPEditItemActivity extends AppCompatActivity {

    @Bind(R.id.spinnerCategory)
    Spinner mSpinnerCategory;
    @Bind(R.id.spinnerPriority)
    Spinner mSpinnerPriority;
    @Bind(R.id.editTextName)
    EditText mEditTextName;
    @Bind(R.id.checkBox)
    CheckBox mCheckBox;
    @Bind(R.id.datePicker)
    DatePicker mDatePicker;
    @Bind(R.id.view_edit_item)
    LinearLayout mBackgroundLayout;

    private MPTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpedit_item);
        ButterKnife.bind(this);

        // Priority Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPriority.setAdapter(adapter);

        // Category Spinner
        List<MPTaskCategory> devices = SQLite.select().from(MPTaskCategory.class).queryList();
        ArrayAdapter<MPTaskCategory> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, devices);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(dataAdapter);

        long taskId = getIntent().getLongExtra("taskId", 0);
        int colorId = getIntent().getIntExtra("color", 0);

        if (taskId >= 0) {
            mTask = SQLite.select().from(MPTask.class).where(MPTask_Table.id.eq(taskId)).querySingle();

            if (mTask != null) {
                mBackgroundLayout.setBackgroundColor(MPUtils.getColorWithRowId(colorId, this));
                mEditTextName.setText(mTask.name);
                mEditTextName.setSelection(mEditTextName.getText().length());
                mCheckBox.setChecked(mTask.complete);
                mDatePicker.updateDate(mTask.year, mTask.month, mTask.day);

                // Set Category Picker
                MPTaskCategory category = mTask.categoryForeignKeyContainer.load();
                for (int i = 0; i < devices.size(); i++) {
                    if (devices.get(i).title.equals(category.title)) {
                        mSpinnerCategory.setSelection(i);
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if (mEditTextName.getText().toString().isEmpty()) {
            mEditTextName.requestFocus();
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    public void onSaveClick(View v) {
        if (mTask == null) {
            mTask = new MPTask();
        }

        // Update Task
        mTask.name = mEditTextName.getText().toString();
        mTask.priority = mSpinnerPriority.getSelectedItemPosition();
        mTask.complete = mCheckBox.isChecked();
        mTask.year = mDatePicker.getYear();
        mTask.month = mDatePicker.getMonth();
        mTask.day = mDatePicker.getDayOfMonth();

        MPTaskCategory category = (MPTaskCategory) mSpinnerCategory.getSelectedItem();
        mTask.addToCategory(category);
        mTask.save();
        finish();
    }
}
