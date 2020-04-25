package com.example.stark.taskapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    List<String> names;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<Map<String, Object>> data, List<String> names) {
        this.context = context;
        this.data = data;
        this.names = names;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView package_name = (TextView) view.findViewById(R.id.packed);
        TextView location = (TextView) view.findViewById(R.id.textView);
        package_name.setText(names.get(i));
        String temp_loc = "Latitude : " + data.get(i).get("latitude").toString() + "\nLongitude : " + data.get(i).get("longitude").toString();
        location.setText(temp_loc);
        return view;
    }
}
