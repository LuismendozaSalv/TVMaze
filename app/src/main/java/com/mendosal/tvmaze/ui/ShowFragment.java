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
import android.widget.Toast;

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
    private RecyclerView rvShows;
    private int actualPage = 0;

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
        rvShows = fragmentView.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            rvShows.setLayoutManager(new LinearLayoutManager(context));
        } else {
            rvShows.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MyShowRecyclerViewAdapter(getActivity(), showList, this);
        rvShows.setAdapter(adapter);
        loadShows(actualPage);
        initializeScrollListener();
        return fragmentView;
    }

    private void loadShows(int page) {
        showViewModel.getShows(page).observe(getActivity(), showEntities -> {
            if (showList == null){
                showList = showEntities;
            }else {
                showList.addAll(showEntities);
            }
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

    private void initializeScrollListener() {
        rvShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) &&
                        newState==RecyclerView.SCROLL_STATE_IDLE) {
                    Toast.makeText(requireActivity(), "FInal", Toast.LENGTH_LONG).show();
                    actualPage++;
                    loadShows(actualPage);
                }
            }
        });
    }
}