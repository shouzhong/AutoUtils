package com.wyf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyf.auto.AutoUtils;

/**
 * Created by wyf on 2017/8/18.
 */

public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        // 在Fragment中使用
        AutoUtils.auto(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(new RvAdapter());
    }

    class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder> {

        @Override
        public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RvHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class RvHolder extends RecyclerView.ViewHolder {

            public RvHolder(View itemView) {
                super(itemView);
                // 在ViewHolder中使用
                AutoUtils.auto(itemView);
            }
        }

    }
}
