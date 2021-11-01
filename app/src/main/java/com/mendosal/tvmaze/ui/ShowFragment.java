package com.mendosal.tvmaze.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.data.network.Resource;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;
import com.mendosal.tvmaze.viewmodel.ShowViewModel;

import java.util.List;

public class ShowFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    List<ShowEntity> showList;
    MyShowRecyclerViewAdapter adapter;
    ShowViewModel showViewModel;

    public ShowFragment() {
    }

    public static ShowFragment newInstance(int columnCount) {
        ShowFragment fragment = new ShowFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        showViewModel = new ViewModelProvider(getActivity())
                .get(ShowViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_list, container, false);

        // Set the adapter
        Button btn = view.findViewById(R.id.button2);
        btn.setOnClickListener(view1 -> {
            NavDirections action =
                    ShowFragmentDirections.actionShowFragmentToShowDetailFragment();
            Navigation.findNavController(view1).navigate(action);
        });
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MyShowRecyclerViewAdapter(getActivity(), showList);
        recyclerView.setAdapter(adapter);
        loadShows();
        return view;
    }

    private void loadShows() {
        showViewModel.getShows().observe(getActivity(), new Observer<List<ShowEntity>>() {
            @Override
            public void onChanged(List<ShowEntity> showEntities) {
                showList = showEntities;
                adapter.setShowList(showList);
            }
        });
    }
}