package com.example.subby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubsListAdapter extends RecyclerView.Adapter<SubsListAdapter.SubsViewHolder> {

    private LayoutInflater mInflator;
    private List<Subscription> mSubs;

    public SubsListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SubsListAdapter.SubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_item, parent, false);
        return new SubsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubsListAdapter.SubsViewHolder holder, int position) {
        if (mSubs != null) {
            Subscription current = mSubs.get(position);
            holder.subName.setText(current.getSubName());
            holder.subPrice.setText(toString().valueOf(current.getPrice()));
            holder.subNotes.setText(current.getNotes());
            switch (current.getColor()) {
                case "Red":
                    holder.subCard.setBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Red));
                    break;
                case "Green":
                    holder.subCard.setBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Green));
                    break;
                case "Blue":
                    holder.subCard.setBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Blue));
                    break;
                case "Purple":
                    holder.subCard.setBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Purple));
                    break;
                case "Yellow":
                    holder.subCard.setBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Yellow));
                    break;
                case "White":
                    holder.subCard.setBackgroundColor(mInflator.getContext().getResources().getColor(R.color.White));
                    break;
            }
        } else {
            holder.subName.setText("No Subscription");
        }

    }

    @Override
    public int getItemCount() {
        if (mSubs != null) {
            return mSubs.size();
        }
        return 0;
    }

    public void setSubs(List<Subscription> subs) {
        mSubs = subs;
        notifyDataSetChanged();
    }

    class SubsViewHolder extends RecyclerView.ViewHolder {
        private final TextView subName;
        private final TextView subPrice;
        private final TextView subNotes;
        private final CardView subCard;

        private SubsViewHolder(@NonNull View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.subName);
            subPrice = itemView.findViewById(R.id.subPrice);
            subNotes = itemView.findViewById(R.id.subNotes);
            subCard = itemView.findViewById(R.id.subCard);
        }
    }


}
