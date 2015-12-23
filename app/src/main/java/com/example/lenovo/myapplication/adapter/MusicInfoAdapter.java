package com.example.lenovo.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.lenovo.myapplication.MusicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2015/12/22.
 */
public class MusicInfoAdapter extends BaseExpandableListAdapter {
    private List<String> groupArray ;
    private List<List<MusicInfo>> childArray ;
    private Context context;
    public MusicInfoAdapter(List<String> group, List<List<MusicInfo>> child, Context context){
        childArray = new ArrayList<List<MusicInfo>>();
        groupArray = new ArrayList<String>();
        groupArray.addAll(group);
        childArray.addAll(child);
        this.context = context;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(groupPosition).get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String string = childArray.get(groupPosition).get(childPosition).getDisplayName();
        convertView = getView(string, 2);
        convertView.setBackgroundColor(Color.GREEN);
        return convertView;
    }

    private View getView(String string, int current) {
        AbsListView.LayoutParams  layoutParams =new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView =new TextView(context);
        textView.setLayoutParams(layoutParams);

        textView.setGravity(Gravity.CENTER_VERTICAL |Gravity.LEFT);
        if(current == 1) {
            textView.setPadding(15, 20, 0, 20);
        }else if(current == 2){
            textView.setPadding(30, 20, 0, 20);
        }
        textView.setText(string);
        return textView;
    }


    @Override
    public int getGroupCount() {
        return groupArray.size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return groupArray.get(groupPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String string = groupArray.get(groupPosition);
        convertView = getView(string, 1);
        convertView.setBackgroundColor(Color.BLUE);
        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
