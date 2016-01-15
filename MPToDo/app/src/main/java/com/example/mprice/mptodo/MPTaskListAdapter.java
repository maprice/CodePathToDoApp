package com.example.mprice.mptodo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.mprice.mptodo.models.MPTask;
import com.example.mprice.mptodo.models.MPTaskCategory;
import com.example.mprice.mptodo.models.MPTaskCategory_Table;
import com.raizlabs.android.dbflow.list.FlowQueryList;

/**
 * Created by mprice on 1/13/16.
 */
public class MPTaskListAdapter extends BaseExpandableListAdapter {


        private FlowQueryList<MPTaskCategory> mFlowQueryList;

        private final Context context;



        public MPTaskListAdapter(Context context) {
            this.context = context;


            mFlowQueryList = new FlowQueryList<>(MPTaskCategory.class, MPTaskCategory_Table.title.like("%"));
            mFlowQueryList.enableSelfRefreshes(context);
           // mFlowQueryList = new FlowQueryList<>(MPTask.class, MPTask_Table.name.like("%"));
        }


    @Override
    public int getGroupCount() {
        return mFlowQueryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("tag", "CHild count = " + mFlowQueryList.get(groupPosition).getTasks().size());
        return mFlowQueryList.get(groupPosition).getTasks().size();
    }

    @Override
    public MPTaskCategory getGroup(int groupPosition) {
        return mFlowQueryList.get(groupPosition);
    }

    @Override
    public MPTask getChild(int groupPosition, int childPosition) {
        return mFlowQueryList.get(groupPosition).getTasks().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).title;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.task_list_group, null);
        }


        convertView.setBackgroundColor(getColor(groupPosition));
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.ITALIC);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

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

            MPTask task  = getChild(groupPosition, childPosition);


            textView1.setText(task.name);

            return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void addCategory(MPTaskCategory model) {
        mFlowQueryList.add(model);
        mFlowQueryList.refresh();
        notifyDataSetChanged();
    }

    private int getColor(int position) {
        Resources res = context.getResources();
        int[] colorArray = res.getIntArray(R.array.androidcolors);

        int color = position % colorArray.length;

        return colorArray[color];
    }
    @Override
    public void notifyDataSetChanged() {
        mFlowQueryList.refresh();
        super.notifyDataSetChanged();
    }

}
