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

/**
 * Created by mprice on 1/16/16.
 */
public class MPMainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private MPTaskListAdapter mTaskListAdapter;
    private ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpmain);

        mExpandableListView = (ExpandableListView)findViewById(R.id.lvItems);
        registerForContextMenu(mExpandableListView);

        mTaskListAdapter = new MPTaskListAdapter(this);
        mExpandableListView.setAdapter(mTaskListAdapter);

        setupListViewListener();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MPMainActivity.this, MPEditItemActivity.class);
                startActivity(i);
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
        int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);

        if (childPos >= 0) {
            mTaskListAdapter.removeTask(groupPos,childPos);
        } else {
            mTaskListAdapter.removeCategory(groupPos);
        }

        mTaskListAdapter.notifyDataSetChanged();
        return true;
    }

    private void setupListViewListener() {
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent i = new Intent(MPMainActivity.this, MPEditItemActivity.class);

                MPTask task = mTaskListAdapter.getChild(groupPosition, childPosition);
                i.putExtra("taskId", task.id);
                i.putExtra("color", groupPosition);

                startActivity(i);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mTaskListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        mTaskListAdapter.notifyDataSetChanged();
    }

    public void onCategoryCreateClicked(View item) {
        FragmentManager fm = getSupportFragmentManager();
        MPAddCategoryDialog editNameDialog = MPAddCategoryDialog.newInstance();
        editNameDialog.show(fm, "fragment_edit_name");
    }
}

