package com.example.subby.ui.subs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subby.R;
import com.example.subby.SubsListAdapter;
import com.example.subby.Subscription;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SubsFragment extends Fragment {

    private SubsModelView subsModelView;
    private Context context;
    public static final int SUB_REQUEST = 1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subsModelView =
                ViewModelProviders.of(this).get(SubsModelView.class);
        View root = inflater.inflate(R.layout.fragment_subs, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final SubsListAdapter adapter = new SubsListAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        subsModelView.getAllSubs().observe(getViewLifecycleOwner(),
                new Observer<List<Subscription>>() {

                    @Override
                    public void onChanged(List<Subscription> subscriptions) {
                        adapter.setSubs(subscriptions);
                    }
                });

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
                    Double.parseDouble(data.getStringExtra("price")), data.getStringExtra("note"), data.getBooleanExtra("isSubscribed", true));
            subsModelView.insert(newSub);
        }
    }
}