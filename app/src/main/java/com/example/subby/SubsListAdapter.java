package com.example.subby;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subby.ui.subs.SubDetailsActivity;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class SubsListAdapter extends RecyclerView.Adapter<SubsListAdapter.SubsViewHolder> {

    private LayoutInflater mInflator;
    private List<Subscription> mSubs;
    private Context mContext;

    public SubsListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        mContext = context;
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
//            if (!current.isPaid()) {}
            holder.subName.setText(current.getSubName());
            holder.subPrice.setText(String.format(Locale.US,"$%.2f", current.getPrice()));
            holder.subNotes.setText(current.getNotes());
            switch (current.getColor()) {
                case "Red":
                    holder.subCard.setCardBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Red));
                    break;
                case "Green":
                    holder.subCard.setCardBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Green));
                    break;
                case "Blue":
                    holder.subCard.setCardBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Blue));
                    break;
                case "Purple":
                    holder.subCard.setCardBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Purple));
                    break;
                case "Yellow":
                    holder.subCard.setCardBackgroundColor(mInflator.getContext().getResources().getColor(R.color.Yellow));
                    break;
                case "White":
                    holder.subCard.setCardBackgroundColor(mInflator.getContext().getResources().getColor(R.color.White));
                    holder.subName.setTextColor(mInflator.getContext().getResources().getColor(R.color.Black));
                    holder.subPrice.setTextColor(mInflator.getContext().getResources().getColor(R.color.Black));
                    holder.subNotes.setTextColor(mInflator.getContext().getResources().getColor(R.color.Black));
                    break;
            }
        } else {
            holder.subName.setText(R.string.no_subscription_message);
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

    class SubsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Subscription current = mSubs.get(getAdapterPosition());
            Intent intent = new Intent(mContext, SubDetailsActivity.class);
            intent.putExtra("name", current.getSubName());
            intent.putExtra("price", current.getPrice());
            intent.putExtra("notes", current.getNotes());
            intent.putExtra("color", current.getColor());
            mContext.startActivity(intent);
        }
    }


}
