package com.example.mprice.mptodo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mprice.mptodo.models.MPTaskCategory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MPAddCategoryDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MPAddCategoryDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MPAddCategoryDialog extends DialogFragment implements View.OnClickListener {

    @Bind(R.id.buttonAdd) Button buttonAdd;
    @Bind(R.id.buttonCancel) Button buttonCancel;
    @Bind(R.id.edit_text_name) EditText editTextName;


    public MPAddCategoryDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MPAddCategoryDialog newInstance(String title) {
        MPAddCategoryDialog frag = new MPAddCategoryDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mpadd_category_dialog, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAdd.setOnClickListener(this);

        editTextName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCancel:

                dismiss();

                break;
            case R.id.buttonAdd:
                MPTaskCategory category = new MPTaskCategory();
                category.title = editTextName.getText().toString();
                category.save();
                dismiss();
                break;
        }
    }

}