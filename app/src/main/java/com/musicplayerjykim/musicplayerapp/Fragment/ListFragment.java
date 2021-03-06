package com.musicplayerjykim.musicplayerapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.musicplayerjykim.musicplayerapp.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017-03-30.
 */

public class ListFragment extends Fragment {

    private List<String> mData;

    public static ListFragment newInstance(List<String> data) {
        ListFragment fragment = new ListFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) data);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.list_id);

        Bundle bundle = getArguments();
        mData = (List<String>) bundle.getSerializable("data");

        ListAdapter adapter = new ListAdapter(mData);

        listView.setAdapter(adapter);
    }

    private static class ListAdapter extends BaseAdapter {

        private final List<String> mmData;

        public ListAdapter(List<String> data) {
            mmData = data;
        }

        @Override
        public int getCount() {
            return mmData.size();
        }

        @Override
        public Object getItem(int position) {
            return mmData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);

                viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String data = mmData.get(position);

            viewHolder.textView.setText(data);

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView textView;
    }
}
