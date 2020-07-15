package com.example.subby.ui.paid;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subby.R;
import com.example.subby.Subscription;
import com.example.subby.ui.paid.PaidListAdapter;

import java.util.List;

public class PaidFragment extends Fragment {

    private PaidModelView paidModelView;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paidModelView =
                ViewModelProviders.of(this).get(PaidModelView.class);
        View root = inflater.inflate(R.layout.fragment_paid, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_paid);
        final PaidListAdapter adapter = new PaidListAdapter(context);
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), gridColumnCount));
        recyclerView.setAdapter(adapter);

        paidModelView.getAllSubs(true).observe(getViewLifecycleOwner(), new Observer<List<Subscription>>() {
            @Override
            public void onChanged(List<Subscription> subscriptions) {
                adapter.setSubs(subscriptions);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Subscription currentSub = adapter.getSubAtPosition(position);
                paidModelView.updateSubPaid(currentSub);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(recyclerView);


        return root;
    }
}