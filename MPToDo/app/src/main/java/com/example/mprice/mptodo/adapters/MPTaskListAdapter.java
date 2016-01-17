package com.example.mprice.mptodo.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mprice.mptodo.utils.MPUtils;
import com.example.mprice.mptodo.R;
import com.example.mprice.mptodo.models.MPTask;
import com.example.mprice.mptodo.models.MPTaskCategory;
import com.example.mprice.mptodo.models.MPTaskCategory_Table;
import com.example.mprice.mptodo.models.MPTask_Table;
import com.raizlabs.android.dbflow.list.FlowQueryList;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by mprice on 1/13/16.
 */
public class MPTaskListAdapter extends BaseExpandableListAdapter {

    private FlowQueryList<MPTaskCategory> mFlowQueryList;
    private final Context mContext;

    public MPTaskListAdapter(Context context) {
        this.mContext = context;

        mFlowQueryList = new FlowQueryList<>(MPTaskCategory.class, MPTaskCategory_Table.title.like("%"));
        mFlowQueryList.enableSelfRefreshes(context);
    }


    @Override
    public int getGroupCount() {
        return mFlowQueryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
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
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_list_group, null);
        }

        // Set Title text
        convertView.setBackgroundColor(MPUtils.getColorWithRowId(groupPosition, mContext));
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.ITALIC);
        lblListHeader.setText(headerTitle);

        // Set Progress text
        TextView progress = (TextView) convertView.findViewById(R.id.text_view_progress);
        progress.setTypeface(null, Typeface.NORMAL);
        MPTaskCategory category = getGroup(groupPosition);
        List<MPTask> completedTasks = SQLite.select()
                .from(MPTask.class)
                .where(MPTask_Table.categoryForeignKeyContainer_id.eq(category.id)).and(MPTask_Table.complete.is(true)).queryList();
        String progressText = completedTasks.size() + "/" + getChildrenCount(groupPosition);
        progress.setText(progressText);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.task_list_item, parent, false);
        } else {
            rowView = convertView;
        }

        TextView itemTitle = (TextView) rowView.findViewById(R.id.task_item_title);
        TextView itemPriority = (TextView) rowView.findViewById(R.id.task_item_priority);
        TextView itemDate = (TextView) rowView.findViewById(R.id.task_item_date);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.task_item_checkbox);

        MPTask task  = getChild(groupPosition, childPosition);
        String date = task.day + "/" + (task.month + 1) + "/" + task.year;

        itemDate.setText(date);
        checkBox.setChecked(task.complete);
        itemTitle.setText(task.name);
        itemPriority.setText(MPUtils.getPriorityString(task.priority, mContext));
        itemPriority.setTextColor(MPUtils.getColorWithPriority(task.priority, mContext));

        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private int getColor(int position) {
        Resources res = mContext.getResources();
        int[] colorArray = res.getIntArray(R.array.androidcolors);

        int color = position % colorArray.length;

        return colorArray[color];
    }

    @Override
    public void notifyDataSetChanged() {
        mFlowQueryList.refresh();
        super.notifyDataSetChanged();

    }

    public void removeCategory(int groupPos) {
        mFlowQueryList.remove(groupPos);
        mFlowQueryList.refresh();
    }

    public void removeTask(int groupPos, int childPosition) {
        mFlowQueryList.get(groupPos).getTasks().get(childPosition).delete();
        mFlowQueryList.get(groupPos).update();
        mFlowQueryList.refresh();
    }
}
