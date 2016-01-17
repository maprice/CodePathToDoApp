package com.example.mprice.mptodo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.mprice.mptodo.models.MPTask;

public class MPMainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

@Override
public void onDismiss(final DialogInterface dialog) {
        //Fragment dialog had been dismissed


    mTaskListAdapter.notifyDataSetChanged();
        }





    MPTaskListAdapter mTaskListAdapter;

    ExpandableListView lvItems;
    int REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpmain);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        lvItems = (ExpandableListView)findViewById(R.id.lvItems);
        registerForContextMenu(lvItems);

        mTaskListAdapter = new MPTaskListAdapter(this);
        // itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(mTaskListAdapter);

        setupListViewListener();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MPMainActivity.this, MPEditItemActivity.class);
                // put "extras" into the bundle for access in the second activity


                i.putExtra("text", "sdf");
                i.putExtra("position", "asd");
                // brings up the second activity

                startActivityForResult(i, REQUEST_CODE);


            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        int groupPos = 0, childPos = 0;

            groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);

        if (childPos >= 0) {
            mTaskListAdapter.removeTask(groupPos,childPos);

        } else {
            mTaskListAdapter.removeGroup(groupPos);
                }

        mTaskListAdapter.notifyDataSetChanged();
        return true;
    }

    private void setupListViewListener() {
        lvItems.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent i = new Intent(MPMainActivity.this, MPEditItemActivity.class);
                // put "extras" into the bundle for access in the second activity

                // MPTaskCategory category = mTaskListAdapter.getGroup(groupPosition);
                MPTask task = mTaskListAdapter.getChild(groupPosition, childPosition);
                i.putExtra("taskId", task.id);
                i.putExtra("color", groupPosition);

                // brings up the second activity

                startActivity(i);

                return true;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        mTaskListAdapter.notifyDataSetChanged();
    }


    public void onCategoryCreateClicked(View item) {
            FragmentManager fm = getSupportFragmentManager();
            MPAddCategoryDialog editNameDialog = MPAddCategoryDialog.newInstance();

            editNameDialog.show(fm, "fragment_edit_name");
    }
}

