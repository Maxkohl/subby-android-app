package com.example.subby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class SubsListAdapter extends RecyclerView.Adapter<SubsListAdapter.SubsViewHolder> {

    private LayoutInflater mInflator;
    private List<Subscription> mSubs;

    SubsListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SubsListAdapter.SubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_item, parent, false);
        return new SubsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubsListAdapter.SubsViewHolder holder, int position) {
        if (mSubs == null) {
            Subscription current = mSubs.get(position);
            holder.subItemView.setText(current.getSubName());
        } else {
            holder.subItemView.setText("No Subscription");
        }

    }

    @Override
    public int getItemCount() {
        if (mSubs != null) {
            return mSubs.size();
        }
        return 0;
    }

    void setSubs(List<Subscription> subs) {
        mSubs = subs;
        notifyDataSetChanged();
    }

    class SubsViewHolder extends RecyclerView.ViewHolder {
        private final TextView subItemView;

        private SubsViewHolder(@NonNull View itemView) {
            super(itemView);
            subItemView = itemView.findViewById(R.id.subName);
        }
    }


}
