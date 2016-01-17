package com.example.mprice.mptodo.utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.mprice.mptodo.R;

/**
 * Created by mprice on 1/17/16.
 */
public class MPUtils {
    public static int getColorWithRowId(int rowId, Context context) {
        Resources res = context.getResources();
        int[] colorArray = res.getIntArray(R.array.androidcolors);
        int color = rowId % colorArray.length;
        return colorArray[color];
    }

    public static int getColorWithPriority(int priority, Context context) {
        Resources res = context.getResources();
        int[] colorArray = res.getIntArray(R.array.priorityColors);
        int color = priority % colorArray.length;
        return colorArray[color];
    }

    public static String getPriorityString(int priority, Context context) {
        Resources res = context.getResources();
        String[] colorArray = res.getStringArray(R.array.priority_array);
        int color = priority % colorArray.length;
        return colorArray[color];
    }
}
