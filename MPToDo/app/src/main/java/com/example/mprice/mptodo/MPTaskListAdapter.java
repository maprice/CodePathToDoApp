package com.example.mprice.mptodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.raizlabs.android.dbflow.list.FlowQueryList;

/**
 * Created by mprice on 1/13/16.
 */
public class MPTaskListAdapter extends BaseAdapter implements ListAdapter {
        private FlowQueryList<MPTask> mFlowQueryList;

        private final Context context;


        public MPTaskListAdapter(Context context) {
            this.context = context;


            mFlowQueryList = new FlowQueryList<>(MPTask.class, MPTask_Table.name.like("%"));
        }

        @Override
        public int getCount() {
            return mFlowQueryList.getCursorList().getCount();
        }

        @Override
        public MPTask getItem(int position) {
            return mFlowQueryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.task_list_item, parent, false);
            } else {
                rowView = convertView;
            }

            TextView textView1 = (TextView) rowView.findViewById(R.id.firstLine);
//            TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);

            MPTask task  = mFlowQueryList.get(position);
            textView1.setText(task.name);
//            textView1.setText("Item from:" + position);
//            textView2.setText("N:" + game.name);

            return rowView;
        }

    public void addTask(MPTask t) {
        mFlowQueryList.add(t);
    }

}
