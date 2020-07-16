package com.example.subby.ui.subs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subby.R;
import com.example.subby.SubsListAdapter;
import com.example.subby.Subscription;
import com.example.subby.ui.paid.PaidListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class SubsFragment extends Fragment {

    private SubsViewModel subsViewModel;
    private Context context;
    public static final int SUB_REQUEST = 1;
    private TextView mTotalSubCost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        subsViewModel =
                ViewModelProviders.of(this).get(SubsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subs, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final SubsListAdapter adapter = new SubsListAdapter(context);
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), gridColumnCount));
        recyclerView.setAdapter(adapter);

        mTotalSubCost = root.findViewById(R.id.totalSubCost);


        subsViewModel.getAllSubs(false).observe(getViewLifecycleOwner(),
                new Observer<List<Subscription>>() {
                    @Override
                    public void onChanged(List<Subscription> subscriptions) {
                        adapter.setSubs(subscriptions);
                    }
                });

        subsViewModel.getTotalCost().observe(getViewLifecycleOwner(),
                new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if (aDouble != null) {
                            mTotalSubCost.setText(String.format(Locale.US, "$%.2f", aDouble));
                        } else {
                            mTotalSubCost.setText("$0");
                        }
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
                        subsViewModel.updateSubPaid(currentSub);
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddSubActivity.class);
                startActivityForResult(intent, SUB_REQUEST);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUB_REQUEST) {
            //TODO Add condition to check that resultCode is RESULT_OK here
            Subscription newSub = new Subscription(data.getStringExtra("name"),
                    Double.parseDouble(data.getStringExtra("price")), data.getStringExtra("note")
                    , data.getStringExtra("color"), data.getLongExtra("dueDate", 0));
            subsViewModel.insert(newSub);
        }
    }
}