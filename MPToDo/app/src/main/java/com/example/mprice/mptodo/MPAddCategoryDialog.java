package com.example.mprice.mptodo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.mprice.mptodo.models.MPTaskCategory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mprice on 1/16/16.
 */
public class MPAddCategoryDialog extends DialogFragment implements View.OnClickListener {

    @Bind(R.id.button_dialog_add) Button mButtonOK;
    @Bind(R.id.button_dialog_cancel) Button mButtonCancel;
    @Bind(R.id.edit_text_name) EditText mEditTextName;

    public MPAddCategoryDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MPAddCategoryDialog newInstance() {
        MPAddCategoryDialog frag = new MPAddCategoryDialog();
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
        mButtonOK.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

        mEditTextName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_dialog_cancel:
                dismiss();
                break;
            case R.id.button_dialog_add:
                MPTaskCategory category = new MPTaskCategory();
                category.title = mEditTextName.getText().toString();
                category.save();
                dismiss();
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}