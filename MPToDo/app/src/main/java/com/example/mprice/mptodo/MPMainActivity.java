package com.example.mprice.mptodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.mprice.mptodo.models.MPTask;

public class MPMainActivity extends AppCompatActivity {
    MPTaskListAdapter mTaskListAdapter;

    ExpandableListView lvItems;
    int REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvItems = (ExpandableListView)findViewById(R.id.lvItems);
       // readItems();



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

        // brings up the second activity

        startActivity(i);

        return true;
    }
});


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String text = data.getExtras().getString("text");
            int position = data.getExtras().getInt("position", 0);

            //items.set(position, text);
          //  itemsAdapter.notifyDataSetChanged();
            //writeItems();

        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Log.e("sadkjfnaskljdfn", "cghanged");


        mTaskListAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mpmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fm = getSupportFragmentManager();
            MPAddCategoryDialog editNameDialog = MPAddCategoryDialog.newInstance("Some Title");
            editNameDialog.show(fm, "fragment_edit_name");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
       // EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        //String itemText = etNewItem.getText().toString();

//
//        MPTaskCategory c = new MPTaskCategory();
//        c.title = itemText;
//
//        c.save();
//
//        MPTask t = new MPTask();
//        t.name = itemText;
//
//        MPTask t1 = new MPTask();
//        t1.name = itemText + 2;
//
//        t.addToCategory(c);
//        t1.addToCategory(c);
//t.save();
//        t1.save();
//        mTaskListAdapter.addCategory(c);
//        etNewItem.setText("");

       // writeItems();
    }

//    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//
//        try {
//            items = new ArrayList<>(FileUtils.readLines(todoFile));
//        } catch (IOException e) {
//            items = new ArrayList<>();
//        }
//    }
//
//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

