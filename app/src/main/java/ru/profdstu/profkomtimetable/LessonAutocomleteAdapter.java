package ru.profdstu.profkomtimetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by metal on 21.03.2018.
 */

public class LessonAutocomleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;

    private Context mContext;
    private List<String> mGroups;
    private WebHelper mWebHelper;

    public LessonAutocomleteAdapter(Context context){
        mContext=context;
        mWebHelper = WebHelper.getInstance(context);
        mGroups = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public String getItem(int index) {
        return mGroups.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.group_drop_down_list, parent, false);
        }
        String group = getItem(position);
        ((TextView) convertView.findViewById(R.id.group)).setText(group);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<String> groups = findGroups(constraint);
                    // Assign the data to the FilterResults
                    filterResults.values = groups;
                    filterResults.count = groups.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mGroups = (List<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    private List<String> findGroups(CharSequence constraint){
        List<String> newList = new ArrayList<>();
        for (String group:  mWebHelper.getGroups()
             ) {
            if(group.contains(constraint.toString().toUpperCase())){
                newList.add(group);
            }
        }
        return newList;
    }
}
