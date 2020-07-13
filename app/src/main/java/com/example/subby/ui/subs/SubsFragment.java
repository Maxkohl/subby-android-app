package com.example.subby.ui.subs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subby.R;
import com.example.subby.SubsListAdapter;

public class SubsFragment extends Fragment {

    private SubsModelView subsModelView;
    private Context context;

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
//        final TextView textView = root.findViewById(R.id.text_home);
        //TODO Set this to get subscriptions and put into list adapter
//        subsModelView.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        final SubsListAdapter adapter = new SubsListAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }
}