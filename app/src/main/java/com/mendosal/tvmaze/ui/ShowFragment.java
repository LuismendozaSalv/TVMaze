package com.mendosal.tvmaze.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;
import com.mendosal.tvmaze.viewmodel.ShowViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShowFragment extends Fragment implements MyShowRecyclerViewAdapter.OnShowListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    List<ShowEntity> showList;
    MyShowRecyclerViewAdapter adapter;
    ShowViewModel showViewModel;
    private View fragmentView;

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
        showViewModel = new ViewModelProvider(requireActivity()).get(ShowViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_show_list, container, false);

        // Set the adapter
        Context context = fragmentView.getContext();
        RecyclerView recyclerView = fragmentView.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MyShowRecyclerViewAdapter(getActivity(), showList, this);
        recyclerView.setAdapter(adapter);
        loadShows();
        return fragmentView;
    }

    private void loadShows() {
        showViewModel.getShows().observe(getActivity(), showEntities -> {
            showList = showEntities;
            adapter.setShowList(showList);
        });
    }

    @Override
    public void onShowClick(int position) {
        ShowFragmentDirections.ActionShowFragmentToShowDetailFragment action =
                ShowFragmentDirections.actionShowFragmentToShowDetailFragment();
        ShowEntity selectedShow = showList.get(position);
        action.setShowId(selectedShow.getId());
        Navigation.findNavController(fragmentView).navigate(action);
    }
}