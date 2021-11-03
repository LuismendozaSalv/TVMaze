package com.mendosal.tvmaze.presentation.ui.show;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShowFragment extends Fragment implements MyShowRecyclerViewAdapter.OnShowListener,
        SearchView.OnQueryTextListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    private List<ShowEntity> showList;
    private List<ShowEntity> searchShowList;
    private MyShowRecyclerViewAdapter adapter;
    private ShowViewModel showViewModel;
    private View fragmentView;
    private SearchView svShows;
    private RecyclerView rvShows;
    private int actualPage = 0;
    private ProgressBar pbShows;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        // Set the adapter
        pbShows = fragmentView.findViewById(R.id.pbShows);
        pbShows.setVisibility(View.VISIBLE);
        svShows = fragmentView.findViewById(R.id.svShows);
        svShows.setOnQueryTextListener(this);
        Context context = fragmentView.getContext();
        rvShows = fragmentView.findViewById(R.id.rvShows);
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
            pbShows.setVisibility(View.GONE);
        });
    }

    @Override
    public void onShowClick(int position) {
        ShowFragmentDirections.ActionShowFragmentToShowDetailFragment action =
                ShowFragmentDirections.actionShowFragmentToShowDetailFragment();
        ShowEntity selectedShow = adapter.getCurrentList().get(position);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchShowsByName(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        searchShowsByName(s);
        return false;
    }

    private void searchShowsByName(String name) {
        if (name.length() > 0) {
            showViewModel.searchShow(name).observe(requireActivity(), scoreShows -> {
                List<ShowEntity> searchedShowList =
                        showViewModel.getShowsListFromSearch(scoreShows);
                adapter.setShowList(searchedShowList);
            });
        }else {
            if(searchShowList != null) {
                searchShowList.clear();
            }
            adapter.setShowList(showList);
        }
    }
}