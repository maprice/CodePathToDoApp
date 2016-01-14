package com.example.mprice.mptodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MPEditItemActivity extends AppCompatActivity {

    @Bind(R.id.etEditItem) EditText editText;


    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpedit_item);
        ButterKnife.bind(this);

        String text = getIntent().getStringExtra("text");
        mPosition = getIntent().getIntExtra("position", 0);
        editText.setText(text);
    }

    public void onSaveClick(View v){
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("text", editText.getText().toString());
        data.putExtra("position", mPosition);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent


    }
}
