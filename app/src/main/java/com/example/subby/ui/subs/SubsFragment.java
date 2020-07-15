package com.example.subby.ui.subs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subby.R;
import com.example.subby.SubsListAdapter;
import com.example.subby.Subscription;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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

        //TODO mTotalSubCost is NULL figure out whyyyyy
        subsViewModel =
                ViewModelProviders.of(this).get(SubsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subs, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final SubsListAdapter adapter = new SubsListAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        mTotalSubCost = root.findViewById(R.id.totalSubCost);


        subsViewModel.getAllSubs().observe(getViewLifecycleOwner(),
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
//                        mTotalSubCost.setText(aDouble.toString());
                        mTotalSubCost.setText(String.format(String.valueOf(aDouble)));
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
                        //TODO Find way to DELETE SUB with name
//                subsModelView.deleteSubscription(viewHolder.getAdapterPosition());
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
                    , data.getStringExtra("color"));
            subsViewModel.insert(newSub);
        }
    }
}